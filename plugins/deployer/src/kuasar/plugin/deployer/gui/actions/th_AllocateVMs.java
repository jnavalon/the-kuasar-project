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

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import kuasar.plugin.Global.CMD;
import kuasar.plugin.deployer.Config;
import kuasar.plugin.deployer.gui.pn_Allocating;
import kuasar.plugin.utils.Connection;
import kuasar.plugin.utils.SSocketTools;
import kuasar.plugin.utils.SSocketTools.IllegalStatement;
import kuasar.plugin.utils.SSocketTools.InitSocketException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class th_AllocateVMs extends Thread {


    public class UnknownAnswer extends Exception {

        UnknownAnswer(String msg) {
            super(msg);
        }
    }
    private pn_Allocating parent;
    private ArrayList<String[]> addresses;
    private String ks;
    private char[] kspwd;
    private String user;
    private char[] userpwd;
    private HashMap<String, TreeSet<String[]>> vmdata;
    private HashMap<String, TreeSet<String[]>> serverdata = new HashMap<String, TreeSet<String[]>>();
    private boolean ramfirst;
    private SSocketTools st;
    private ArrayList<String[]> errors = new ArrayList<String[]>();
    private ArrayList<String[]> allocatedVM = new ArrayList<String[]>();
    private boolean stop = false;

    public th_AllocateVMs(ArrayList<String[]> addresses, HashMap<String, TreeSet<String[]>> vmdata, boolean ramfirst, pn_Allocating parent) {
        this.parent = parent;
        this.addresses = addresses;
        this.vmdata = vmdata;
        this.ramfirst = ramfirst;
    }
    
    public void cleanStop(){
        stop = true;
    }

    @Override
    public void run() {

        /*
         * map = Object[]
         *          [0] : Total RAM (Long)
         *          [1] : Free RAM  (Long)
         *          [2] : HashMap<String, Long> Where Key is the Engine code & Value is the free Space
         */

        HashMap<String, Object[]> map = new HashMap<String, Object[]>();

        for (String[] address : addresses) {
            parent.setInfo("Sacanning " + address[0] + " server");
            if(stop) return;
            int port;
            try {
                port = Integer.parseInt(address[1]);
            } catch (NumberFormatException ex) {
                port = 46600;
            }

            if (!map.containsKey(address[0])) {
                loadResources(address[0], port, map);
            }
        }
        parent.setInfo("Reordering data...");
        reorderByEngineCode(map);
        map.clear();
        if(stop) return;
        parent.setInfo("Estimating chances...");
        tryAllocate();
        if(stop) return;
        if(errors.size()>0){
            parent.showErrors(errors);
        }
        else{
            parent.setInfo("All VMs were allocated successfully!");
            parent.finished(allocatedVM);
        }
    }

    private void loadResources(String address, int port, HashMap<String, Object[]> map) {
        boolean ok;
        loadSecrets(address);
        st = new SSocketTools(address, port, ks, kspwd, user, userpwd);
        st.initSocket();
        try {
            ok = st.login();
        } catch (InitSocketException ex) {
            st.closeAll();
            return;
        }
        if (!ok) {
            return;
        }
        ArrayList<String> vma = new ArrayList<String>();
        try {
            st.sendLine(CMD.CHARS.QUESTION + "listvm");
            int total = st.readInt(CMD.INFO);
            for (int i = 0; i < total; i++) {
                vma.add(st.readLine(CMD.ANSWER));
                if(stop) return;
            }
        } catch (Exception ex) {
            return;
        }

        long totalRAM, freeRAM;
        HashMap<String, Long> data = new HashMap<String, Long>();
        try {
            st.sendLine(CMD.CHARS.QUESTION + "$MEM:get_total");
            totalRAM = st.readLong(CMD.ANSWER);
            st.sendLine(CMD.CHARS.QUESTION + "$MEM:get_free");
            freeRAM = st.readLong(CMD.ANSWER);
        } catch (Exception ex) {
            return;
        }

        for (String vm : vma) {
            try {
                data.put(vm, getVMAvailableSpace(vm));
            } catch (Exception ex) {
                return;
            }
        }
        try {
            st.sendLine(CMD.CHARS.QUESTION + "exit");
            st.readLine();
        } catch (SocketException ex) {}
        catch (IOException ex){}
        st.closeAll();
        Object[] odata = new Object[3];
        odata[0] = totalRAM;
        odata[1] = freeRAM;
        odata[2] = data;
        map.put(address, odata);
    }

    private long getVMAvailableSpace(String vm) throws SocketException, IOException, IllegalStatement, UnknownAnswer {
        st.sendLine(CMD.CHARS.QUESTION + "switchvm " + vm);
        String answer = st.readLine(CMD.INFO);
        if (!answer.equals("OK")) {
            return -1;
        }
        st.sendLine(CMD.CHARS.QUESTION + "getfreespace");
        long freeSpace = -1;
        try {
            freeSpace = st.readLong(CMD.ANSWER);
        } catch (Exception ex) {
            return -1;
        }
        st.sendLine(CMD.CHARS.QUESTION + "exit");
        if (!st.readLine(CMD.INFO).equals("OUTVM")) {
            throw new UnknownAnswer("Bad exit");
        }
        return freeSpace;
    }

    private void loadSecrets(String address) {
        ks = Connection.getKeyStore(address);
        kspwd = Connection.getKeyStorePWD(address);
        userpwd = Connection.getUserPwd(address);

        if (ks == null) {
            ks = Config.gkeystore;
            if (ks == null) {
                //NOTIFY ERROR
                return;
            }
        }

        if (kspwd == null) {
            kspwd = Config.gkssecret;
            if (kspwd == null) {
                //NOTIFY ERROR
                return;
            }
        }

        if (!Connection.isDNIe(address)) {
            user = Connection.getUserName(address);
            if (user == null) {
                user = Config.guser;
                if (user == null) {
                    //NOTIFY ERROR
                    return;
                }
            }

        } else {
            user = "";
        }
        if (userpwd == null) {
            userpwd = Config.gusersecret;
            if (userpwd == null) {
                //NOTIFY ERROR
                return;
            }
        }
    }

    private void reorderByEngineCode(HashMap<String, Object[]> map) {
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String[] sdata = new String[4];
            Object[] data = map.get(key);
            sdata[0] = key;
            sdata[1] = Long.toString((Long) data[0]);
            sdata[3] = Long.toString((Long) data[1]);
            HashMap<String, Long> imgsz = (HashMap<String, Long>) data[2];
            Iterator<String> ikeys = imgsz.keySet().iterator();
            while (ikeys.hasNext()) {
                String ikey = ikeys.next();
                sdata[2] = Long.toString(imgsz.get(ikey));
                TreeSet<String[]> list;
                if (!serverdata.containsKey(ikey)) {
                    list = new TreeSet<String[]>(new CompareArrays(ramfirst));
                } else {
                    list = serverdata.get(ikey);
                }
                list.add(sdata);
                serverdata.put(ikey, list);
                if(stop) return;
            }
        }
    }

    private void tryAllocate() {
        Iterator<String> it = vmdata.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            TreeSet<String[]> vms = vmdata.get(key);
            TreeSet<String[]> servers = serverdata.get(key);
            if (servers == null) {
                errors.add(new String[]{"", "Servers!", "There aren't " + key + " hypervisor on selected servers"});
                return;
            }
            

            Iterator<String[]> ivms = vms.descendingIterator();
            Iterator<String[]> iservers = servers.descendingIterator();
            ArrayList<String[]> avms = new ArrayList<String[]>();
            ArrayList<String[]> aservers = new ArrayList<String[]>();
            
            while (ivms.hasNext()) {
                avms.add(ivms.next());
            }
            while (iservers.hasNext()) {
                aservers.add(iservers.next());
            }
            
            ivms = null;
            iservers = null;
            servers.clear();
            vms.clear();
            while (avms.size() > 0) {
                ArrayList<ArrayList<Integer>> availability = new ArrayList<ArrayList<Integer>>();
                for (int i = 0; i < avms.size(); i++) {
                    ArrayList<Integer> intersect = new ArrayList<Integer>();
                    Long vimgsz = Long.parseLong(avms.get(i)[2]);
                    Long vram = Long.parseLong(avms.get(i)[3]);
                    boolean safe = Boolean.parseBoolean(avms.get(i)[4]);

                    for (int j = 0; j < aservers.size(); j++) {
                        Long simgsz = Long.parseLong(aservers.get(j)[2]);
                        Long sram = Long.parseLong(aservers.get(j)[safe ? 3 : 1]);
                        if ((vimgsz <= simgsz) && (vram <= sram)) {
                            intersect.add(j);
                        }
                    }
                    availability.add(intersect);
                }
                int selected = -1;
                int ssize = -1;
                for (int i = 0; i < availability.size(); i++) {
                    int size = availability.get(i).size();
                    if (size == 0) {
                        errors.add(new String[]{avms.get(i)[0], avms.get(i)[1], "There aren't servers availables to stay vm"});
                    } else {
                        if (selected == -1) {
                            selected = i;
                            ssize = size;
                        }
                        if (size < ssize) {
                            selected = i;
                        }
                    }
                }
                if(selected>=0){
                    allocatedVM.add(new String[]{avms.get(selected)[0], aservers.get(availability.get(selected).get(0))[0]});
                    String[] toreplace = aservers.get(availability.get(selected).get(0));
                    toreplace[2] = Long.toString( Long.parseLong(toreplace[2]) - Long.parseLong(avms.get(selected)[2]));
                    toreplace[3] = Long.toString(Long.parseLong(toreplace[3]) - Long.parseLong(avms.get(selected)[3]));
                    aservers.set(availability.get(selected).get(0), toreplace);
                    avms.remove(selected);
                    for(String[] data : aservers){
                        servers.add(data);
                    }
                    Iterator<String[]> i = servers.descendingIterator();
                    aservers.clear();
                    while(i.hasNext()){
                        aservers.add(i.next());
                    }
                }
                if(stop) return;
            }
            if(stop) return;
        }
    }
}
