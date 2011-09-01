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
import blasar.Info;
import blasar.Services.Com.vms.PluginInterface;
import blasar.Services.Com.vms.SysCommands;
import blasar.Services.Com.vms.VMService;
import blasar.Services.Exceptions.IllegalStatement;
import blasar.Services.SocketTools;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class UserService {

    private SocketTools st;
    VMService service = null;

    public UserService(SocketTools st) throws IOException, SocketException {
        boolean exit = false;
        this.st = st;
        st.setSocketTime(0);
        st.sendLine(CHARS.INFO + "Good to see you again " + st.getUser() + "!");
        Info.showMessage("User " + st.getUser() + " logged successfully from " + st.getRemoteIP());
        while (!exit) {
            if (service == null) {
                StringTokenizer cmd;
                try {
                    cmd = new StringTokenizer(st.readLine(Config.CMD.QUESTION));
                    exit = splitter(cmd);
                } catch (IllegalStatement ex) {
                    exit = true;
                }
            } else {
                if (service.splitter(st)) {
                    service = null;
                }
            }
        }
    }

    private boolean splitter(StringTokenizer cmd) throws SocketException, IOException {
        if (cmd == null) {
            return false;
        }
        if (!cmd.hasMoreTokens()) {
            return false;
        }

        String act = cmd.nextToken().toLowerCase();
        if (act.equals("exit")) {
            st.sendLine(CHARS.INFO + "BYE");
            return true;
        }else if(act.charAt(0) == '$'){
            return !SysCommands.splitter(act.substring(1), cmd ,st);
        } else if (act.equals("listvm")) {
            sendVMs();
        } else if (act.equals("switchvm")) {
            switchVM(cmd);
        }

        return false;
    }

    private void switchVM(StringTokenizer cmd) throws SocketException {
        if (!cmd.hasMoreTokens()) {
            st.sendLine(CHARS.INFO + "ERROR");
        }
        String name = cmd.nextToken();
        if (loadValidVM(name)) {
            st.sendLine(CHARS.INFO + "OK");
        } else {
            st.sendLine(CHARS.INFO + "BAD");
        }
    }

    private boolean loadValidVM(String vm) throws SocketException {
        HashMap<String, PluginInterface> plugins = Config.BLASAR.plugins;
        for (String key : plugins.keySet()) {
            PluginInterface pi = plugins.get(key);
            if (pi.getEngine().equals(vm)) {
                service = new VMService(pi, st);
                return true;
            }
        }
        return false;
    }

    private void sendVMs() throws SocketException {
        HashMap<String, PluginInterface> plugins = Config.BLASAR.plugins;
        st.sendLine("" + CHARS.INFO + plugins.size());
        for (String key : plugins.keySet()) {
            PluginInterface pi = plugins.get(key);
            st.sendLine(CHARS.ANSWER + pi.getEngine());
        }
    }
}
