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
package kuasar.plugin.deployer.gui.actions;

import java.util.HashMap;
import java.util.List;
import kuasar.plugin.deployer.Config;
import kuasar.plugin.deployer.gui.pn_Checker;
import kuasar.plugin.utils.Connection;
import kuasar.plugin.utils.dialogs.dg_KeyStore;
import kuasar.plugin.utils.dialogs.dg_Username;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class ServerChecker {
    private pn_Checker parent;
    private HashMap<String, String[]> errors = new HashMap<String, String[]>();

    
    public boolean check(pn_Checker parent){
        this.parent = parent;
        Element root = parent.target;
        dg_KeyStore ksd = new dg_KeyStore(null, true, null);
        ksd.setHeader("Insert a global keystore (Optional)");
        ksd.setVisible(true);
        Config.gkeystore = ksd.getKeyStore();
        Config.gkssecret = ksd.getPassword();
        dg_Username userd = new dg_Username(null, true, null);
        userd.setHeader("Insert a global login (Optional)");
        userd.setVisible(true);
        Config.guser = userd.getUserName();
        Config.gusersecret = userd.getPassword();
        if(root.getAttributeValue("type")==null){
            boolean correct = true;
            List<Element> children = root.getChildren();
            for(Element child : children){
                if(child.getAttributeValue("type").isEmpty()){
                    if(!browseNodes(child,"/"+child.getAttributeValue("name") + "/","/"+child.getName()+"/"))
                        correct=false;
                }else if(child.getAttributeValue("type").equals("server")){
                    if(!checkServer(child,"/","/"))
                        correct = false;
                }
            }
            return correct;
        }else if(root.getAttributeValue("type").isEmpty()){
            return browseNodes(root,"/", "/");
        }else if(root.getAttributeValue("type").equals("server")){
            return checkServer(root, "", "");
        }
        return true;
    }
    
    public boolean browseNodes(Element root, String name, String path){
        boolean correct = true;
        List<Element> children = root.getChildren();
        for(Element child : children){
            if(child.getAttributeValue("type").equals("server")){
                if(!checkServer(child, name, path)){
                    correct = false;
                }
            }
        }
        return correct;
    }

    public HashMap<String, String[]> getErrors() {
        return errors;
    }

    private boolean checkServer(Element root, String name, String path) {
        parent.setInfoText("Checking " + name + root.getAttributeValue("name") + "...");
        if(root ==null) return true;
        if(!root.getAttributeValue("type").equals("server"))
            return true;
        String address = root.getAttributeValue("address");
        String pt = root.getAttributeValue("port");
        int port = 46600;
        try{
            port = Integer.parseInt(pt);
        }catch(NumberFormatException ex){
            String[] info = {name + root.getAttributeValue("name"), "Bad port", address, "1"};
            errors.put(path+root.getName(), info);
            return false;
            
        }
        String keystore = Connection.getKeyStore(address);
        if(keystore == null){
            keystore = Config.gkeystore;
            if(keystore==null){
                String[] info = {name + root.getAttributeValue("name"), "KeyStore not found",  address, "2"};
                errors.put(path+root.getName(), info);
                return false;
            }
        }
        char[] kssecret = Connection.getKeyStorePWD(address);
        if(kssecret == null){
            kssecret = Config.gkssecret;
            if(kssecret == null){
                String[] info = {name + root.getAttributeValue("name"), "KeyStore password not found", address, "3"};
                errors.put(path+root.getName(), info);
                return false;
            }
            
        }
        
        String username = null;
        if(!Connection.isDNIe(address)){
            username = Connection.getUserName(address);
            if(username == null){
                username = Config.guser;
                if(username == null){
                    String[] info = {name+root.getAttributeValue("name"), "Username not found", address, "4"};
                    errors.put(path+root.getName(), info);
                    return false;
                }
            }
        }else{
            username = "";
        }
        
        char[] userpwd = Connection.getUserPwd(address);
        if(userpwd == null){
            userpwd = Config.gusersecret;
            if(userpwd == null){
                String[] info = {name+ root.getAttributeValue("name"), "User's password not found", address, "5"};
                errors.put(path+root.getName(), info);
                return false;
            }
        }
        
        int status = Connection.checkServer(address, port, keystore, kssecret, username, userpwd, true);
        if(status<1){
            String[] info = {name + root.getAttributeValue("name") , Connection.getErrorDescription(status), 
                address, Integer.toString(status)};
            errors.put(path+root.getName(), info);
            return false;
        }
        return true;
    }
   
}
