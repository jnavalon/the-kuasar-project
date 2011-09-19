/*
 *  Copyright (C) 2010 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
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
package blasar;

import blasar.Services.Server;
import blasar.util.Encryptation;
import blasar.util.plugins.PluginScan;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*<Test>
        Config.BLASAR.verbose=true;
        Config.BLASAR.interactive=true;
        </Test>*/

        SetVariables sv = new SetVariables();
        if(!sv.isOK()){
            System.err.println("Blasar couldn't load all required variables and won't continue");
            System.exit(1);
        }
        PluginScan plugins = new PluginScan();
        System.setProperty("javax.net.ssl.keyStore", "/home/jnavalon/blasar.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "blasar");
        if(!setArgs(args)) return;
        Server server = new Server();
        server.start();
        if(Config.BLASAR.interactive){
            blasar.console.InitConsole console = new blasar.console.InitConsole(server);
        }else{
            try {
                server.join();
            } catch (InterruptedException ex) {}
        }
        System.exit(0);
    }

    private static boolean setArgs(String[] args) {
        boolean help = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (isSimple(args[i])) {
                    if (i + 1 < args.length) {
                        if (!setSimple(args[i], args[i+1])) {
                            help = true;
                        }
                    }else{
                        if (!setComplex(args[i])) {
                            help = true;
                        }
                    }
                } else {
                    if (!setComplex(args[i])) {
                        help = true;
                    }
                }
            }
            if(args[i].equals("genhash")){
                printHash();
                return false;
            }
        }
        if (help) {
            showHelp();
        }
        return !help;
    }

    private static void printHash(){
        Console terminal = System.console();
        if(terminal == null){
            System.out.println("Error found a usable terminal. Impossible to continue.");
            return;
        }
        char[] passwd = null;
        char[] confirm = null;
        int i=0;
        do{
           passwd = terminal.readPassword("password: ");
           confirm = terminal.readPassword("again: ");
           i++;
           if(!Arrays.equals(passwd, confirm))
               System.out.println("Passwords aren't equals. Try" + (i<3 ? " again." :" later"));
        }while(!Arrays.equals(passwd, confirm) && i < 3);
        if( i>= 3) return;
        try {
            System.out.println("HASH:\t\t" + Encryptation.getSHA512(passwd) );
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("MD5 or SHA1 is not supported. Imposssible to generate Hash String");
        }
        
        
    }

    private static boolean isSimple(String arg) {
        return (arg.length() == 2);
    }

    private static boolean setSimple(String att, String value) {

        String aux;
        aux = att.substring(1);
        char character[] = aux.toCharArray();
        switch (character[0]) {
            case 'b':
                if (isIP(value)) {
                    InetAddress ip = getIP(value);
                    if (ip == null) {
                        return false;
                    }
                    Config.BLASAR.bind = ip;
                    return true;
                } else {
                    return false;
                }
            case 'l':
                if(isFile(value)){
                    Config.BLASAR.log = true;
                    Config.BLASAR.logFile = value;
                    return true;
                }else{
                    return false;
                }
            case 'p':
                if (isPort(value)) {
                    Config.BLASAR.port = Integer.parseInt(value);
                    return true;
                } else {
                    return false;
                }
            case 'U':
                if (isNumber(value)) {
                    try {
                        Config.BLASAR.max_users = Integer.parseInt(value);
                        return true;
                    } catch (NumberFormatException Ex) {
                        System.err.println("Max User, maybe was too high number.");
                        return false;
                    }
                } else {
                    System.err.println("Max users value isn't a number.");
                    return false;
                }
            default:
                return setComplex(att);
        }
    }

    private static boolean setComplex(String att) {
        att = att.substring(1);
        char characters[] = att.toCharArray();
        for (char character : characters) {
            switch (character) {
                case 'i':
                    Config.BLASAR.interactive = true;
                    break;
                case 'v':
                    Config.BLASAR.verbose = true;
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

    private static InetAddress getIP(String value) {
        String[] ip = value.split("[.]");
        byte ipa[] = new byte[4];
        if (ipa.length != ip.length) {
            return null;
        }
        try {
            InetAddress bind = InetAddress.getByAddress(new byte[]{ipa[0], ipa[1], ipa[2], ipa[3]});
            return bind;
        } catch (UnknownHostException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    private static boolean isFile(String path){
        File file = new File(path);
        if(!file.exists()){
            try {
                if(!file.createNewFile())
                    return false;
            } catch (IOException ex) {
                System.err.println("Log file couldn't be created:\t" + ex.getMessage());
                return false;
            }
        }
        if(!file.canWrite()){
            System.err.println("Log file isn't writable! Check permissions or access!");
            return false;
        }
        return true;
    }

    private static boolean isIP(String value) {
        try {
            InetAddress.getByName(value);
            return true;
        } catch (UnknownHostException ex) {
            return false;
        }

    }

    private static boolean isNumber(String value) {
        char character[] = value.toCharArray();
        for (int i = 0; i < character.length; i++) {
            if (!Character.isDigit(character[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPort(String value) {
        int port;
        try {
            port = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            System.err.println("Argument port isn't a int number.");
            return false;
        }
        if (port > -1 && port < 65535) {
            return true;
        } else {
            System.err.println("Argument port isn't a port number accepted (0 - 65535)");
            return false;
        }
    }

    private static void showHelp() {
        System.err.println("\nUsage blasar.jar [OPTIONS]\n\n");
        System.err.println("Options:\n");
        System.err.println("\t-b #BIND_ADD \t IP Mask to restrict remote connections");
        System.err.println("\t-i \t\t Interactive Mode (CLI)");
        System.err.println("\t-p #PORT \t blasar will listen on selected port");
        System.err.println("\t-U #MAX_USERS \t Number of connections allowed");
        System.err.println("\t-h \t\t Show this help");
        System.err.println("\t-v \t\t Show extra messages (Verbose)");
        System.err.println("\n\n");
        System.err.println("Values:\n");
        System.err.println("\t BIND_ADD: \t IP format; Exemple (0.0.0.0) to allow all remote connections");
        System.err.println("\t PORT: \t\t [0 - 65535] On *nix systems ports besides 1 and 1023 could need root privileges ");
        System.err.println("\t\t\t Warning! If you select port 0 on *nix. OS will select the next available port.");
        System.err.println("\t\t\t Add verbose option to show the selected port");
        System.err.println("\t MAX_USERS: \t 0 (no limit) ");
        System.err.println("\n");
    }
}
