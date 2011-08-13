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
import java.util.HashMap;
import java.util.List;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class ImageChecker {
    private HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    public boolean check(Element root){
        return browseNodes(root);
    }
    
    public HashMap<String, String[]> getErrors(){
        return errors;
    }
    
    
    private boolean browseNodes(Element root) {
        if(root.getAttributeValue("type") == null){
            return recursiveBrowse(root, "/", "");
        }
        if (root.getAttributeValue("type").equals("dir")) {
            return recursiveBrowse(root, "/", "");
        } else if (root.getAttributeValue("type").equals("dir")) {
            return recursiveBrowse(root, root.getName(), root.getAttributeValue("name"));
        } else {
            return checkImage(root, "/", "");
        }
    }
    
    private boolean recursiveBrowse(Element root, String path, String name) {
        boolean correct = true;
        List<Element> children = root.getChildren();
        for (Element child : children) {
            if (child.getAttributeValue("type").equals("dir")) {
                if (!recursiveBrowse(child, path + (path.equals("/") ? "" : "/") + child.getName(), name + (name.equals("/") ? "" : "/") + child.getAttributeValue("name"))) {
                    correct = false;
                }
            } else {
                if (!checkImage(child, path + (path.equals("/") ? "" : "/") + child.getName(), name + (name.equals("/") ? "" : "/") + child.getAttributeValue("name"))) {
                    correct = false;
                }
            }
        }
        return correct;
    }

    private boolean checkImage(Element node, String path, String name) {
        boolean correct = true;
        Element devices = node.getChild("dev");
        if(devices==null) return true;
        List<Element> modules = devices.getChildren();
        for(Element module : modules){
            List<Element> images = module.getChildren();
            for(Element image : images){
                if(image.getAttributeValue("file")!=null){
                    File file = new File(image.getAttributeValue("file"));
                    if(!file.exists()){
                        String[] info = {name, "Error checking VM image file " + file.getAbsolutePath() + " no exists."};
                        errors.put(path, info);
                        correct = false;
                    }else if(!file.canRead()){
                        String[] info = {name, "Error checking VM image file " + file.getAbsolutePath() + " can't be readed."};
                        errors.put(path, info);
                        correct = false;
                    }
                }
            }
        }
        return correct;
    }
        
}