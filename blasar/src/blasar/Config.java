/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blasar;

import blasar.Services.Com.vms.PluginInterface;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Config {

    public final class CMD {

        public final static short NULL = 0;
        public final static short ANSWER = 1;
        public final static short QUESTION = 2;
        public final static short INFO = 3;

        public final class CHARS {

            public final static char ANSWER = '<';
            public final static char QUESTION = '>';
            public final static char INFO = '#';
        }
    }
    public static final class BLASAR{

        public final static String passSUFile = "supasswd";
        public final static String passFile = "passwd";
        public final static String DNIeChar = "*"; //USED TO IDENTIFY A DNIE PASSWORD ON passFile
        public final static String VERSION = "0.01d";
        public final static String PublicKeyDIR = "pks/";
        public final static String PublicKeyEXT =".pk"; //Plublic key extension
        public final static String PluginsDIR = "plugins/";
        public final static String PluginEXT =".jet";
        public static String startDir;
        public static int port = 46600;
        public static int max_users = 50;
        public static int max_wait = 10000;
        public static InetAddress bind = getBind();
        public static boolean verbose = false;
        public static boolean interactive = false;
        public static boolean log = false;
        public static String logFile = null;
        public static int online = 0;
        public static HashMap<String, PluginInterface> plugins = new HashMap<String,PluginInterface>();
    }

    

    private static InetAddress getBind() {
        try {
            return InetAddress.getByAddress(new byte[]{0, 0, 0, 0});
        } catch (UnknownHostException ex) {
            return null;
        }
    }
}
