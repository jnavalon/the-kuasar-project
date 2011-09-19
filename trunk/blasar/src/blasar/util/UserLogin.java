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

package blasar.util;

import blasar.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class UserLogin {

    public static boolean checkUser(String user, String passwd) {
        if(user == null || passwd ==null) return false;
        if(passwd.startsWith(Config.BLASAR.DNIeChar)) return false;
        try {
            HashMap<String, String> users = getUsers();
            if(users==null)return false;
            String SHApass =Encryptation.getSHA512(passwd.toCharArray());
            if(users.get(user)==null) return false;
            if(users.get(user).equals(SHApass)) return true;
        } catch (NoSuchAlgorithmException ex) {
            //LOG THIS EXCEPTION
        } catch (FileNotFoundException ex) {
            //LOG THIS EXCEPTION
        }
        return false;
    }

    public static String checkNIF(String nif){
        HashMap<String,String> map= null;
        try {
            map = UserLogin.getUsers();
            if(map == null) return null;
            Iterator it = map.keySet().iterator();
            while(it.hasNext()){
                String user = (String) it.next();
                String unif = (String) map.get(user);
                if(unif!=null){
                    if(unif.startsWith(Config.BLASAR.DNIeChar)){
                        unif = unif.substring(1);
                        if(unif.equals(nif)) return user;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found. Impossible to check user");
        }
        return null;
    }

    public static HashMap<String, String> getUsers() throws FileNotFoundException {
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.passFile);
        if (!file.exists()) {
            return null;
        }
        if (!file.canRead()) {
            return null;
        }
        HashMap<String, String> users = new HashMap<String, String>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String[] data = new String[2];
        try {
            while ((line = br.readLine().trim()) != null) {
                data = line.split(" ");
                users.put(data[0], data[1]);
            }
        } catch (Exception e) {
        }

        return users;
    }
}
