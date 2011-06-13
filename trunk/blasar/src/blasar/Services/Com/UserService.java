/*
 *  Copyright (C) 2011 jnavalon
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package blasar.Services.Com;

import blasar.Config;
import blasar.Config.CMD.CHARS;
import blasar.Services.Com.vms.PluginInterface;
import blasar.Services.Exceptions.IllegalStatement;
import blasar.Services.SocketTools;
import java.io.IOException;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class UserService {
    private SocketTools st;
    private PluginInterface curPlugin = null;

    public UserService(SocketTools st) throws IOException, SocketException {
        boolean exit = false;
        this.st = st;
        st.setSocketTime(0);
        st.sendLine(CHARS.INFO +"Good to see you again " + st.getUser() + "!");
        while(!exit){
            StringTokenizer cmd;
            try {
                cmd = new StringTokenizer(st.readLine(Config.CMD.ANSWER));
                exit=splitter(cmd);
            } catch (IllegalStatement ex) {
                exit=true;
            }
        }
    }

    private boolean splitter(StringTokenizer cmd) {
        if(cmd==null) return false;
        if(!cmd.hasMoreElements()) return false;
        String act = cmd.nextToken().toLowerCase();
        if(act.equals("exit")){
            return true;
        }else if(act.equals("createvm")) {
            //createVM(cmd);
        }else if(act.equals("listvm")){
            
        }
            
        return true;
    }

}
