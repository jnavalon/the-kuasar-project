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

package kuasar.plugin.netcreator;

import java.io.File;
import javax.swing.ImageIcon;
import kuasar.plugin.Global;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.PluginInterface;
import kuasar.plugin.netcreator.gui.pn_Main;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class netCreator implements kuasar.plugin.PluginInterface{

    private String state = "";

    public String getName() {
        return "Network Creator";
    }

    public ImageIcon getIcon() {
        try {
            ImageIcon icon = new javax.swing.ImageIcon(this.getClass().getResource("/kuasar/plugin/netcreator/icons/icon"));
            return icon;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public boolean Start(Object ODRClassInstance, Class ODRClass) {
        Global.ODRClass=ODRClass;
        Global.ODRClassInstance = ODRClassInstance;
        ODR.setValue(getPluginName()+".path", ODRClassInstance);
        return true;
    }

    public boolean Load(Object mainClassInstance, Class mainClass) {
        Global.mainClass = mainClass;
        Global.mainClassInstance = mainClassInstance;
        PluginInterface[] plugins = (PluginInterface[]) ODR.getValue("$PLUGINS");
        Config.skinpath=ODR.getValue("$STARTDIR").toString() + ODR.getValue("$SKINDIR").toString() + File.separator + ODR.getValue("$SKINNAME");
        boolean loaded= false;
        for(int i=0; i<plugins.length && loaded==false;i++){
            PluginInterface plugin = plugins[i];
            if(plugin.getPluginName().equals("vmcreator")){
                loaded=true;
            }
        }
        if(!loaded){
            state="<html><body>We sorry but netCreator plugin requires VMCreator.<br> Please, check if VMCreator is installed!";
            return false;
        }
        Config.VMdata=(String) ODR.getValue("vmcreator.data");
        Config.VMdir=(String) ODR.getValue("vmcreator.path");
        Config.VMnodes=(String) ODR.getValue("vmcreator.nodes");
 
        pn_Main panel = new pn_Main();
        kuasar.plugin.Intercom.GUI.loadPlugin(panel);
        return true;
    }

    public boolean unLoad() {
        if(state.isEmpty())
        return GUI.unLoadPlugin();
        else return true;
    }

    public boolean Stop() {
        return true;
    }

    public String getError() {
        return state;
    }

    public String getPluginName() {
        return (Config.PluginName);
    }

}
