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
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        if (!sv.isOK()) {
            System.err.println("Blasar couldn't load all required variables and won't continue");
            System.exit(1);
        }
        PluginScan plugins = new PluginScan();
        loadConfig();
        if (!setArgs(args)) {
            return;
        }
        Server server = new Server();
        server.start();
        if (Config.BLASAR.interactive) {
            blasar.console.InitConsole console = new blasar.console.InitConsole(server);
        } else {
            try {
                server.join();
            } catch (InterruptedException ex) {
            }
        }
        System.exit(0);
    }

    private static boolean setArgs(String[] args) {
        boolean help = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (isSimple(args[i])) {
                    if (i + 1 < args.length) {
                        if (!setSimple(args[i], args[i + 1])) {
                            help = true;
                        }
                    } else {
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
            if (args[i].equals("genhash")) {
                printHash();
                return false;
            }
        }
        if (help) {
            showHelp();
        }
        return !help;
    }

    private static void printHash() {
        Console terminal = System.console();
        if (terminal == null) {
            System.out.println("Error found a usable terminal. Impossible to continue.");
            return;
        }
        char[] passwd = null;
        char[] confirm = null;
        int i = 0;
        do {
            passwd = terminal.readPassword("password: ");
            confirm = terminal.readPassword("again: ");
            i++;
            if (!Arrays.equals(passwd, confirm)) {
                System.out.println("Passwords aren't equals. Try" + (i < 3 ? " again." : " later"));
            }
        } while (!Arrays.equals(passwd, confirm) && i < 3);
        if (i >= 3) {
            return;
        }
        try {
            System.out.println("HASH:\t\t" + Encryptation.getSHA512(passwd));
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
            case 'l':
                return setLog(value);
            case 'k':
                return setKeyStore(value);
            case 'p':
                return setPort(value);
            case 'w':
                return setKSPwd(value);
            case 'U':
                return setMaxUsers(value);
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
                    setInteractive();
                    break;
                case 'v':
                    setVerbose();
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

    private static boolean setPort(String value) {
        if (isPort(value)) {
            Config.BLASAR.port = Integer.parseInt(value);
            return true;
        } else {
            return false;
        }
    }

    private static boolean setKeyStore(String value) {
        File file = new File(value);
        if (!file.exists()) {
            System.err.println("KeyStore no exists");
            return false;
        }
        if (!file.canRead()) {
            System.err.println("KeyStore can't be readed.");
            return false;
        }
        System.setProperty("javax.net.ssl.keyStore", value);
        return true;
    }

    private static boolean setKSPwd(String value) {
        System.setProperty("javax.net.ssl.keyStorePassword", value);
        return true;
    }

    private static boolean isFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return false;
                }
            } catch (IOException ex) {
                System.err.println("Log file couldn't be created:\t" + ex.getMessage());
                return false;
            }
        }
        if (!file.canWrite()) {
            System.err.println("Log file isn't writable! Check permissions or access!");
            return false;
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
        System.err.println("\t-i \t\t Interactive Mode (CLI)");
        System.err.println("\t-k #KS_PATH  \t KeyStore to secure connection.");
        System.err.println("\t-w #KS_PWD  \t KeyStore password.");
        System.err.println("\t-p #PORT \t blasar will listen on selected port");
        System.err.println("\t-U #MAX_USERS \t Number of connections allowed");
        System.err.println("\t-h \t\t Show this help");
        System.err.println("\t-v \t\t Show extra messages (Verbose)");
        System.err.println("\n\n");
        System.err.println("Values:\n");
        System.err.println("\t BIND_ADD: \t IP format; Exemple (0.0.0.0) to allow all remote connections");
        System.err.println("\t KS_PWD: \t\t A path where there is a keystore. Eg: \"C:\\blasar.ks\"");
        System.err.println("\t KS_PWD: \t\t The keystore's password. Eg: blasar");
        System.err.println("\t PORT: \t\t [0 - 65535] On *nix systems ports besides 1 and 1023 could need root privileges ");
        System.err.println("\t\t\t Warning! If you select port 0 on *nix. OS will select the next available port.");
        System.err.println("\t\t\t Add verbose option to show the selected port");
        System.err.println("\t MAX_USERS: \t 0 (no limit) ");
        System.err.println("\n");
    }

    private static void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Config.BLASAR.startDir + Config.BLASAR.ConfigFile));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.charAt(0) != '#') {
                        String arg = line.substring(0, line.lastIndexOf('='));
                        String value = line.substring(line.lastIndexOf('=') + 1);
                        if (arg.equals("ks")) {
                            setKeyStore(value);
                        } else if (arg.equals("ksp")) {
                            setKSPwd(value);
                        } else if (arg.equals("port")) {
                            setPort(value);
                        } else if (arg.equals("maxusers")) {
                            setMaxUsers(value);
                        } else if (arg.equals("interactive")) {
                            setInteractive(value);
                        } else if (arg.equals("verbose")) {
                            setVerbose(value);
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
    }

    private static boolean setMaxUsers(String value) {
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
    }

    private static boolean setLog(String value) {
        if (isFile(value)) {
            Config.BLASAR.log = true;
            Config.BLASAR.logFile = value;
            return true;
        } else {
            return false;
        }
    }

    private static void setInteractive() {
        Config.BLASAR.interactive = true;
    }

    private static void setVerbose() {
        Config.BLASAR.verbose = true;
    }

    private static void setInteractive(String value) {
        try {
            if (Boolean.parseBoolean(value)) {
                setInteractive();
            }
        } catch (Exception ex) {
        }

    }

    private static void setVerbose(String value) {
        try {
            if (Boolean.parseBoolean(value)) {
                setVerbose();
            }
        } catch (Exception ex) {
        }
    }
}
