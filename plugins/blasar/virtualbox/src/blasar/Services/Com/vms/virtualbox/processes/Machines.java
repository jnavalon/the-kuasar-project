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
package blasar.Services.Com.vms.virtualbox.processes;

import blasar.Services.Com.vms.virtualbox.Config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Machines {
    /*
     * Return vm registered on VirtualBox Hypervisor
     */
    
    public static String[] getRegisteredMachines() {
        return Extractor.extractRegMach(Hypervisor.getStream("list vms"));
    }
    
    public static String[] getRunningMachines(){
        return Extractor.extractRunMachUUID(Hypervisor.getStream("list runningvms"));
    }

    public static String getSysProperties(String key) {
        if (Extractor.sysproperties == null) {
            Extractor.extractSysProperties(Hypervisor.getStream("list systemproperties"));
        }
        return Extractor.sysproperties.get(key);
    }

    public static String createMachine(String os, String preferredName) {
        preferredName = checkName(preferredName);
        String ostype = getOS(os);
        if(ostype==null) return null;
        
        BufferedReader br = Hypervisor.getStream("createvm --name \""+ preferredName + "\" --ostype " + ostype + " --register");
        if (br == null) {
            return null;
        }
        String line, uuid = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("UUID:")) {
                    uuid = line.substring(line.indexOf(':') + 1, line.length()).trim();
                }
            }
        } catch (IOException ex) {
        }
        if (uuid == null) {
            return null;
        }
        setVideoRAM(uuid, 32);
        return uuid;
    }
    
    public static boolean setMemory(String uuid, Long memory){
        return (Hypervisor.execute("modifyvm "+ uuid + " --memory " + memory)==0)? true: false;
    }
    
    public static boolean setStorageCtl(String name, String uuid, String type, String controller, boolean cache){
        return(Hypervisor.execute("storagectl " + uuid + " --name \"" + name + "\" --add "
                + type + " --controller " + controller + " --hostiocache " + (cache ? "on" : "off"))==0? true: false);
    }
    public static boolean addStorage(String uuid, String storagectl, int port, int device, String type, String filepath, boolean passthrought){
        if(type.equals("hdd")){
            Hypervisor.execute("internalcommands sethduuid " + filepath);
        }
        if(passthrought){
            return(Hypervisor.execute("storageattach " + uuid + " --storagectl \"" + storagectl 
                    + "\" --port " + port + " --device " + device + " --type " 
                    + type + " --medium " + filepath + " -- passthrough on") ==0 ? true : false);
        }else{
            return(Hypervisor.execute("storageattach " + uuid + " --storagectl \"" + storagectl 
                    + "\" --port " + port + " --device " + device + " --type " 
                    + type + " --medium " + filepath) ==0 ? true : false);
        }
        
    }
    
    public static boolean addEmptyDevice(String uuid, String storagectl, int port, int device, String type, boolean passthrought){
        if(passthrought){
            return(Hypervisor.execute("storageattach " + uuid + " --storagectl \"" + storagectl 
                    + "\" --port " + port + " --device " + device + " --type " 
                    + type + " --medium emptydrive -- passthrough on") ==0 ? true : false);
        }else{
            return(Hypervisor.execute("storageattach " + uuid + " --storagectl \"" + storagectl 
                    + "\" --port " + port + " --device " + device + " --type " 
                    + type + " --medium emptydrive") ==0 ? true : false);
        }
    }
    
    public static boolean addNIC(String uuid, int nicID, String type, String mac) {
        String eargs="";
        if(type.equals("bridged")){
            eargs="--bridgeadapter" + nicID + " " + Config.bridgedif;
        }else if(type.equals("hostonly")){
            eargs="--hostonlyadapter" + nicID + " " + Config.hostoif;
        }
        
        boolean ok=(Hypervisor.execute("modifyvm " + uuid + " --nic" + nicID + " " + type + 
                " " + eargs) ==0 ? true : false );
        if(!ok) return false;
        return (Hypervisor.execute("modifyvm " + uuid + " --macaddress" + nicID + " " + mac) ==0 ? true : false );
    }

    protected synchronized void wakeUP(){
        this.notify();
    }

    private static String checkName(String name) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(getRegisteredMachines()));
        int count = 0;
        if (!list.contains(name)) {
            return name;
        }
        while (list.contains(name + "_" + count)) {
            count++;
        }
        return name + "_" + count;
    }

    private static String getOS(String kuasarOS) {
        if (kuasarOS == null) {
            return null;
        }
        InputStream in = Extractor.class.getResourceAsStream("/blasar/Services/Com/vms/virtualbox/EqVMs");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        String os = null;
        boolean stop = false;
        try {
            while ((line = br.readLine()) != null && !stop) {
                if (!line.isEmpty()) {
                    if (line.charAt(0) != '#') {
                        if (line.substring(0, line.indexOf('=')).equals(kuasarOS)) {
                            os = line.substring(line.indexOf('=') + 1);
                            stop = true;
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
        return os;
    }
    public static boolean setNetwork(String uuid, String operator, String username, String password, String mac, String ip, String mask, String gw, String dns){
        boolean wasRunning = false;
        if(!isRunning(uuid)){
            if(Hypervisor.execute("startvm " + uuid + " --type gui")!=0) return false;
        }else{
            wasRunning = true;
        }
        WaitingRoom wr = new WaitingRoom();
        String cmd = "guestcontrol execute " + uuid + " \"" + operator + "\" --arguments \"ipchange " + mac + " "
                + ip + " " + mask + " " + gw + " " + dns +"\" --username \"" + username + "\" --password \"" + password + "\"";
        boolean ok = wr.waitForRun(cmd, 20, 15);
        if(!wasRunning && isRunning(uuid)){
            Hypervisor.acpiPowerMachine(uuid);
            if(!wr.waitForShutdown(uuid, 10, 30)){
                Hypervisor.powerOffMachine(uuid);
            }
            
        }
        return ok;
        
        
    }
    public static boolean isRunning(String uuid){
        String[] rvms = Machines.getRunningMachines();
        boolean running = false;
        for(int i = 0; i<rvms.length && !running; i++){
            String rvm= rvms[i];
            if(rvm.equals(uuid)){
             return true;  
            }
        }
        return false;
    }
    public static boolean setVideoRAM(String uuid, int vram){
        return (Hypervisor.execute("modifyvm "+ uuid + " --vram " + vram)==0)? true: false;
    }
}

