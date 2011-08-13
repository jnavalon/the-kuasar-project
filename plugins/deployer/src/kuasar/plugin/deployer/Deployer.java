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
package kuasar.plugin.deployer;

import javax.swing.ImageIcon;
import kuasar.plugin.Global;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.PluginInterface;
import kuasar.plugin.deployer.gui.pn_Targets;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Deployer implements kuasar.plugin.PluginInterface{

    private String state = "";
    
    @Override
    public String getName() {
        return "Deployer";
    }

    @Override
    public String getPluginName() {
        return Config.PluginName;
    }

    @Override
    public ImageIcon getIcon() {
        try {
            ImageIcon icon = new javax.swing.ImageIcon(this.getClass().getResource("/kuasar/plugin/deployer/icons/icon"));
            return icon;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String getError() {
        return state;
    }

    @Override
    public boolean Start(Object ODRClassInstance, Class ODRClass) {
        Global.ODRClass = ODRClass;
        Global.ODRClassInstance = ODRClassInstance;
        Config.SMdata = (String) ODR.getValue("servermanager.data");
        Config.SMdir = (String) ODR.getValue("servermanager.path");
        Config.VMdata = (String) ODR.getValue("vmcreator.data");
        Config.VMdir =(String) ODR.getValue("vmcreator.path");
        Config.VMnodes = (String) ODR.getValue("vmcreator.nodes");
        Config.VMicondir = (String) ODR.getValue("vmcreator.iconpath");
        Config.startDir = (String) ODR.getValue("$STARTDIR");
        Config.pluginDir = (String) ODR.getValue("$PLUGINDIR");
        return true;
    }

    @Override
    public boolean Load(Object mainClassInstance, Class mainClass) {
        Global.mainClass = mainClass;
        Global.mainClassInstance = mainClassInstance;
        PluginInterface[] plugins = (PluginInterface[]) ODR.getValue("$PLUGINS");
        boolean vmcLoaded = false;
        boolean netcLoaded = false;
        boolean smanLoaded = false;

        for (PluginInterface plugin : plugins) {
            if (plugin.getPluginName().equals("vmcreator")) {
                vmcLoaded = true;
            }
            if (plugin.getPluginName().equals("netcreator")) {
                netcLoaded = true;
            }
            if(plugin.getPluginName().equals("servermanager")){
                smanLoaded = true;
            }
        }
        if (!vmcLoaded) {
            state = "<html><body>We sorry but " + getName() + " requires VMCreator plugin.<br> Please, check if VMCreator is installed!";
            return false;
        }

        if (!netcLoaded) {
            state = "<html><body>We sorry but " + getName() + " requires netCreator plugin.<br> Please, check if netCreator is installed!";
            return false;
        }
        if(!smanLoaded){
            state = "<html><body>We sorry but " + getName() + " requires serverManager plugin.<br> Please, check if serverManager is installed!";
            return false;
        }
        
        pn_Targets panel = new pn_Targets();
        kuasar.plugin.Intercom.GUI.loadPlugin(panel);
        kuasar.plugin.Intercom.GUI.invisibleToolBar();
        return true;
    }

    @Override
    public boolean unLoad() {
        return true;
    }

    @Override
    public boolean Stop() {
        return true;
    }
    
}
