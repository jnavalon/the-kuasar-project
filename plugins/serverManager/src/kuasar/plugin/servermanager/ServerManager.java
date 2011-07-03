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

import javax.swing.ImageIcon;
import kuasar.plugin.Global;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.PluginInterface;
import kuasar.plugin.servermanager.gui.pn_Main;
import kuasar.plugin.servermanager.gui.pn_ToolBar;
import kuasar.plugin.utils.DNIe;
import kuasar.plugin.utils.XML;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class ServerManager implements kuasar.plugin.PluginInterface {

    private String state = "";

    @Override
    public String getName() {
        return "Server Manager";
    }

    @Override
    public String getPluginName() {
        return Config.PluginName;
    }

    @Override
    public ImageIcon getIcon() {
        try {
            ImageIcon icon = new javax.swing.ImageIcon(this.getClass().getResource("/kuasar/plugin/servermanager/icons/icon"));
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
         XML.Load(Config.path, Config.fileServers);
        ODR.setValue(getPluginName() + ".path", ODRClassInstance);
        ODR.setValue(Config.PluginName+".usersecrets", Config.GlobalServerCFG.usersecrets);
        Config.loadConfig();
        DNIe.loadAutoChecker();
        return true;
    }

    @Override
    public boolean Load(Object mainClassInstance, Class mainClass) {
        Global.mainClass = mainClass;
        Global.mainClassInstance = mainClassInstance;
        Config.loadKSSecrets();
        PluginInterface[] plugins = (PluginInterface[]) ODR.getValue("$PLUGINS");
        boolean vmcLoaded = false;
        boolean netcLoaded = false;

        for (PluginInterface plugin : plugins) {
            if (plugin.getPluginName().equals("vmcreator")) {
                vmcLoaded = true;
            }
            if (plugin.getPluginName().equals("netcreator")) {
                netcLoaded = true;
            }
        }
        if (!vmcLoaded) {
            state = "<html><body>We sorry but " + getName() + " requires VMCreator.<br> Please, check if VMCreator is installed!";
            return false;
        }

        if (!netcLoaded) {
            state = "<html><body>We sorry but " + getName() + " requires netCreator.<br> Please, check if netCreator is installed!";
            return false;
        }

        pn_Main panel = new pn_Main();
        kuasar.plugin.Intercom.GUI.loadPlugin(panel);
        kuasar.plugin.Intercom.GUI.loadToolBar(new pn_ToolBar(panel));
        kuasar.plugin.Intercom.GUI.visibleToolBar();
        return true;
    }

    @Override
    public boolean unLoad() {
        if (state.isEmpty()) {
            short cent = 0;
            GUI.unLoadToolBar();
            GUI.unLoadPlugin();
            if (!GUI.unLoadToolBar()) {
                cent++;
            }
            if (!GUI.unLoadPlugin()) {
                cent++;
            } else {
                kuasar.plugin.Intercom.GUI.invisibleToolBar();
            }
            return cent > 0 ? false : true;
        } else {
            return true;
        }

    }

    @Override
    public boolean Stop() {
        DNIe.unloadAutoChecker();
        return true;
    }
    
}
