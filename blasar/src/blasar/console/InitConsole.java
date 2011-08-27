/*
 *  Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
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

package blasar.console;

import blasar.Config;
import blasar.Services.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class InitConsole {
    protected static Server server;
    protected static boolean su = false;
    public  InitConsole(Server server){
        InitConsole.server = server;
        boolean exit =false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Blasar console (" + Config.BLASAR.VERSION + ") :\n");
        String command = null;
        do{
            printHeaderLine();
            try {
                command = br.readLine();
                if(command!=null)
                    command = command.trim();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println("Blasar got an error while reads a command line. Blasar will be closed cleanly!");
                exit = true;
            }
            exit=analyze(command);
        }while(!exit);
    }
    private boolean analyze(String command){
        if(command == null) return true;
        if(command.isEmpty()) return false;
        if(command.equals("exit"))
            if (su)
                return true;
            else{
                System.out.println("For security reasons, only Admin can close blasar. Please use \"su\" command to login as Admin");
                return false;
            }
        boolean status =false;
        if(su)
            status |= RedirectCommands.RedirectSU(command);
        status |= RedirectCommands.Redirect(command);
        if(!status)System.out.println("Command not found!");
        return false;
    }

    private void printHeaderLine(){
        System.out.print(getPrintableAddress() + (su ? " # " : " > "));
    }

    private String getPrintableAddress(){
        ArrayList<String[]> interfaces = server.getInterfaces();
        if(interfaces == null) return "!";
        for(int i=0; i<interfaces.size(); i++){
            String[] ipaddresses = interfaces.get(i)[1].split(" ");
            for(String ipaddress : ipaddresses)
                if(!ipaddress.trim().isEmpty()) return ipaddress.trim();
        }
        return "!";

    }
}
