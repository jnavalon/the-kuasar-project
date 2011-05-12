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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
        if(!st.hasMoreTokens()){
            System.out.println("Missing username");
            return;
        }
        String user = st.nextToken();
        if(checkUser(user)){
            System.out.println("User exists. If you want to change user's password, use \"vmuser password\" command.");
            return;
        }
        char[] passwd = readPasswd();
        if(passwd != null)
            if(saveUser(user, passwd)) System.out.println("User saved!");
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
        String username = st.nextToken();
        HashMap<String, String> users = getUsers();
        if(users.get(username)==null){
            System.out.println("User \"" + username + "\" not found");
            return;
        }

        if(!new File(Config.BLASAR.startDir + Config.BLASAR.passFile).delete()){
            System.out.println("File couldn't be deleted. Check file permissions!");
            return;
        }
        Iterator usernames = users.keySet().iterator();
        while(usernames.hasNext()){
            String key = (String) usernames.next();
            if(!key.equals(username)){
                saveUser(key,(String) users.get(key));
            }
        }
    }
    private static HashMap<String, String> getUsers(){
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.passFile);
        if(!file.exists()){
            return null;
        }
        if(!file.canRead()){
            return null;
        }
        HashMap<String,String> users = new HashMap<String,String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] data = new String[2];
            try{
                while ((line = br.readLine().trim()) != null) {
                    data=line.split(" ");
                    users.put(data[0], data[1]);
                }
            }catch(Exception e){}
        } catch (FileNotFoundException ex) {
            System.out.println("Impossible to check users. File not found.");
        }
        return users;
    }
    private static boolean checkUser(String user){
        HashMap<String,String> map = getUsers();
        if(map == null) return false;
        return (map.get(user)==null ?  false : true);
    }
    private static void listUsers(){
        Iterator users = new TreeSet<String>(getUsers().keySet()).iterator();
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
        String user = st.nextToken();
        HashMap<String, String> users = getUsers();
        if(users.get(user)==null){
            System.out.println("User \"" + user + "\" not found");
            return;
        }
        char[] passwd = readPasswd();
        if(passwd==null) return;
        try {
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
        
    }
    private static char[] readPasswd(){
        Console terminal = System.console();
        char[] passwd = null;

        if(terminal == null){
            System.out.println("Error found a usable terminal. Using visible password on screen.\n");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int cont = 0;
                char[] check;
                do{
                    System.out.print("Password: ");
                    passwd = br.readLine().toCharArray();
                    System.out.print("Again: ");
                    check = br.readLine().toCharArray();
                    if(!Arrays.equals(passwd, check)){
                        System.out.println("Passwords don't match.");
                        cont++;
                    }
                }while(cont < 3 && !Arrays.equals(passwd, check) );
                if(cont==3){
                    System.out.println("Try again later.");
                    return null;
                }
            } catch (IOException ex) {
                System.out.println("Was not possible to read a password. Aborted!");
            }
        }else{
            char[] check;
            int cont = 0;
            do{
                passwd = terminal.readPassword("Password: ");
                check = terminal.readPassword("Again: ");
                if(!Arrays.equals(passwd, check)){
                        System.out.println("Passwords don't match.");
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
       System.out.println("\t\tadd <user>     \tAdd new user");
       System.out.println("\t\tdel <user>     \tRemove user");
       System.out.println("\t\tlist           \tList all users");
       System.out.println("\t\tpassword <user>\tChange user's password");
    }

}
