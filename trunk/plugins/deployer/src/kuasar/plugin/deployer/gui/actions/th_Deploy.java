/*
 * Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kuasar.plugin.deployer.gui.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import kuasar.plugin.Global.CMD;
import kuasar.plugin.deployer.Config;
import kuasar.plugin.deployer.gui.actions.infopns.pn_Connecting;
import kuasar.plugin.deployer.gui.actions.infopns.pn_Creating;
import kuasar.plugin.deployer.gui.pn_Deploy;
import kuasar.plugin.utils.Connection;
import kuasar.plugin.utils.SSocketTools;
import kuasar.plugin.utils.SSocketTools.IllegalStatement;
import kuasar.plugin.utils.SSocketTools.InitSocketException;
import kuasar.plugin.utils.Utils;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class th_Deploy extends Thread {

    private final static int BUFFER_SIZE = 1024;
    private Element vms;
    private pn_Deploy parent;
    private String ks;
    private char[] kspwd;
    private char[] userpwd;
    private String user;
    private ArrayList<String[]> errors = new ArrayList<String[]>();
    private pn_Creating creat=null;
    public th_Deploy(Element vms, pn_Deploy parent) {
        this.vms = vms;
        this.parent = parent;
    }

    @Override
    public void run() {
        recursiveDeploy(vms, "", "");
        if(!errors.isEmpty()){
            parent.showErrors(errors);
        }else{
            parent.goNext();
        }
    }

    private void recursiveDeploy(Element vms, String path, String gpath) {
        List<Element> children = vms.getChildren();
        for (Element child : children) {
            if (child.getAttributeValue("type").equals("vm")) {
                deployVM(child, "/" + child.getName(), "/" + child.getAttributeValue("name"));
            } else {
                recursiveDeploy(child, "/" + child.getName(), "/" + child.getAttributeValue("name"));
            }
        }
    }

    private void deployVM(Element vm, String path, String gpath) {
        parent.setCurrentMachime(gpath);
        String address = vm.getAttributeValue("server");
        int port = Integer.parseInt(vm.getAttributeValue("server.port"));
        pn_Connecting conn = new pn_Connecting();
        parent.changePanel(conn);
        if (!loadSecrets(address)) {
            errors.add(new String[]{path, gpath, "It was impossible to get data login"});
            return;
        }
        SSocketTools st = new SSocketTools(address, port, ks, kspwd, user, userpwd);
        st.initSocket();
        if (!startConnection(st)) {
            errors.add(new String[]{path, gpath, "It was impossible connect to server" + address});
            st.closeAll();
            return;
        }
        if (!enterHVMode(st, vm.getAttributeValue("hv"))) {
            errors.add(new String[]{path, gpath, "There aren't a Hypervisor plugin on server " + address + " with Engine Code: " + vm.getAttributeValue("hv")});
            st.closeAll();
            return;
        }
        conn.stopAnimation();
        creat = new pn_Creating();
        parent.setInfo("Creating Machine...");
        parent.changePanel(creat);
        if (!createMachine(st, vm, path, gpath)) {
            return;
        }
        creat.setValueGlobal(10);
        parent.setInfo("Setting VM's Memory...");
        if (!setMemory(st, vm, path, gpath)) {
            return;
        }
        creat.setValueGlobal(20);
        creat.showPBDisk(true);
        parent.setInfo("Adding VM's Storage...");
        if (!addStorage(st, vm, path, gpath)) {
            return;
        }
        creat.showPBDisk(false);
        creat.setValueGlobal(70);
        parent.setInfo("Adding VM's Interfaces...");
        if (!addIf(st, vm, path, gpath)) {
            return;
        }
        creat.setValueGlobal(80);
        parent.setInfo("First Running and Setting VM's Network...");
        creat.changeCurrentImage("/kuasar/plugin/deployer/icons/animation/power/power");
        if (!setNetwork(st, vm, path, gpath)) {
            return;
        }
        creat.setValueGlobal(100);
        closeConnection(st);
        parent.setInfo("VM was implemented successfully!");
        creat.stopAnimation();
    }

    private boolean loadSecrets(String address) {
        ks = Connection.getKeyStore(address);
        kspwd = Connection.getKeyStorePWD(address);
        userpwd = Connection.getUserPwd(address);

        if (ks == null) {
            ks = Config.gkeystore;
            if (ks == null) {
                //NOTIFY ERROR
                return false;
            }
        }

        if (kspwd == null) {
            kspwd = Config.gkssecret;
            if (kspwd == null) {
                //NOTIFY ERROR
                return false;
            }
        }

        if (!Connection.isDNIe(address)) {
            user = Connection.getUserName(address);
            if (user == null) {
                user = Config.guser;
                if (user == null) {
                    //NOTIFY ERROR
                    return false;
                }
            }

        } else {
            user = "";
        }
        if (userpwd == null) {
            userpwd = Config.gusersecret;
            if (userpwd == null) {
                //NOTIFY ERROR
                return false;
            }
        }
        return true;
    }

    private boolean startConnection(SSocketTools st) {
        boolean ok;
        try {
            ok = st.login();
        } catch (InitSocketException ex) {
            st.closeAll();
            return false;
        }
        if (!ok) {
            return false;
        }
        return true;
    }

    private boolean enterHVMode(SSocketTools st, String hv) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + "switchvm " + hv);
            if (st.readLine(CMD.INFO).equals("OK")) {
                return true;
            } else {
                return false;
            }

        } catch (IOException ex) {
            st.closeAll();
            return false;
        } catch (IllegalStatement ex) {
            st.closeAll();
            return false;
        }
    }

    private boolean createMachine(SSocketTools st, Element vm, String path, String gpath) {

        String osCode = vm.getAttributeValue("os.key") + " " + vm.getAttributeValue("osv.key");
        if (vm.getAttributeValue("osv.arc").equals("64")) {
            osCode += " 64";
        }
        try {
            st.sendLine(CMD.CHARS.QUESTION + "createvm \"" + vm.getName() + "\" \""
                    + osCode + "\"");
            String answer = st.readLine();
            if (answer.isEmpty()) {
                errors.add(new String[]{path, gpath, "Unknown answer"});
                st.closeAll();
                return false;
            }
            char idc = answer.charAt(0);
            answer = answer.substring(1);
            if (idc == CMD.INFO) {
                if (answer.equals("BADARGS")) {
                    errors.add(new String[]{path, gpath, "Bad args sended! It can be a version conflict"});
                } else if (answer.equals("ERROR")) {
                    errors.add(new String[]{path, gpath, "There was an error to create vm"});
                }
                st.closeAll();
                return false;
            } else if (idc == CMD.CHARS.ANSWER) {
                vm.setAttribute("server.uuid", answer);
            }
        } catch (IOException ex) {
            errors.add(new String[]{path, gpath, "Connection was closed unexpectedly"});
            st.closeAll();
            return false;
        }
        return true;
    }

    private boolean setMemory(SSocketTools st, Element vm, String path, String gpath) {
        String memory = vm.getAttributeValue("mem");
        String uuid = vm.getAttributeValue("server.uuid");
        try {
            st.sendLine(CMD.CHARS.QUESTION + "setmemory " + uuid + " " + memory);
            String answer = st.readLine(CMD.INFO);
            if (answer.equals("OK")) {
                return true;
            }
            if (answer.equals("BADARGS")) {
                errors.add(new String[]{path, gpath, "Bad args sended! It can be a version conflict"});
            } else if (answer.equals("ERROR")) {
                errors.add(new String[]{path, gpath, "There was an error to set memory"});
            }
        } catch (IllegalStatement ex) {
            errors.add(new String[]{path, gpath, "Blasar answers with unexpected character. ANSWER :: " + ex.getMessage()});
        } catch (IOException ex) {
            errors.add(new String[]{path, gpath, "Connection was closed unexpectedly"});
        }
        st.closeAll();
        return false;
    }

    private boolean addStorage(SSocketTools st, Element vm, String path, String gpath) {
        
        String uuid = vm.getAttributeValue("server.uuid");
        Element devices = vm.getChild("dev");
        List<Element> modules = devices.getChildren();
        for (Element module : modules) {
            String typeStorage = getTypeStorage(module.getAttributeValue("id"));
            if (typeStorage == null) {
                errors.add(new String[]{path, gpath, "TypeStorage value is incorrect. It seems data is corrupted"});
                st.closeAll();
                return false;
            }
            String id = module.getAttributeValue("type");
            String cache = module.getAttributeValue("cache");
            try {
                parent.setInfo("Adding module " + module.getAttributeValue("name") +"... ");
                st.sendLine(CMD.CHARS.QUESTION + "addstorage " + uuid + " " + typeStorage
                        + " " + id + " " + cache);
                String answer = st.readLine(CMD.INFO);
                if (answer.equals("BADARGS")) {
                    errors.add(new String[]{path, gpath, "Bad args sended! It can be a version conflict"});
                    st.closeAll();
                    return false;
                } else if (answer.equals("ERROR")) {
                    errors.add(new String[]{path, gpath, "There was an error to set memory"});
                    st.closeAll();
                    return false;
                }
                if (!addDisk(st, module, path, gpath)) {
                    return false;
                }
            } catch (IllegalStatement ex) {
                errors.add(new String[]{path, gpath, "Blasar answers with unexpected character. ANSWER :: " + ex.getMessage()});
            } catch (IOException ex) {
                errors.add(new String[]{path, gpath, "Connection was closed unexpectedly"});
                st.closeAll();
                return false;
            }
        }
        creat.showPBDisk(false);
        return true;
    }

    private boolean addDisk(SSocketTools st, Element module, String path, String gpath) {
        try {
            String answer = st.readLine(CMD.QUESTION);
            if (!answer.equals("addimage?")) {
                errors.add(new String[]{path, gpath, "Blasar answers with unexpected command. Bad Version?"});
                st.closeAll();
                return false;
            }
            List<Element> images = module.getChildren();
            for (Element image : images) {
                st.sendLine(CMD.CHARS.ANSWER + "y");
                answer = st.readLine(CMD.QUESTION);
                if (!answer.equals("data?")) {
                    errors.add(new String[]{path, gpath, "Blasar answers with unexpected command. Bad Version?"});
                    st.closeAll();
                    return false;
                }
                if (!addDevice(st, image, path, gpath)) {
                    errors.add(new String[]{path, gpath, "Error setting vm devices"});
                    st.closeAll();
                    return false;
                }
                answer = st.readLine(CMD.QUESTION);
                if (!answer.equals("addimage?")) {
                    errors.add(new String[]{path, gpath, "Blasar answers with unexpected command. Bad Version?"});
                    st.closeAll();
                    return false;
                }
            }
            st.sendLine(CMD.CHARS.ANSWER + "n");
            answer = st.readLine(CMD.INFO);
            if (!answer.equals("END")) {
                errors.add(new String[]{path, gpath, "Images were sent successfully. But we got an incorrect answer from server."
                            + " Maybe VM will be corrupted!    Msg got: " + answer});
                st.closeAll();
            }
        } catch (IOException ex) {
            errors.add(new String[]{path, gpath, "Connection was closed unexpectedly"});
            st.closeAll();
            return false;
        } catch (IllegalStatement ex) {
            errors.add(new String[]{path, gpath, "Blasar answers with unexpected character while we add a image. ANSWER :: " + ex.getMessage()});
            st.closeAll();
            return false;
        }
        return true;
    }

    private boolean addDevice(SSocketTools st, Element image, String path, String gpath) {
        String name = image.getAttributeValue("name");
        parent.setInfo("Adding image " + name + "...");
        creat.changeCurrentImage("/kuasar/plugin/deployer/icons/animation/creating/creating");
        Short slot;
        try {
            slot = Short.parseShort(image.getAttributeValue("slot"));
        } catch (NumberFormatException ex) {
            errors.add(new String[]{path, gpath, "Data Corrupted: Slot is not a short number."});
            st.closeAll();
            return false;
        }
        String filepath = image.getAttributeValue("file");
        String type = image.getAttributeValue("type");
        String passthrough = image.getAttributeValue("passtrhough");
        if (passthrough == null) {
            passthrough = "false";
        }
        if (filepath == null) {
            if (!addEmptyDevice(st, slot, type, passthrough)) {
                errors.add(new String[]{path, gpath, "Error trying to create a empty device called " + name});
                return false;
            }
        } else {
            File file = new File(filepath);
            if (!sendImage(st, name, file, slot, type, passthrough, image)) {
                errors.add(new String[]{path, gpath, "Error trying to send the device called " + name});
                return false;
            }

        }
        return true;

    }

    private boolean sendImage(SSocketTools st, String name, File file, Short slot, String type, String passthrough, Element image) {
        parent.setInfo("Making SHA1-Hash of "+ name +"...   (It may take several minutes)");
        creat.changeCurrentImage("/kuasar/plugin/deployer/icons/animation/hash/hash");
        String sha1 = Utils.getSHA1(file);
        if (sha1 == null) {
            return false;
        }
        try {
            st.sendLine(CMD.CHARS.ANSWER + "\"" + name + "\" " + type + " " + slot + " " + passthrough + " " + sha1 + " " + file.length());
            String answer = st.readLine();
            if (answer.length() < 1) {
                return false;
            }
            char cc = answer.charAt(0);
            answer = answer.substring(1);
            if (cc == '#') {
                return false;
            }
            if (!answer.equals("BINARY")) {
                return false;
            }
            if (!sendBinary(st, file)) {
                return false;
            }
            parent.setInfo("Server is checking the sent file... Be await...");
            creat.changeCurrentImage("/kuasar/plugin/deployer/icons/animation/hash/hash");
            answer = st.readLine();
            if (answer.length() < 1) {
                return false;
            }
            cc = answer.charAt(0);
            answer = answer.substring(1);
            if (cc == '#') {
                return false;
            }
            image.setAttribute("servername", answer);
            answer = st.readLine(CMD.INFO);
            if (answer.equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            return false;
        } catch (IllegalStatement ex) {
            return false;
        }
    }

    private boolean sendBinary(SSocketTools st, File file) {
        try {
            long size = file.length();
            parent.setInfo("Sending file " + file.getAbsolutePath() + " ["+size + " bytes ]...");
            creat.changeCurrentImage("/kuasar/plugin/deployer/icons/animation/creating/creating");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] data = new byte[size > BUFFER_SIZE ? BUFFER_SIZE : (int) (size)];
            long i = 0;
            while (bis.read(data) > 0) {
                st.Send(data);
                i += data.length;
                if (size - i < BUFFER_SIZE) {
                    data = new byte[(int) (size - i)];
                }
                if(i%(BUFFER_SIZE*100)==0){
                    creat.setValueDisk(Math.round((float)((float)i/(float)size*100)));
                }
            }
            bis.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private boolean addEmptyDevice(SSocketTools st, Short slot, String type, String passthrough) {
        try {
            st.sendLine(CMD.CHARS.ANSWER + "\"NULL.NULL\" " + type + " " + slot + " " + passthrough + " 0 -1");
            String answer = st.readLine(CMD.INFO);
            if (answer.equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            return false;
        } catch (IllegalStatement ex) {
            return false;
        }
    }

    private String getTypeStorage(String id) {
        int num = -1;
        try {
            num = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return null;
        }
        switch (num) {
            case 0:
                return "ide";
            case 1:
                return "sata";
            case 2:
                return "scsi";
            case 3:
                return "sas";
            case 4:
                return "floppy";
            default:
                return null;
        }
    }

    private boolean addIf(SSocketTools st, Element vm, String path, String gpath) {
        String uuid = vm.getAttributeValue("server.uuid");
        Element net = vm.getChild("net");
        List<Element> interfaces = net.getChildren();
        int i = 0;
        for (Element interfac : interfaces) {
            String type = getNICType(interfac.getAttributeValue("type"));
            String mac = getMac(i, uuid);
            try {
                st.sendLine(CMD.CHARS.QUESTION + "addnic " + uuid + " " + i++ + " " + type + " " + mac);
                String answer = st.readLine(CMD.INFO);
                if (answer.equals("ERROR")) {
                    errors.add(new String[]{path, gpath, "There was an error to add a network inteface"});
                    st.closeAll();
                    return false;
                } else if (answer.equals("BADARGS")) {
                    errors.add(new String[]{path, gpath, "Bad args sended! It can be a version conflict"});
                    st.closeAll();
                    return false;
                }
            } catch (IOException ex) {
                errors.add(new String[]{path, gpath, "Connection was closed unexpectedly"});
                st.closeAll();
                return false;
            } catch (IllegalStatement ex) {
                errors.add(new String[]{path, gpath, "Blasar answers with unexpected character while we add a network interface. ANSWER :: " + ex.getMessage()});
                st.closeAll();
                return false;
            }
        }
        return true;
    }

    private boolean setNetwork(SSocketTools st, Element vm, String path, String gpath) {
        String uuid = vm.getAttributeValue("server.uuid");
        String op = vm.getAttributeValue("op");
        String opUser = vm.getAttributeValue("op.user");
        String opPwd = vm.getAttributeValue("op.password");

        Element net = vm.getChild("net");
        List<Element> interfaces = net.getChildren();
        if (interfaces.isEmpty()) {
            return true;
        }
        String mac = getMac(0, uuid);
        String ip = vm.getAttributeValue("ipv4");
        String mask;
        if (ip == null) {
            ip = vm.getAttributeValue("ipv6");
            if (ip == null) {
                return false;
            }
            mask = vm.getAttributeValue("prefix");
        } else {
            mask = vm.getAttributeValue("mask");
        }
        String gw = vm.getAttributeValue("gw");
        String dns = vm.getAttributeValue("dns");
        try {
            st.sendLine(CMD.CHARS.QUESTION + "setnetwork " + uuid + " \"" + op + "\" \""
                    + opUser + "\" \"" + opPwd + "\" " + mac + " " + ip + " " + mask +
                    " " + gw + " " + dns);
            String answer = st.readLine(CMD.INFO);
            if (answer.equals("BADARGS")) {
                errors.add(new String[]{path, gpath, "Bad args sended! It can be a version conflict"});
                st.closeAll();
            } else if (answer.equals("ERROR")) {
                errors.add(new String[]{path, gpath, "There was an error to create vm"});
                st.closeAll();
            }else if(answer.equals("OK")){
                return true;
            }
            return true;
        } catch (IOException ex) {
            errors.add(new String[]{path, gpath, "Connection was closed unexpectedly"});
            st.closeAll();      
        } catch (IllegalStatement ex) {
            errors.add(new String[]{path, gpath, "Blasar answers with unexpected character while we add a network interface. ANSWER :: " + ex.getMessage()});
            st.closeAll();
        }
        return false;
    }

    private String getNICType(String type) {
        int id = 0;
        try {
            id = Integer.parseInt(type);
        } catch (NumberFormatException ex) {
            return "null";
        }
        switch (id) {
            case 0:
                return "nat";
            case 1:
                return "bridged";
            case 2:
                return "intnet";
            case 3:
                return "hostonly";
            default:
                return "null";
        }
    }

    private String getMac(int id, String uuid) {
        String aux = uuid.substring(uuid.length() - 12);
        String mac = "";
        for (int i = 0; i < aux.length(); i++) {
            if (i == 1) {
                mac += id * 2;
            } else {
                mac += aux.charAt(i);
            }
        }
        return mac;
    }

    private void closeConnection(SSocketTools st) {
        try {
            st.sendLine(CMD.CHARS.QUESTION+"exit");
            st.sendLine(CMD.CHARS.QUESTION + "exit");
        } catch (SocketException ex) {
        } catch (IOException ex) {
        }
        st.closeAll();
    }
}
