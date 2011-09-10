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
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Hypervisor {

    
    public static boolean startMachine(String uuid){
        return(execute("startvm " + uuid + " --type headless") ==0 ? true : false );
    }
    public static boolean powerOffMachine(String uuid){
        return(execute("controlvm " + uuid + " poweroff") ==0 ? true : false );
    }
    public static boolean acpiPowerMachine(String uuid){
        return(execute("controlvm " + uuid + " acpipowerbutton") ==0 ? true : false );
    }
    public static int execute(String args) {
        String[] aargs = argsToArray(args);
        try {
            Process p = new ProcessBuilder(aargs).start();
            p.waitFor();
            return p.exitValue();
        } catch (IOException ex) {
            return -1;
        } catch (IllegalThreadStateException itse) {
            return -2;
        } catch (InterruptedException ex) {
            return -1;
        }
    }

    public static BufferedReader getStream(String args) {
        String[] aargs = argsToArray(args);
        try {
            Process p = new ProcessBuilder(aargs).start();
            //Process p = Runtime.getRuntime().exec(cmd);
            return new BufferedReader(new InputStreamReader(p.getInputStream()));
        } catch (IOException ex) {
            return null;
        }
    }

    private static String[] argsToArray(String args) {
        ArrayList<String> alargs = new ArrayList<String>();
        alargs.add(Config.application);
        alargs.add("-q");
        String[] aux = args.split(" ");
        for (int i = 0; i < aux.length; i++) {
            String arg = aux[i];
            if (arg.startsWith("\"")) {
                while (!arg.endsWith("\"") && i < aux.length) {
                    arg += " " + aux[++i];
                }
                arg = arg.substring(1);
                if (arg.endsWith("\"")) {
                    arg = arg.substring(0, arg.length() - 1);
                }
            }
            alargs.add(arg);
        }
        String[] aargs = new String[alargs.size()];
        alargs.toArray(aargs);
        return aargs;
    }
}
