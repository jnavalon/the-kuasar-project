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
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //<Test>
        Config.verbose=true;
        //</Test>
        System.setProperty("javax.net.ssl.keyStore", "/home/jnavalon/key");
        System.setProperty("javax.net.ssl.keyStorePassword", "blasar");
        if(!setArgs(args)) return;
        Server server = new Server();
        server.start();
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
        }
        if (help) {
            showHelp();
        }
        return !help;
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
                    Config.bind = ip;
                    return true;
                } else {
                    return false;
                }
            case 'l':
                if(isFile(value)){
                    Config.log = true;
                    Config.logFile = value;
                    return true;
                }else{
                    return false;
                }
            case 'p':
                if (isPort(value)) {
                    Config.port = Integer.parseInt(value);
                    return true;
                } else {
                    return false;
                }
            case 'U':
                if (isNumber(value)) {
                    try {
                        Config.max_users = Integer.parseInt(value);
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
                    Config.interactive = true;
                    break;
                case 'v':
                    Config.verbose = true;
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

        String ranks[] = value.split("[.]");
        int number = 0;
        if (ranks.length != 4) {
            System.err.println("Argument Bind doesn't have a well-known ip format.");
            return false;
        }
        for (String rank : ranks) {
            try {
                number = Integer.parseInt(rank);
            } catch (NumberFormatException ex) {
                System.err.println("Unknown Bind Address.");
                return false;
            }
            if (number < 0 || number > 255) {
                System.err.println("IP values out of range [0 - 255]");
                return false;
            }
        }
        return true;

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
