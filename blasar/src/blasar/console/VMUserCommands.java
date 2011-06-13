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
import blasar.util.DNIe;
import blasar.util.UserLogin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import javax.smartcardio.CardException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class VMUserCommands{

    protected static void Redirect(StringTokenizer st){
        if(!st.hasMoreTokens()){
            printHelp();
            return;
        }
        String token = st.nextToken().toLowerCase();
        if(token.equals("add"))
            addUser(st);
        else if(token.equals("del"))
            delUser(st);
        else if(token.equals("list"))
            listUsers();
        else if(token.equals("password"))
            changePasswd(st);
        else if(token.equals("?"))
            printHelp();
        else
            printHelp();
    }

    private static void addUser(StringTokenizer st){
        boolean dnie = false;
        if(st.countTokens() > 1){
            if(!st.nextElement().equals("-d")){
                printHelp();
                return;
            }
            dnie = true;
        }
        if(!st.hasMoreTokens()){
            System.out.println("Missing username");
            return;
        }
        String user = st.nextToken();
        if(checkUser(user)){
            System.out.println("User exists. If you want to change user's password, use \"vmuser password\" command.");
            return;
        }
        char[] passwd = null;
        if(!dnie) passwd = readPasswd();
        if(passwd != null){
            if(saveUser(user, passwd)) System.out.println("User saved!");
        }else{
            String nif =readNIF();
            String unif= UserLogin.checkNIF(nif);
            if(unif!=null){
                System.out.println(unif + " has this NIF. If you want use this NIF first delete this user then try again");
                return;
            }
            if(nif != null)
                if(saveUser(user, Config.BLASAR.DNIeChar + nif)) System.out.println("User saved!");
        }
    }
    private static boolean saveUser(String user, char[] passwd){
        try {
            return saveUser(user, blasar.util.Encryptation.getSHA512(passwd));
        }  catch(NoSuchAlgorithmException ex) {
            System.out.println("Impossible to HASH password! SHA512 is not available");
        }
        return false;
    }
    private static boolean saveUser(String user, String SHApasswd){
        boolean status = true;
        if(SHApasswd == null || user == null) return false;
        if(user.trim().length()==0) return false;
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.passFile);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(user + " " + SHApasswd + "\n");
        } catch (IOException ex) {
            System.out.println("User can't be saved! Check file permissions.");
            status = false;
        }finally{
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException ex) {}
            }
        }
        return status;
    }
    private static void delUser(StringTokenizer st){
        if(!st.hasMoreTokens()){
            System.out.println("Missing username");
            return;
        }
        String nif;
        String username = st.nextToken();
        HashMap<String, String> users = null;
        try {
            users = UserLogin.getUsers();
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Impossible to check user");
        }
        if(users == null){
            System.out.println("There aren't users.");
            return;
        }
        if(users.get(username)==null){
            System.out.println("User \"" + username + "\" not found");
            return;
        }
        nif = getNIF(username);
        if(!new File(Config.BLASAR.startDir + Config.BLASAR.passFile).delete()){
            System.out.println("File couldn't be deleted. Check file permissions!");
            return;
        }
        Iterator usernames = users.keySet().iterator();
        boolean successfull = true;
        while(usernames.hasNext()){
            String key = (String) usernames.next();
            if(!key.equals(username)){
                if(!saveUser(key,(String) users.get(key)))
                    successfull = false;
            }
        }
        if(successfull && nif!=null){
            DNIe.removePublicKey(nif);
        }
    }
    
    private static boolean checkUser(String user){
        HashMap<String,String> map= null;
        try {
            map = UserLogin.getUsers();
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Impossible to check user");
        }
        if(map == null) return false;
        return (map.get(user)==null ?  false : true);
    }
    private static String getNIF(String username){
        HashMap<String,String> map= null;
        String nif = null;
        try {
            map = UserLogin.getUsers();
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Impossible to check user");
        }
        if(map == null) return null;
        nif = map.get(username).trim();
        if(nif==null) return null;
        if(nif.length()<1) return null;
        if(!nif.startsWith("*"))return null;
        return nif.substring(1);
    }
    
    private static void listUsers(){
        Iterator users=null;
        try {
            HashMap<String,String> map = UserLogin.getUsers();
            if(map==null){
                System.out.println("There aren't users.");
                return;
            }
            users = new TreeSet<String>(map.keySet()).iterator();
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Impossible to check user");
            return;
        }
        int i=0;
        while(users.hasNext()){
            i++;
            System.out.println(users.next());
        }
        System.out.println("\n>>> Total users: " + i);
    }
    private static void changePasswd(StringTokenizer st){
        if(!st.hasMoreTokens()){
            System.out.println("Missing user");
            return;
        }
         boolean dnie = false;
        if(st.countTokens() > 1){
            if(!st.nextElement().equals("-d")){
                printHelp();
                return;
            }
            dnie = true;
        }
        String user = st.nextToken();
        HashMap<String, String> users = null;
        try {
            users = UserLogin.getUsers();
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Impossible to check user");
        }
        if(users.get(user)==null){
            System.out.println("User \"" + user + "\" not found");
            return;
        }
        String onif=users.get(user).trim();
        String nnif= null;
        char[] passwd =null;
        if(dnie){
             nnif=readNIF();
            if (nnif!=null)
                passwd = nnif.toCharArray();
        }else
            passwd = readPasswd();
        if(passwd==null) return;
        try {
            if(dnie)
                users.put(user, Config.BLASAR.DNIeChar + String.valueOf(passwd));
            else
                users.put(user, blasar.util.Encryptation.getSHA512(passwd));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Impossible to HASH password! SHA512 is not available");
            return;
        }
        if(!new File(Config.BLASAR.startDir + Config.BLASAR.passFile).delete()){
            System.out.println("File couldn't be deleted. Check file permissions!");
            return;
        }

        Iterator keys = new TreeSet<String>(users.keySet()).iterator();
        while(keys.hasNext()){
            String key =(String) keys.next();
            saveUser(key, users.get(key));
        }
        System.out.println((dnie ? "Certificate": "Password") + " was changed successfully!");
        if(onif != null){
            if(onif.length()>1)
                onif= onif.substring(1);
            if(!onif.equals(nnif))
                DNIe.removePublicKey(onif);
        }
    }
    private static String readNIF(){
        System.out.println("Insert your DNIe and press any key to continue...");
        try {
            System.in.read();
        } catch (IOException ex) {
            System.out.println("Error reading input data.");
        }
        char[] pin = readPasswd("PIN");
        X509Certificate cert = null;
        try {
            System.out.println("Getting DNIe Certificate (Autenticate Cert ONLY). It might take a while");
            cert = DNIe.extractCert(null, pin);
        } catch (KeyStoreException ex) {
            System.out.println("Error accessing to KeyStore: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error reading SmartCard: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Algorithm not implemented: " + ex.getMessage());
        } catch (CertificateException ex) {
            System.out.println("Error reading certificate: "+ ex.getMessage());
        } catch (CardException ex) {
            System.out.println("Error checking SmartCard: " + ex.getMessage());
        }
        if(cert == null) {
            System.out.println("We can't read your certificate. Check if your card is inserted correctly");
            return null;
        }
        System.out.println("You may remove your card now (If not already done so)");
        String nif = DNIe.getNIF(cert);
        if (nif==null){
            System.out.println("Error: NIF not found on certificate? Maybe this program is an obsolete version");
            return null;
        }
        System.out.println("Saving Public Key to file (" + nif  + ".pk)");
        DNIe.savePublicKey(cert.getPublicKey(), nif);
        return nif;
    }
    
    private static char[] readPasswd(){
        return readPasswd(null);
    }

    private static char[] readPasswd(String type){
        Console terminal = System.console();
        char[] passwd = null;
        if(type == null) type = "password";
        if(terminal == null){
            System.out.println("Error found a usable terminal. Using visible "+type+" on screen.\n");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int cont = 0;
                char[] check;
                do{
                    System.out.print(type + ": ");
                    passwd = br.readLine().toCharArray();
                    System.out.print("Again: ");
                    check = br.readLine().toCharArray();
                    if(!Arrays.equals(passwd, check)){
                        System.out.println(type + " don't match.");
                        cont++;
                    }
                }while(cont < 3 && !Arrays.equals(passwd, check) );
                if(cont==3){
                    System.out.println("Try again later.");
                    return null;
                }
            } catch (IOException ex) {
                System.out.println("It was not possible to read a "+type+". Aborted!");
            }
        }else{
            char[] check;
            int cont = 0;
            do{
                passwd = terminal.readPassword(type+": ");
                check = terminal.readPassword("Again: ");
                if(!Arrays.equals(passwd, check)){
                        System.out.println(type+" don't match.");
                        cont++;
                }
            }while(cont < 3 && !Arrays.equals(passwd,check));
            if(cont==3){
                    System.out.println("Try again later.");
                    return null;
            }
        }
        return passwd;
    }
    private static void printHelp(){
       System.out.println("vmuser <CMD>       \t");
       System.out.println("\t\tadd [-d] <user>\tAdd new user; -d (SPANISH DNIe)");
       System.out.println("\t\tdel <user>     \tRemove user");
       System.out.println("\t\tlist           \tList all users");
       System.out.println("\t\tpassword <user>\tChange user's password");
    }

}
