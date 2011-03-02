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
package kuasar.plugin.utils;

import java.io.*;
import java.util.List;
import kuasar.plugin.Intercom.ODR;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class XML {

    public static boolean isCorrectName(String name){
        if(adaptName(name)== null) return false;
        return true;
    }

    public static String adaptName(String name){

        if(name==null) return null;
        if(Character.isDigit(name.charAt(0))) return null;
        for(int i=0; i<name.length(); i++){
            if(!Character.isDigit(name.charAt(i)) && !Character.isLetter(name.charAt(i)) && name.charAt(i)!= ' '){
                return null;
            }
        }
        return name.trim().replace(' ', '_');
    }

    public static Element getElementOnPath(String netpath, Element root) {

        if (netpath == null) {
            netpath = ".";
        }
        netpath = netpath.substring(1);
        if (netpath.trim().length() > 0) {
            String[] netnames = netpath.split("[.]");
            for (String netname : netnames) {
                root = root.getChild(netname);
                if (root == null) {
                    return null;
                }
            }
        }
        return root;
    }

    public static boolean AddElement(String netpath, Element root, String name, List attributes) {
        root = getElementOnPath(netpath, root);
        if (root == null) {
            return false;
        }
        Element e = new Element(name);
        for (int i = 0; i < attributes.size(); i++) {
            String attribute[] = (String[]) attributes.get(i);
            e.setAttribute(attribute[0], attribute[1]);
        }
        root.addContent(e);
        return true;
    }

    public static boolean RemoveElement(String netpath, Element root, String name) {
        root = getElementOnPath(netpath, root);
        if (root == null) {
            return false;
        }
        return root.removeChild(name);
    }

    public static boolean Save(String cfgPath, String cfgFile, Element root) {
        String pluginSkin = (String) kuasar.plugin.Intercom.ODR.getValue("$PLUGINDIR");
        File file = new File(pluginSkin + File.separator + cfgPath + File.separator + cfgFile);
        if (!file.exists()) {
            return false;
        }
        try {
            Element aux = (Element) root.detach();
            Document doc = new Document(aux);
            XMLOutputter out = new XMLOutputter();
            FileOutputStream fos = new FileOutputStream(file);
            out.output(doc, fos);
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            System.err.println("I couldn't create network file on " + cfgPath + " path, changes won't be saved!");
            return false;
        }
        return true;
    }

    public static void Load(String cfgPath, String cfgFile) {

        String path = (String) kuasar.plugin.Intercom.ODR.getValue("$PLUGINDIR");
        File file = new File(path + File.separator + cfgPath);
        if (file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            if (!file.mkdir()) {
                System.err.println("I couldn't create +" + cfgPath  + " path, changes won't be saved!");
                return;
            }
        }

        file = new File(path + File.separator + cfgPath + File.separator + cfgFile);

        if (!file.exists()) {
            try {
                file.createNewFile();
                Element root = new Element(cfgPath + "." + cfgFile);
                Document doc = new Document(root);
                XMLOutputter out = new XMLOutputter();
                FileOutputStream fos = new FileOutputStream(file);
                out.output(doc, fos);
                fos.flush();
                fos.close();
            } catch (IOException ex) {
                System.err.println("I couldn't create network file on "+cfgPath +" path, changes won't be saved!");
                return;
            }
        }
        Element test = getRoot(file);
        if (test != null) {
            ODR.setValue(cfgPath + "." + cfgFile, test);
        } else {
            System.err.println("I couldn't find a XML Format on next file. Please check it: " + file.getAbsolutePath());
        }
    }

    public static Element getRoot(File file) {
        try {
            SAXBuilder builder = new SAXBuilder(false);
            return builder.build(file).getRootElement();
        } catch (Exception ex) {
            return null;
        }

    }
}
