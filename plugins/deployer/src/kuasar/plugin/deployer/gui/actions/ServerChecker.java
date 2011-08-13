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
    private String gkeystore = null;
    private char[] gkssecret = null;
    private String guser = null;
    private char[] gusersecret = null;
    
    public boolean check(pn_Checker parent){
        this.parent = parent;
        Element root = parent.target;
        dg_KeyStore ksd = new dg_KeyStore(null, true, null);
        ksd.setHeader("Insert a global keystore (Optional)");
        ksd.setVisible(true);
        gkeystore = ksd.getKeyStore();
        gkssecret = ksd.getPassword();
        dg_Username userd = new dg_Username(null, true, null);
        userd.setHeader("Insert a global login (Optional)");
        userd.setVisible(true);
        guser = userd.getUserName();
        gusersecret = userd.getPassword();
        if(root.getAttributeValue("type")==null){
            boolean correct = true;
            List<Element> children = root.getChildren();
            for(Element child : children){
                if(!browseNodes(child))
                    correct=false;
            }
            return correct;
        }else if(root.getAttributeValue("type").isEmpty()){
            return browseNodes(root);
        }else if(root.getAttributeValue("type").equals("server")){
            return checkServer(root, "", "");
        }
        return true;
    }
    
    public boolean browseNodes(Element root){
        boolean correct = true;
        List<Element> children = root.getChildren();
        for(Element child : children){
            if(child.getAttributeValue("type").equals("server")){
                if(!checkServer(child, "/" + root.getAttributeValue("name"), "/" + root.getName())){
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
        parent.setInfoText("Checking " + name + "/" + root.getAttributeValue("name") + "...");
        if(root ==null) return true;
        if(!root.getAttributeValue("type").equals("server"))
            return true;
        String address = root.getAttributeValue("address");
        String pt = root.getAttributeValue("port");
        int port = 46600;
        try{
            port = Integer.parseInt(pt);
        }catch(NumberFormatException ex){
            String[] info = {name, "Bad port", "1"};
            errors.put(path+root.getName(), info);
            return false;
            
        }
        String keystore = Connection.getKeyStore(address);
        if(keystore == null){
            keystore = gkeystore;
            if(keystore==null){
                String[] info = {name, "KeyStore not found", "2"};
                errors.put(path, info);
                return false;
            }
        }
        char[] kssecret = Connection.getKeyStorePWD(address);
        if(kssecret == null){
            kssecret = gkssecret;
            if(kssecret == null){
                String[] info = {name, "KeyStore password not found", "3"};
                errors.put(path, info);
                return false;
            }
            
        }
        
        String username = null;
        if(!Connection.isDNIe(address)){
            username = Connection.getUserName(address);
            if(username == null){
                username = guser;
                if(username == null){
                    String[] info = {name, "Username not found", "4"};
                    errors.put(path, info);
                    return false;
                }
            }
        }else{
            username = "";
        }
        
        char[] userpwd = Connection.getUserPwd(address);
        if(userpwd == null){
            userpwd = gusersecret;
            if(userpwd == null){
                String[] info = {name, "User's password not found", "5"};
                errors.put(path, info);
                return false;
            }
        }
        
        int status = Connection.checkServer(address, port, keystore, kssecret, username, userpwd, true);
        if(status<1){
            String[] info = {name, Connection.getErrorDescription(status), Integer.toString(status)};
            errors.put(path, info);
            return false;
        }
        return true;
    }
   
}
