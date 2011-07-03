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
package kuasar.plugin.servermanager.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.smartcardio.CardException;
import kuasar.plugin.servermanager.Config;
import kuasar.plugin.utils.DNIe;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Utils {
    /*
     * Return:
     * -2 Error Send ping
     * -1 Bad Address
     * 0 Ping Fail
     * 1 Ping Successfull
     */

    public static int ping(String netaddress) {
        InetAddress ip;
        try {
            ip = InetAddress.getByName(netaddress);
        } catch (UnknownHostException ex) {
            return -1;
        }
        try {
            if (ip.isReachable(5000)) {
                return 1;
            } else {
                return 0;
            }
        } catch (IOException ex) {
            return -2;
        }
    }
    /*
     * Return:
     * -1X : (See checkDNIe function)
     * -2X : (see checkUser function)
     * -1: Server Unknow;
     * 0 : Error connection;
     * 1 : Server OK!
     * 2 : Client OK!
     */

    public static int checkServer(String netaddress, int port, String keystore, char[] kspassword, String user, char[] passwd, boolean checkUser) {
        
        SSLSocket ssocket = getServerSocket(netaddress, port, keystore, kspassword);
        if (ssocket == null) {
            return -1;
        }
        int status;
        PrintStream ps =null;
        BufferedReader br = null;
        try {
            ssocket.setSoTimeout(10000);
            ps = new PrintStream(ssocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
            if(!isServerOnline(br, ps)){
                return 0;
            }
            if(!checkUser)
                return 1;
            if(user.isEmpty()){
                status=checkDNIe(br,ps,passwd);
                if(status<0)
                    return status;
            }else{
                status=checkUser(br,ps,user,passwd);
                if(status<0)
                    return status;
            } 
            return 2;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return 0;
        }finally{
            if(ps!=null) ps.close();
            if(br!=null) try {br.close();} catch (IOException ex) {}
            try {ssocket.close();} catch (IOException ex) {}
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
    private static int checkDNIe(BufferedReader br, PrintStream ps, char[] passwd){
        try{
            DNIe.setPIN(passwd);
            ps.println("<2");
            String ticket = br.readLine();
            if(!ticket.startsWith(">"))
                return -18;
            ticket = ticket.substring(1);
            String nif = null;
            try {
                nif = DNIe.getNIF(DNIe.getCertificate());
            } catch (KeyStoreException ex) {
                System.err.println(ex.getMessage());
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
            }

            ps.println("<"+nif);
            byte[] sign = null;
            try {
                sign=DNIe.sign(ticket);
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
            if(!br.readLine().contains("Good to see you"))
                return -19;
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

    private static int checkUser(BufferedReader br, PrintStream ps, String user, char[] passwd) {
        try {
            ps.println("<1");
            String line =br.readLine();
            if(!line.equals(">Username?"))
                return -22;
            ps.println("<" + user);
            line=br.readLine();
            if(!line.equals(">Passwd?"))
                return -22;
            ps.println("<" + String.valueOf(passwd));
            line=br.readLine();
            if(!line.contains("Good to see you"))
                return -21;
        } catch (IOException ex) {
            return -20;
        }
        return 1;
    }

    /*
     * Return:
     * -1 Bad Cert/Server
     * 0 Error Connection
     * 1 Connection successfull
     */
    private static boolean isServerOnline(BufferedReader br, PrintStream ps) {
        try {
            String line = null;
            line = br.readLine();
           
            if (!line.contains("The kuasar project's daemon")) {
                return false;
            }
            line = br.readLine();
            if (!line.contains("setAUTH"))return false;
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static SSLSocket getServerSocket(String netaddress, int port, String keystore, char[] password) {
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.trustStorePassword", String.valueOf(password));
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket ssocket;
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

    public static String getKeyServer(String address) {
        String path = (String) Config.GlobalServerCFG.kssecrets.get(address + Config.KS_Secrets.KEYSTORE);
        if (path == null) {
            return Config.GlobalServerCFG.keyStore;
        }
        return path;
    }

    public static char[] getKeyServerPWD(String address) {
        char[] pwd = (char[]) Config.GlobalServerCFG.kssecrets.get(address + Config.KS_Secrets.KS_PASSWD);
        if (pwd == null) {
            return Config.GlobalServerCFG.ksPasswd;
        }
        return pwd;
    }

    public static boolean isIP(String address) {
        try {
            InetAddress net = InetAddress.getByName(address);
        } catch (UnknownHostException ex) {
            return false;
        }
        return true;
    }

    public static boolean saveKeyServer(String source, String new_name){
        File files = new File(source);
        if(!files.exists()) return false;
        File filed = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + Config.keystoreDIR+File.separator+new_name+Config.keystoreEXT);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(files);
            out = new FileOutputStream(filed);
            int bytes;
            while( (bytes = in.read()) != -1)
                out.write(bytes);
            return true;
        } catch (FileNotFoundException ex) {
            System.err.println("I couldn't copy keystore. Cause: " + ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.err.println("I couldn't write in destiny file. Cause: " + ex.getMessage());
            return false;
        }finally{
            try {
                in.close();
                out.close();
            } catch (IOException ex) {}
        }
    }
    
    public static boolean saveKSPassword(char[] password, String filename){
        if(password ==null || filename == null) return false;
        if(password.length==0) return false;
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + Config.keystoreDIR+File.separator+filename+Config.keystore_PWD_EXT);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("password=" + String.valueOf(password));
        } catch (IOException ex) {
            System.err.println("I couldn't save the keystore's password. Cause: " + ex.getMessage());
            return false;
        }finally{
            try {
                bw.close();
            } catch (IOException ex) {}
        }
        return true;
    }

    public static boolean delKSPassword(String filename){
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + Config.keystoreDIR+File.separator+filename+Config.keystore_PWD_EXT);
        if(file.exists())return file.delete();
        Config.loadKSSecrets();
        return true;
    }
    
    public static boolean delKeyServer(String filename){
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + Config.keystoreDIR+File.separator+filename+Config.keystoreEXT);
        if(file.exists())return file.delete();
        Config.loadKSSecrets();
        return true;
    }

    public static HashMap<String,List<InterfaceAddress> > getInterfaces(){
        HashMap<String,List<InterfaceAddress>> interfaces = new HashMap<String,List<InterfaceAddress>>();
        try {
            Enumeration<NetworkInterface> nis =NetworkInterface.getNetworkInterfaces();
            while(nis.hasMoreElements()){
                NetworkInterface ni = nis.nextElement();
                interfaces.put(ni.getDisplayName(), ni.getInterfaceAddresses());
            }
            return interfaces;
        } catch (SocketException ex) {
            return null;
        }
    }
}
