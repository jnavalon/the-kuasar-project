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

import blasar.Config;
import blasar.Services.Exceptions.IllegalStatement;
import blasar.Services.SocketTools;
import java.io.IOException;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class VMService{
    PluginInterface plugin;
    SocketTools st;
    VMCommands intercom;

    public VMService(PluginInterface plugin, SocketTools st) {
        this.plugin =plugin;
        this.intercom = plugin.getInterCom();
        this.st = st;
    }
    
    public boolean splitter(SocketTools st) throws IOException{
        StringTokenizer cmd;
        try {
            cmd = new StringTokenizer(st.readLine(Config.CMD.QUESTION));       
        } catch (IllegalStatement ex) {
            st.sendLine(Config.CMD.CHARS.INFO + "OUTVM");
            return true;
        }
        if(cmd == null) return true;
        if(!cmd.hasMoreTokens()) return false;
        String command = cmd.nextToken().toLowerCase();
        if(command.isEmpty()) return true;
        if(command.equals("getfreespace"))
            getFreeSpace();
        else if(command.equals("getmachines"))
            getMachines();
        else if (command.equals("getdefaultfolder"))
            getSysProperties("DefMachFolder");
        else if(command.equals("createvm"))
            createVM(cmd);
        else if(command.equals("addstoragectl"))
            addStorageCtl(cmd);
        else
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR"); 
        return false;
    }
    
    private void getMachines() throws SocketException, IOException{
        st.Send(Config.CMD.CHARS.ANSWER, intercom.getRegisteredMachines());    
    }
    
    private void getSysProperties(String key) throws SocketException{
       st.sendLine(Config.CMD.CHARS.ANSWER + intercom.getSysProperites(key));
    }
    
    private void getFreeSpace() throws SocketException{
        st.sendLine(Config.CMD.CHARS.ANSWER + Long.toString(intercom.getFreeSpace()));
    }

    private void createVM(StringTokenizer cmd) throws SocketException {
        String os = null;
        String name = null;
        while(cmd.hasMoreTokens()){
            String option = cmd.nextToken();
            if(option.length()>1){
                if(option.startsWith("-")){
                    if(cmd.hasMoreTokens()){
                        switch(option.toCharArray()[1]){
                            case 'n':
                                name = cmd.nextToken().trim();
                                break;
                            case 'o':
                                os= cmd.nextToken().trim();
                                break;
                        }
                    }
                }
            }
        }
        if(name == null || os == null){
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        if(intercom.createvm(name, os)){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
    }

    private void addStorageCtl(StringTokenizer cmd) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    
}