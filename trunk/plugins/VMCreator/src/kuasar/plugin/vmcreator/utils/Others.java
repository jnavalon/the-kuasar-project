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
package kuasar.plugin.vmcreator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.utils.XML;
import kuasar.plugin.vmcreator.Config;
import kuasar.plugin.vmcreator.gui.tooltasks.AddHost.Namespace.keyMaps;
import org.jdom.Element;

/**
 *
 * @author jnavalon
 */
public final class Others {

    public static String getIcon(int id) {
        switch (id) {
            case 0:
                return "windows.png";
            case 1:
                return "linux.png";
            case 2:
                return "solaris.png";
            case 3:
                return "bsd.png";
            case 5:
                return "mac.png";
        }
        return "other.png";
    }

    public static boolean FileCopy(File src, File dest) {
        try {
            FileInputStream in = new FileInputStream(src);
            FileOutputStream out = new FileOutputStream(dest);
            int oct;
            while ((oct = in.read()) != -1) {
                out.write(oct);
            }
            in.close();
            out.close();

        } catch (IOException e) {
            System.err.println("Error :> " + e.getMessage());
            System.err.println(src.getName() + " ==> " + dest.getName());
        }
        return true;
    }

    public static File nextFileAvailable(String path) {
        String filename = "0.bin";
        File next = new File(path + File.separator + filename);
        for (int i = 1; next.exists(); i++) {
            next = new File(path + File.separator + i + ".bin");
        }
        return next;
    }

    public static void saveXML(String name, File file, HashMap<String, Object> data, String XMLPath, boolean delete) {

        ArrayList<String[]> attributs = new ArrayList<String[]>();
        attributs.add(new String[]{"name", name.replace('_', ' ')});
        attributs.add(new String[]{"type", "vm"});
        attributs.add(new String[]{"icon", Others.getIcon((Integer) data.get(keyMaps.OS + ".id"))});
        attributs.add(new String[]{"path", file.getName()});
        Element root = XML.getElementOnPath(XMLPath, (Element) ODR.getValue(Config.path + "." + Config.network));
        root = XML.getElementOnPath("/", (Element) ODR.getValue(Config.path + "." + Config.network));
        Element node = root.getChild(name);
        if (node != null) {
            if (delete) {
                root.removeChild(name);
            } else {
                return;
            }
        }
        if (XML.AddElement(XMLPath, root, name, attributs)) {
            GUI.launchInfo("VM \"" + name.replace('_', ' ') + "\" added successfully!");
        }
        XML.Save(Config.path, Config.network, root);
    }

    public static HashMap<String, Object> getVM(File file) {
        ObjectInputStream ois = null;
        HashMap<String, Object> map=null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            map = (HashMap<String, Object>) ois.readObject();
        } catch (ClassNotFoundException ex) {
            System.err.println("Error: " + file.getAbsolutePath() + " isn't a correct HashMap file");
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage() + " :: " + file.getAbsolutePath());
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage() + " :: " + file.getAbsolutePath());
            }
        }
        return map;
    }

    public static boolean removeVMFile(Element root){
        boolean delete = true;
        List<Element> children = root.getChildren();
        for(Element child: children){
            if(child.getAttributeValue("type").isEmpty()){
                if(!removeVMFile(child)) delete=false;
            }else{
                if(!removeVMFile(child.getAttributeValue("path"))) delete=false;
            }
        }
        return delete;
    }
    public static boolean removeVMFile(String Filename){
        String path = ((String) kuasar.plugin.Intercom.ODR.getValue("$PLUGINDIR")) + File.separator + kuasar.plugin.vmcreator.Config.path + File.separator + kuasar.plugin.vmcreator.Config.virtualmachine;
        File file = new File(path+File.separator+Filename);
        if(!file.exists()) return false;
        return file.delete();
    }
}
