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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import kuasar.plugin.deployer.Config;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class DataExtractor {
    private Element dataProject = new Element("Project");
    private HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    public boolean extract(Element root){
        return browseNodes(root);
    }
    
    public HashMap<String, String[]> getErrors(){
        return errors;
    }
    
    public Element getData(){
        return dataProject;
    }
    
    private boolean browseNodes(Element root) {
        if (root.getAttributeValue("type") == null) {
            return recursiveBrowse(root, dataProject, "/", "");
        } else if (root.getAttributeValue("type").isEmpty()) {
            return recursiveBrowse(root,  dataProject, root.getName(), root.getAttributeValue("name"));
        } else {
            return loadBin(root, dataProject, "/", "");
        }
    }

    private boolean recursiveBrowse(Element root, Element parent, String path, String name) {
        boolean correct = true;
        List<Element> children = root.getChildren();
        for (Element child : children) {
            if (child.getAttributeValue("type").isEmpty()) {
                Element node = new Element(child.getName());
                node.setAttribute("name", child.getAttributeValue("name"));
                node.setAttribute("type", "dir");
                if (!recursiveBrowse(child, node, path + (path.equals("/") ? "" : "/") + child.getName(), name + (name.equals("/") ? "" : "/") + child.getAttributeValue("name"))) {
                    correct = false;
                }
                parent.addContent(node);
            } else {
                if (!loadBin(child,parent, path + (path.equals("/") ? "" : "/") + child.getName(), name + (name.equals("/") ? "" : "/") + child.getAttributeValue("name"))) {
                    correct = false;
                }
            }
        }
        return correct;
    }
    private boolean loadBin(Element child, Element parent, String path, String name) {

        Element node = new Element(child.getName());
        node.setAttribute("name", child.getAttributeValue("name"));
        node.setAttribute("type", "vm");
        HashMap<String, Object> mapVM;
        String file = Config.pluginDir + File.separator + Config.VMdir + File.separator + Config.VMnodes + File.separator + child.getAttributeValue("path");
        File binFile = new File(file);
        if (!binFile.exists()) {
            String[] info = {name, "VM data file " + file + " no exists"};
            errors.put(path, info);
            return false;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            mapVM = (HashMap<String, Object>) ois.readObject();
            loadMap(mapVM, node, path, name);
            parent.addContent(node);
        } catch (IOException ex) {
            String[] info = {name, "Error reading data file " + file};
            errors.put(path, info);
            return false;
        } catch( ClassNotFoundException ex){
            String[] info = {name, "Bad class found on file " + file};
            errors.put(path, info);
            return false;
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
            }
        }

        return true;
    }
     private void loadMap(HashMap<String, Object> mapVM,Element parent, String path, String name) {
        Iterator<String> keys = mapVM.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            if(key.equals("dev")){
                Element node = new Element(key);
                loadDevices((DefaultTreeModel) mapVM.get(key), node, path, name);
                parent.addContent(node);
            }else if(key.equals("dev.slots")){
                //extract slots
            }else if(key.equals("net")){
                Element node = new Element(key);
                loadNetwork((ArrayList<Object[]>) mapVM.get(key), node);
                parent.addContent(node);
            }else{
                parent.setAttribute(key,mapVM.get(key).toString());
            }
        }
    }

     private void loadNetwork(ArrayList<Object[]> list, Element parent){
         for(int i = 0; i<list.size(); i++){
             Object[] data = list.get(i);
             Element e = new Element(("_" + i));
             e.setAttribute("name", (String) data[0]);
             e.setAttribute("type", Integer.toString((Integer) data[1]));
             parent.addContent(e);
         }
     }
    private boolean loadDevices(DefaultTreeModel treeModel,Element parent, String path, String name) {
         DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) treeModel.getRoot();     
         Element curModuleNode=null;
         boolean correct = true;
        if (treenode == null) {
            return true;
        }
        do {
            Object[] data = (Object[]) treenode.getUserObject();
            if((Integer)data[0]>=0){
                HashMap<String, Object> deviceMap = (HashMap<String, Object>) data[2];
                if((Boolean) deviceMap.get("isdevice")){
                    Element node = new Element("device_"+ Integer.toString((Integer) deviceMap.get("slot")));
                    node.setAttribute("name",(String) data[1]);
                    node.setAttribute("slot", Integer.toString((Integer) deviceMap.get("slot")));
                    node.setAttribute("type", Integer.toString((Integer) deviceMap.get("id")));
                    if(deviceMap.get("passthrough")!=null)
                        node.setAttribute("passthrough", Boolean.toString((Boolean)deviceMap.get("passthrough")));
                    if(deviceMap.get("diff")!=null)
                        node.setAttribute("diff", Boolean.toString((Boolean)deviceMap.get("diff")));
                    if(deviceMap.get("file")!=null)
                    node.setAttribute("file", ((File) deviceMap.get("file")).getAbsolutePath());
                    if(curModuleNode==null){
                        String[] info = {name, "Error translating data"};
                        errors.put(path, info);
                        correct=false;
                    }else{
                        curModuleNode.addContent(node);
                    }
                }else{
                    Element node = new Element("module_"+ data[0]);
                    node.setAttribute("id",Integer.toString((Integer) data[0]));
                    node.setAttribute("name", (String) data[1]);
                    node.setAttribute("cache", Boolean.toString((Boolean)deviceMap.get("cache")));
                    node.setAttribute("type", Integer.toString((Integer)deviceMap.get("type")));
                    curModuleNode = node;
                    parent.addContent(curModuleNode);
                }
            }
            treenode = treenode.getNextNode();
        } while (treenode != null);
        return correct;
    }
}
