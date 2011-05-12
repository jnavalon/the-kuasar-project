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
import blasar.util.Encryptation;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class RedirectCommands {
    protected static boolean Redirect(String command){
        StringTokenizer st = new StringTokenizer(command);
        if(st.countTokens() ==0) return false;
        String token = st.nextToken().toLowerCase();
        if(token.equals("show"))
            ShowCommands.Redirect(st);
        else if(token.equals("su"))
            setSU();
        else if(token.equals("?"))
            printHelp();
        else
            return false;
        return true;
    }

        private static boolean setSU(){
        if(!checkPassFile()){
            System.out.println("Password is not set, However access is granted. Be careful.");
            InitConsole.su = true;
            return true;
        }
        Console terminal = System.console();
        if(terminal == null){
            System.out.println("Error found a usable terminal. Impossible to login as Admin.");
            return false;
        }
        for(int i=0;i<3;i++ ){
            char[] passwd = terminal.readPassword("Password: ");

            String SHA = null;
            try {
                SHA = Encryptation.getSHA512(passwd);
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Oups! I can't use SHA Encrypt System. I'm sorry but access is not granted.");
                return false;
            }

            if(checkPass(SHA)){
                InitConsole.su=true;
                return true;
            }
            System.out.println("Access Denied! " + (i<2 ? "Try again." : "Access will be notified"));

        }
        return false;
    }

    private static boolean checkPass(String hash){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Config.BLASAR.startDir + Config.BLASAR.passSUFile));
            String line = null;
            try {
                while ((line = br.readLine().trim()) != null) {
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        return hash.equals(line) ? true : false;
                    }
                }
            } catch (IOException ex) {
                System.out.println("Impossible to read password file. Please check permissions and try again. Access is not granted.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File was deleted while you try login to admin. Access is not granted now, but you can try again later.");
        }

        return false;

    }

    private static boolean checkPassFile(){
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.passSUFile);
        if(!file.exists()) return false;
        if(!file.canRead()) return false;
        if(file.canWrite()){
            System.out.println("Warning! Be careful, your password file has writeable access. Check the file and change its permissions");
        }
        return true;
    }
    protected static boolean RedirectSU(String command){
        StringTokenizer st = new StringTokenizer(command);
        if(st.countTokens() ==0) return false;
        String token = st.nextToken().toLowerCase();
        if(token.equals("logout"))
            InitConsole.su = false;
        else if(token.equals("vmuser"))
            VMUserCommands.Redirect(st);
        else if(token.equals("kill"))
            KillCommands.kill(st);
        else if(token.equals("killall"))
            KillCommands.killAll();
        else
            return false;
        return true;

    }
    private static void printHelp(){
        System.out.println("\tAvailable commands:\n");

        System.out.println("\t\texit    \tExit and close the server. All connections will be closed.");
        System.out.println("\t\tshow <?>\tShow information about server and its connexions.");
        System.out.println("\t\tsu      \tLogin as SuperUser (Admin) ");
        System.out.println();
         if(InitConsole.su){
            System.out.println("\tAvailable SU commands:\n");
            System.out.println("\t\tkill <ID>\tClose a active connection.");
            System.out.println("\t\tkillall  \tClose all active connections.");
            System.out.println("\t\tlogout   \tClose admin session. ");
            System.out.println("\t\tvmuser <?>  \t");
            System.out.println();
        }
    }
}
