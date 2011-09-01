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
package kuasar.plugin.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.smartcardio.CardException;
import kuasar.plugin.Intercom.ODR;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Connection {
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
    public static final String KEYSTORE = ".keystore";
    public static final String KS_PASSWD = ".kspass";
    public static final String USERNAME = ".user";
    public static final String PASSWD = ".pass";
    public static final String DNIE = ".dnie";
    
    public static int timeout = 250;
    
    /*
     * Return:
     * -1X : (See checkDNIe function)
     * -2X : (see checkUser function)
     * -1: Server Unknown;
     * 0 : Error connection;
     * 1 : Server OK!
     * 2 : Client OK!
     */

    public static int checkServer(String netaddress, int port, String keystore, char[] kspassword, String user, char[] passwd, boolean checkUser) {
        return checkServer(netaddress, port, keystore, kspassword, user, passwd, checkUser, Connection.timeout);
    }
    /*
     * If user is empty will be authenticated with DNIe
     * 
     */
    public static int checkServer(String netaddress, int port, String keystore, char[] kspassword, String user, char[] passwd, boolean checkUser, int timeout) {

        SSLSocket ssocket = getServerSocket(netaddress, port, keystore, kspassword, timeout);
        if (ssocket == null) {
            return -1;
        }
        int status;
        PrintStream ps = null;
        BufferedReader br = null;
        try {
            ssocket.setSoTimeout(0);
            ps = new PrintStream(ssocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
            if (!isServerOnline(br, ps)) {
                return 0;
            }
            if (!checkUser) {
                return 1;
            }
            ssocket.close();
            ssocket = getServerSocket(netaddress, port, keystore, kspassword, timeout);
            ps = new PrintStream(ssocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
            br.readLine();
            br.readLine();
            if (user.isEmpty()) {
                status = checkDNIe(br, ps, passwd);
                if (status < 0) {
                    return status;
                }
            } else {
                status = checkUser(br, ps, user, passwd);
                if (status < 0) {
                    return status;
                }
            }
            return 2;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return 0;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                }
            }
            try {
                ssocket.close();
            } catch (IOException ex) {
            }
        }

    }
    
    /*
     * Return:
     * 1: OK
     * -10 : Error Connection
     * -11: Error KeyStore
     * -12: Error Algorithm
     * -13: Error Certificate
     * -14: Error Card
     * -15: Bad PIN
     * -16: Error getting Key
     * -17: Bad SIGN
     * -18: Bad Server
     * -19: Bad User
     */
    public static int checkDNIe(BufferedReader br, PrintStream ps, char[] passwd) {
        try {
            DNIe.setPIN(passwd);
            ps.println(CMD.CHARS.ANSWER + "2");
            String ticket = br.readLine();
            if (ticket.charAt(0) != CMD.CHARS.QUESTION) {
                return -18;
            }
            ticket = ticket.substring(1);
            String nif = null;
            try {
                nif = DNIe.getNIF(DNIe.getCertificate());
            } catch (KeyStoreException ex) {
                System.err.println(ex.getMessage() + "\n\tCause: " + ex.getCause().getMessage());
                return -11;
            } catch (NoSuchAlgorithmException ex) {
                System.err.println(ex.getMessage());
                return -12;
            } catch (CertificateException ex) {
                System.err.println(ex.getMessage());
                return -13;
            } catch (CardException ex) {
                System.err.println(ex.getMessage());
                return -14;
            } catch (ProviderException ex) {
                System.err.println(ex.getMessage());
                return -14;
            }

            ps.println(CMD.CHARS.ANSWER + nif);
            byte[] sign = null;
            try {
                sign = DNIe.sign(ticket);
            } catch (NoSuchAlgorithmException ex) {
                return -12;
            } catch (CardException ex) {
                return -14;
            } catch (InvalidKeyException ex) {
                return -15;
            } catch (KeyStoreException ex) {
                return -11;
            } catch (CertificateException ex) {
                return -13;
            } catch (UnrecoverableKeyException ex) {
                return -16;
            } catch (SignatureException ex) {
                return -17;
            }

            ps.println("<" + sign.length);
            ps.write(sign);
            if (!br.readLine().contains("Good to see you")) {
                return -19;
            }
            return 1;
        } catch (IOException ex) {
            return -10;
        }

    }

    /*
     * Return:
     * 1: OK
     * -20: CONNECTION ERROR
     * -21: BAD USER/PASSWORD
     * -22: BAD SERVER
     */
    public static int checkUser(BufferedReader br, PrintStream ps, String user, char[] passwd) {
        try {
            ps.println(CMD.CHARS.ANSWER+"1");
            String line = br.readLine();
            if (!line.equals(CMD.CHARS.QUESTION + "Username?")) {
                return -22;
            }
            ps.println(CMD.CHARS.ANSWER + user);
            line = br.readLine();
            if (!line.equals(CMD.CHARS.QUESTION + "Passwd?")) {
                return -22;
            }
            ps.println(CMD.CHARS.ANSWER + String.valueOf(passwd));
            line = br.readLine();
            if (!line.contains("Good to see you")) {
                return -21;
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return -20;
        }
        return 1;
    }

    public static boolean isServerOnline(BufferedReader br, PrintStream ps) {
        try {
            String line = null;
            line = br.readLine();

            if (!line.contains("The kuasar project's daemon")) {
                return false;
            }
            line = br.readLine();
            if (!line.contains("setAUTH")) {
                return false;
            }
            ps.println(CMD.CHARS.ANSWER + "0");
            br.readLine();
            return true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public static String getErrorDescription(int error){
        switch (error) {
            case 0:
            case -10:
            case -20:
                return "Error connection";
            case -1:
                return "Server unknown";
            case -11:
                return "Error KeyStore";
            case -12:
                return "Error algorithm";
            case -13:
                return "Error certificate";
            case -14:
                return "Error card";
            case -15:
                return "Bad PIN";
            case -16:
                return "Error getting key";
            case -17:
                return "Bad sign";
            case -18:
            case -22:
                return "Bad server";
            case -19:
                return "Bad user";
            case -21:
                return "Bad user or password";
            default:
                return "Unknown error";
        }
    }

    public static SSLSocket getServerSocket(String netaddress, int port, String keystore, char[] password, int timeout) {
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.trustStorePassword", String.valueOf(password));
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket ssocket;
        try {
            Socket checkTCP = new Socket();
            SocketAddress sockaddr = new InetSocketAddress(netaddress, port);
            checkTCP.connect(sockaddr, timeout);
            checkTCP.close();
        } catch (Exception ex) {
            return null;
        }
        try {

            ssocket = (SSLSocket) sslsocketfactory.createSocket(netaddress, port);
            return ssocket;
        } catch (IOException ex) {
            return null;
        }
    }

    public static boolean tryConnect(String address, int port) {
        Socket socket = null;
        try {
            socket = new Socket(address, port);

        } catch (Exception ex) {
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                }
            }
        }
        return true;
    }

    public static boolean saveKeyStore(String source, String new_name) {
        File files = new File(source);
        if (!files.exists()) {
            return false;
        }
        File filed = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + (String) ODR.getValue("$KEYSTOREDIR")
                + File.separator + new_name + (String) ODR.getValue("$KEYSTORE_EXT"));
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            if(filed.exists())
                filed.delete();
            in = new FileInputStream(files);
            out = new FileOutputStream(filed);
            int bytes;
            while ((bytes = in.read()) != -1) {
                out.write(bytes);
            }
            return true;
        } catch (SecurityException ex) {
            System.err.println("I couldn't delete old keystore. Cause: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println("I couldn't copy keystore. Cause: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("I couldn't write in destiny file. Cause: " + ex.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
            }
        }
        return false;
    }

    public static boolean saveKSPassword(char[] password, String filename) {
        if (password == null || filename == null) {
            return false;
        }
        if (password.length == 0) {
            return false;
        }
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + (String) ODR.getValue("$KEYSTOREDIR")
                + File.separator + filename + (String) ODR.getValue("$KEYSTORE_PWD_EXT"));
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("password=" + String.valueOf(password));
        } catch (IOException ex) {
            System.err.println("I couldn't save the keystore's password. Cause: " + ex.getMessage());
            return false;
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
            }
        }
        return true;
    }

    public static boolean delKSPassword(String filename) {
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR")
                + (String) ODR.getValue("$KEYSTOREDIR") + File.separator + filename
                + (String) ODR.getValue("$KEYSTORE_EXT"));
        if (file.exists()) {
            return file.delete();
        }
        loadKSSecrets();
        return true;
    }

    public static boolean delKeyStore(String filename) {
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + 
                (String) ODR.getValue("$KEYSTOREDIR") + File.separator + filename + (String) ODR.getValue("$KEYSTORE_EXT"));
        if (file.exists()) {
            return file.delete();
        }
        loadKSSecrets();
        return true;
    }

    public static String getKeyStore(String address) {
        HashMap map = (HashMap) ODR.getValue("#KS_SECRET");
        String path = (String)map.get(address + KEYSTORE);
        return path;
    }

    public static char[] getKeyStorePWD(String address) {
        HashMap map = (HashMap) ODR.getValue("#KS_SECRET");
        char[] pwd = (char[]) map.get(address + KS_PASSWD);
        return pwd;
    }
    
    
    public static void loadKSSecrets() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        ODR.setValue("#KS_SECRET", map);
        
        File dir = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + (String) ODR.getValue("$KEYSTOREDIR"));
        if (!dir.exists()) {
            dir.mkdir();
            return;
        }
        File[] files = dir.listFiles(new KSFilter());
        for(File file : files){
            String address = file.getName().substring(0, file.getName().lastIndexOf("."));
            map.put(address + KEYSTORE, file.getAbsolutePath());
            map.put(address + KS_PASSWD, loadPWDFile(address + (String) ODR.getValue("$KEYSTORE_PWD_EXT")));
        }
        ODR.setValue("#KS_SECRET", map);
    }
    
    @SuppressWarnings("unchecked")
    public static void addUserSecret(String address, String user, char[] password, boolean dnie){
        HashMap<String,Object> map = (HashMap<String,Object>) ODR.getValue("#USER_SECRET");
        map.put(address + USERNAME, user);
        map.put(address + PASSWD, password);
        map.put(address + DNIE, dnie);
        ODR.setValue("#USER_SECRET", map);
    }
    
    @SuppressWarnings("unchecked")
    public static String getUserName(String address){
        HashMap<String,Object> map = (HashMap<String,Object>) ODR.getValue("#USER_SECRET");
        return (String) map.get(address + USERNAME);
    }
    
    @SuppressWarnings("unchecked")
    public static char[] getUserPwd(String address){
        HashMap<String,Object> map = (HashMap<String,Object>) ODR.getValue("#USER_SECRET");
        return (char[]) map.get(address + PASSWD);
    }
    
    @SuppressWarnings("unchecked")
    public static boolean isDNIe(String address){
        HashMap<String,Object> map = (HashMap<String,Object>) ODR.getValue("#USER_SECRET");
        Object data = map.get(address + DNIE);
        return data ==null ? false : (Boolean) map.get(address + DNIE);
    }
    
    public static void delUserSecret(String address){
        HashMap map = (HashMap) ODR.getValue("#USER_SECRET");
        map.remove(address + USERNAME);
        map.remove(address + PASSWD);
        map.remove(address + DNIE);
        ODR.setValue("#USER_SECRET", map);
    }
    
    private static char[] loadPWDFile(String name){
        char[] passwd = null;
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + File.separator +
               (String) ODR.getValue("$KEYSTOREDIR") + File.separator + name);
        if(!file.exists()) return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while(br.ready()){
                String line = br.readLine();
                if(line.startsWith("password")){
                    passwd = line.substring(line.indexOf("=") + 1).toCharArray();
                }
            }
        } catch (Exception ex) {
            return null;
        }finally{
            try{
                br.close();
            }catch(IOException ex){}
        }
        return passwd;
    }
     public static class KSFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
                if (file.getName().toLowerCase().endsWith((String) ODR.getValue("$KEYSTORE_EXT"))) {
                    return true;
                }
            return false;
        }
    }
}

