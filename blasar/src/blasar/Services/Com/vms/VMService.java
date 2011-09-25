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
package blasar.Services.Com.vms;

import blasar.Services.SocketTools;
import blasar.Config;
import blasar.Services.Exceptions.IllegalStatement;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class VMService {

    private PluginInterface plugin;
    private SocketTools st;
    private VMCommands intercom;
    
    public final static short PW_START = 0;
    public final static short PW_RESET = 1;
    public final static short PW_POWEROFF = 2;
    public final static short PW_ACPIOFF = 3;

    public VMService(PluginInterface plugin, SocketTools st) {
        this.plugin = plugin;
        this.intercom = plugin.getInterCom();
        this.st = st;
    }

    public boolean splitter(SocketTools st) throws IOException, SocketException, IllegalStatement {
        StringTokenizer cmd;
        try {
            cmd = new StringTokenizer(st.readLine(Config.CMD.QUESTION));
        } catch (IllegalStatement ex) {
            st.sendLine(Config.CMD.CHARS.INFO + "OUTVM");
            return true;
        }
        if (cmd == null) {
            return true;
        }
        if (!cmd.hasMoreTokens()) {
            return false;
        }
        String command = cmd.nextToken().toLowerCase();
        if (command.isEmpty()) {
            return true;
        }
        if (command.equals("exit")) {
            st.sendLine(Config.CMD.CHARS.INFO + "OUTVM");
            return true;
        }
        if (command.equals("getfreespace")) {
            getFreeSpace();
        } else if (command.equals("getmachines")) {
            getMachines();
        } else if (command.equals("getdefaultfolder")) {
            getSysProperties("DefMachFolder");
        } else if (command.equals("createvm")) {
            createVM(cmd);
        } else if (command.equals("setmemory")) {
            setMem(cmd);
        } else if (command.equals("addstorage")) {
            addStorage(cmd);
        } else if (command.equals("addnic")) {
            addNIC(cmd);
        } else if (command.equals("setnetwork")) {
            setNetwork(cmd);
        } else if (command.equals("getenginename")) {
            getEngineName();
        } else if (command.equals("getmachinename")) {
            getMachineName(cmd);
        } else if (command.equals("getmachineuuid")) {
            getMachineUUID(cmd);
        } else if (command.equals("isrunning")) {
            isRunning(cmd);
        } else if (command.equals("startvm")) {
            sendPower(cmd,PW_START);
        } else if (command.equals("resetvm")) {
            sendPower(cmd,PW_RESET);
        } else if (command.equals("poweroffvm")) {
            sendPower(cmd,PW_POWEROFF);
        } else if (command.equals("acpioffvm")) {
            sendPower(cmd,PW_ACPIOFF);
        } else if (command.equals("deletevm")) {
            deleteVM(cmd);
        } else {
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
        return false;
    }
    
    private void deleteVM(StringTokenizer cmd) throws SocketException{
        if(cmd.countTokens()!=1){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        if(intercom.deleteVM(uuid)){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
    }
    
    private void sendPower(StringTokenizer cmd, short type) throws SocketException{
        if(cmd.countTokens()!=1){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        boolean status = false;
        switch(type){
            case PW_START:
                status=intercom.RunMachine(uuid);
                break;
            case PW_RESET:
                status=intercom.ResetMachine(uuid);
                break;
            case PW_POWEROFF:
                status=intercom.PowerOffMachine(uuid);
                break;
            case PW_ACPIOFF:
                status=intercom.ShutdownMachine(uuid);
                break;
        }
        if(status){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
        
    }
    
    private void getMachines() throws SocketException, IOException {
        st.Send(Config.CMD.CHARS.ANSWER, intercom.getRegisteredMachines());
    }

    private void getSysProperties(String key) throws SocketException {
        st.sendLine(Config.CMD.CHARS.ANSWER + intercom.getSysProperites(key));
    }

    private void getFreeSpace() throws SocketException {
        st.sendLine(Config.CMD.CHARS.ANSWER + Long.toString(intercom.getFreeSpace()));
    }

    private void createVM(StringTokenizer cmd) throws SocketException {
        String os = null;
        String name = null;
        if(cmd.countTokens()<2){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        name = st.getFullArguments(cmd);
        os = st.getFullArguments(cmd);
        if (name == null || os == null) {
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = intercom.createvm(name, os);
        if (uuid != null) {
            st.sendLine(Config.CMD.CHARS.ANSWER + uuid);
        } else {
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
    }

    private void setMem(StringTokenizer cmd) throws SocketException {
        if(cmd.countTokens()!=2){
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
            return;
        }
        String uuid=cmd.nextToken();
        long size=0;
        try{
            UUID.fromString(uuid);
            size = Long.parseLong(cmd.nextToken());
        }catch(Exception ex){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        if(intercom.setMemory(uuid, size))
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        else
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
    }

    private String getController(String typeStorage, int id) {
        if(typeStorage.equals("ide")){
            switch(id){
                case 0: return "PIIX3";
                case 1: return "PIIX4";
                case 2: return "ICH6";
            }
        }else if(typeStorage.equals("scsi")){
            switch(id){
                case 0: return "LSILogic";
                case 1: return "BusLogic";
            }
        }else if(typeStorage.equals("sata")){
            return "IntelAHCI";
        }else if(typeStorage.equals("sas")){
            return "LSILogicSAS";
        }else if(typeStorage.equals("floppy")){
            return "I82078";
        }
        return null;
    }

    private void addStorage(StringTokenizer cmd) throws SocketException, IOException, IllegalStatement {
        if(cmd.countTokens()!= 4){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        String typeStorage = cmd.nextToken();
        int id; //TYPE OF CONTROLLER Exemple: IDE 0: PIIX3, 1: PIIX4, 2: ICH6
        try{
            id = Integer.parseInt(cmd.nextToken());
        }catch(NumberFormatException ex){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String controller = getController(typeStorage, id);
        boolean cache = Boolean.parseBoolean(cmd.nextToken());
        AddStorage as = new AddStorage(uuid, typeStorage, controller, cache, intercom);
        
        if(as.addStorageCtl()){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
            return;
        }
        as.addImages(st);
    }

    private void addNIC(StringTokenizer cmd) throws SocketException {
        if(cmd.countTokens()!= 4){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        int nicID = 0;
        try{
            nicID = Integer.parseInt(cmd.nextToken());   
        }catch(NumberFormatException ex){
             st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
             return;
        }
        nicID++;
        String type = cmd.nextToken();
        String mac = cmd.nextToken();
        if(intercom.addNIC(uuid, nicID, type, mac)){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
    }

    private void setNetwork(StringTokenizer cmd) throws SocketException {
        if(cmd.countTokens() < 9){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        String op = st.getFullArguments(cmd);
        String user = st.getFullArguments(cmd);
        String pwd = st.getFullArguments(cmd);
        String mac = cmd.nextToken();
        String ip = cmd.nextToken();
        String mask = cmd.nextToken();
        String gw = cmd.nextToken();
        String dns = cmd.nextToken();
        try{
            InetAddress.getByName(ip);
        }catch(Exception ex){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        if(intercom.setNetwork(uuid, op, user, pwd, mac,ip, mask, gw, dns)){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
    }

    private void getEngineName() throws SocketException{
        st.sendLine(Config.CMD.CHARS.ANSWER + intercom.getEngineName());
    }

    private void getMachineName(StringTokenizer cmd) throws SocketException {
        if(cmd.countTokens()!=1){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        String name = intercom.getMachineName(uuid);
        if(name == null){
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }else{
            st.sendLine(Config.CMD.CHARS.ANSWER + name);
        }
    }

    private void getMachineUUID(StringTokenizer cmd) throws SocketException {
        if(cmd.countTokens()<1){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String name = st.getFullArguments(cmd);
        String uuid = intercom.getMachineUUID(name);
        if(uuid == null){
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }else{
            st.sendLine(Config.CMD.CHARS.ANSWER + uuid);
        }
    }

    private void isRunning(StringTokenizer cmd) throws SocketException {
        if(cmd.countTokens()!=1){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String uuid = cmd.nextToken();
        if(intercom.isRunning(uuid)){
            st.sendLine(Config.CMD.CHARS.ANSWER + "true");
        }else{
            st.sendLine(Config.CMD.CHARS.ANSWER + "false");
        }
    }
}
