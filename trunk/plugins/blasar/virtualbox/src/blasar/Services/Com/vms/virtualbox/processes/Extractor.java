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
import java.util.HashMap;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Extractor {
    protected static HashMap<String,String> sysproperties = null;
    protected static String[] extractRegMach(BufferedReader br){
        if(br == null) return new String[]{};
        ArrayList<String> vmnames= new ArrayList<String>();
        String line;
        try {
            while ((line = br.readLine()) != null){
                line = line.trim();
                if(line.startsWith("\"")){
                   line = line.substring(1);
                   vmnames.add(line.substring(0, line.indexOf('\"')));
                }
            }
        } catch (IOException ex) {
            return new String[]{};
        }
        String[] machines = new String[vmnames.size()];
        return vmnames.toArray(machines);
    }
    
    protected static String[] extractInterfaces(BufferedReader br){
        if(br ==null) return new String[]{};
        ArrayList<String> interfaces= new ArrayList<String>();
        String line;
        try {
            while ((line = br.readLine()) != null){
                if(line.startsWith("Name:")){
                    interfaces.add(line.substring(5).trim());
                }
            }
        } catch (IOException ex) {
            return new String[]{};
        }
        String[] ifs = new String[interfaces.size()];
        return interfaces.toArray(ifs);
    }
    
    protected static String[] extractRunMachUUID(BufferedReader br){
        if(br == null) return new String[]{};
        ArrayList<String> vmuuids= new ArrayList<String>();
        String line;
        try {
            while ((line = br.readLine()) != null){
                line = line.trim();
                if(line.startsWith("\"")){
                   line = line.substring(line.indexOf('{')+1);
                   vmuuids.add(line.substring(0, line.length()-1));
                }
            }
        } catch (IOException ex) {
            return new String[]{};
        }
        String[] machines = new String[vmuuids.size()];
        return vmuuids.toArray(machines);
    }
    
    protected static void extractSysProperties(BufferedReader br){
        sysproperties = new HashMap<String,String>();
        if(br == null) return;
        String line;
        String key;
        try {
            while ((line = br.readLine()) != null){
                key=getKey(line.substring(0,line.indexOf(':')));
               if(key!=null){
                   sysproperties.put(key, line.substring(line.indexOf(':')+1).trim());
               }
            }
        } catch (IOException ex) {
        }
    }
    
    private static String getKey(String datakey){
        if(datakey==null) return null;
        InputStream in = Extractor.class.getResourceAsStream("/blasar/Services/Com/vms/virtualbox/processes/keyIndex");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        String key=null;
        boolean stop = false;
        try {
            while((line=br.readLine())!=null && !stop){
                if(line.substring(line.indexOf('=')+1).equals(datakey)){
                    key = line.substring(0, line.indexOf('='));
                    stop=true;
                }
            }
        } catch (IOException ex) {
        }
        return key;
    }
}
