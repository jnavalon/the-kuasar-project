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
        if(passthrought){
            return(Hypervisor.execute("storageattach " + uuid + " --storagectl " + storagectl 
                    + " --port " + port + " --device " + device + " --type " 
                    + type + " --medium " + filepath + " -- passthrough on") ==0 ? true : false);
        }else{
            return(Hypervisor.execute("storageattach " + uuid + " --storagectl " + storagectl 
                    + " --port " + port + " --device " + device + " --type " 
                    + type + " --medium " + filepath) ==0 ? true : false);
        }
        
    }
    
    public static boolean addNIC(String uuid, String nicID, String type) {
        return(Hypervisor.execute("modifyvm " + uuid + " --nic" + nicID + " " + type) ==0 ? true : false );
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
    
}

