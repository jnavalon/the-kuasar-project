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
package kuasar.plugin.vmcreator;

import kuasar.plugin.vmcreator.gui.pn_Main;
import kuasar.plugin.vmcreator.gui.pn_ToolBar;
import javax.swing.ImageIcon;
import kuasar.plugin.Global;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.utils.XML;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class VMCreator implements kuasar.plugin.PluginInterface {

    private String state = "";

    public String getName() {
        return "VM Creator";
    }

    public ImageIcon getIcon() {
        try {
            ImageIcon icon = new javax.swing.ImageIcon(this.getClass().getResource("/kuasar/plugin/vmcreator/icons/icon"));
            return icon;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public boolean Start(Object ODRClassInstance, Class ODRClass) {

        Global.ODRClass = ODRClass;
        Global.ODRClassInstance = ODRClassInstance;
        XML.Load(Config.path, Config.network);
        ODR.setValue(getPluginName()+".path", Config.path);
        ODR.setValue(getPluginName()+".data", Config.network);
        ODR.setValue(getPluginName()+".nodes", Config.virtualmachine);
        ODR.setValue(getPluginName()+".iconpath", "/kuasar/plugin/vmcreator/icons/icon");
        return true;

    }

    public boolean Load(Object mainClassInstance, Class mainClass) {
        try {
            Global.mainClass = mainClass;
            Global.mainClassInstance = mainClassInstance;
            pn_Main panel = new pn_Main();
            kuasar.plugin.Intercom.GUI.loadToolBar(new pn_ToolBar(panel));
            kuasar.plugin.Intercom.GUI.loadPlugin(panel);
            kuasar.plugin.Intercom.GUI.visibleToolBar();
            return true;
        }catch(Exception ex){
            state=ex.getMessage();
            return false;
        }

        
    }

    public boolean unLoad() {
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
