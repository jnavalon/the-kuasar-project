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
package kuasar.plugin.servermanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import kuasar.plugin.Intercom.ODR;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Config {

    public static final String PluginName = "servermanager";
    public static final String path = "servermanager";
    public static final String fileServers = "servers";
    public static final String filecfg = "config";
    public static final String keystoreDIR = "keystores";
    public static final String keystoreEXT = ".ks";
    public static final String keystore_PWD_EXT = ".pwd";
    public static String NetworkFile;
    public static Element rootNetwork;

    public static final class GlobalServerCFG {

        public static String keyStore = null;
        public static char[] ksPasswd = null;
        public static String user = null;
        public static boolean dnie = false;
        public static boolean checkServer = false;
        public static boolean checkClient = false;
        public static int port = 46600;
        public static final int connection_timeout = 250;
        public static HashMap<String, Object> kssecrets = new HashMap<String, Object>();
        public static HashMap<String, Object> usersecrets = new HashMap<String, Object>();
    }

    public static final class KS_Secrets{
        public static final String KEYSTORE = ".keystore";
        public static final String KS_PASSWD = ".kspass";
    }
    public static final class User_Secrets{
        public static final String USERNAME = ".user";
        public static final String PASSWD = ".pass";
        public static final String DNIE = ".dnie";
    }
    public static final class ServerList {

        public static final Integer ICON = 0;
        public static final Integer NAME = 1;
        public static final Integer XMLNAME = 2;
        public static final Integer LABEL = 3;
    }

    public static void loadConfig() {
        loadKSSecrets();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File((String) kuasar.plugin.Intercom.ODR.getValue("$PLUGINDIR") + File.separator + Config.path + File.separator + Config.filecfg)));
            GlobalServerCFG.dnie = false;
            GlobalServerCFG.checkClient = false;
            GlobalServerCFG.checkServer = false;
            GlobalServerCFG.port = 46600;
            while (br.ready()) {
                String line = br.readLine();
                if (line.startsWith("keystore")) {
                    GlobalServerCFG.keyStore = line.substring(line.indexOf("=") + 1).isEmpty() ? null:line.substring(line.indexOf("=") + 1);
                } else if (line.startsWith("kspwd")) {
                    GlobalServerCFG.ksPasswd = line.substring(line.indexOf("=") + 1).isEmpty()?null: line.substring(line.indexOf("=") + 1).toCharArray();
                } else if (line.startsWith("dnie")) {
                    GlobalServerCFG.dnie = true;
                } else if (line.startsWith("checkuser")) {
                    GlobalServerCFG.checkClient = true;
                } else if (line.startsWith("checkserver")) {
                    GlobalServerCFG.checkServer = true;
                } else if (line.startsWith("user")) {
                    GlobalServerCFG.user = line.substring(line.indexOf("=") + 1).trim();
                    if(!GlobalServerCFG.user.isEmpty())
                        GlobalServerCFG.dnie = false;
                    else
                        GlobalServerCFG.user=null;
                } else if (line.startsWith("port")) {
                    int new_port;
                    try {
                        new_port = Integer.parseInt(line.substring(line.indexOf("=") + 1));
                    } catch (NumberFormatException ex) {
                        new_port = 46600;
                    }
                    GlobalServerCFG.port = new_port;
                }
            }
        } catch (IOException ex) {
        }
    }

    public static void loadKSSecrets() {
        GlobalServerCFG.kssecrets.clear();
        File dir = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + Config.keystoreDIR);
        if (!dir.exists()) {
            dir.mkdir();
            return;
        }
        File[] files = dir.listFiles(new KSFilter());
        for(File file : files){
            String address = file.getName().substring(0, file.getName().lastIndexOf("."));
            GlobalServerCFG.kssecrets.put(address + KS_Secrets.KEYSTORE, file.getAbsolutePath());
            GlobalServerCFG.kssecrets.put(address + KS_Secrets.KS_PASSWD, loadPWDFile(address + Config.keystore_PWD_EXT));
        }
        ODR.setValue(Config.PluginName+".kssecrets", GlobalServerCFG.kssecrets);
    }
    
    public static void addUserSecret(String address, String user, char[] password, boolean dnie){
        Config.GlobalServerCFG.usersecrets = (HashMap<String, Object>) ODR.getValue(Config.PluginName+".usersecrets");
        GlobalServerCFG.usersecrets.put(address + User_Secrets.USERNAME, user);
        GlobalServerCFG.usersecrets.put(address + User_Secrets.PASSWD, password);
        GlobalServerCFG.usersecrets.put(address + User_Secrets.DNIE, dnie);
        ODR.setValue(Config.PluginName+".usersecrets", GlobalServerCFG.usersecrets);
    }
    
    public static void delUserSecret(String address){
        Config.GlobalServerCFG.usersecrets = (HashMap<String, Object>) ODR.getValue(Config.PluginName+".usersecrets");
        GlobalServerCFG.usersecrets.remove(address + User_Secrets.USERNAME);
        GlobalServerCFG.usersecrets.remove(address + User_Secrets.PASSWD);
        GlobalServerCFG.usersecrets.remove(address + User_Secrets.DNIE);
        ODR.setValue(Config.PluginName+".usersecrets", GlobalServerCFG.usersecrets);
    }
    
    private static char[] loadPWDFile(String name){
        char[] passwd = null;
        File file = new File((String) kuasar.plugin.Intercom.ODR.getValue("$STARTDIR") + File.separator + Config.keystoreDIR+File.separator+name);
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
                if (file.getName().toLowerCase().endsWith(Config.keystoreEXT)) {
                    return true;
                }
            return false;
        }
    }
}
