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
package kuasar.gui;

import javax.swing.DefaultListModel;
import kuasar.plugin.PluginInterface;
import kuasar.util.plugins.PluginLoader;
import kuasar.util.Error;
import kuasar.util.config.Configuration;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class PluginScan {

    public PluginScan() {
        boolean loaded = PluginLoader.loadPlugins();
        if (loaded) {
            try {
                PluginInterface[] plugins = PluginLoader.getPlugins();
                if (plugins.length > 0) {
                    Configuration.addODR(Configuration.Plugins.pluginsKey, plugins);
                } else {
                    System.out.println("Sorry, I couldn't find any plugin!     :-(");
                }
            } catch (Exception ex) {
                Error.console(this.getClass().getCanonicalName(), ex.getMessage());
            }
        } else {
            System.out.println("Sorry, I couldn't load any plugin!     :-(");
        }
    }

    public void fillList(fun_frm_Main MainWindow) {
        DefaultListModel model = new DefaultListModel();
        MainWindow.lst_Plugins.setModel(model);
        PluginInterface[] plugins = (PluginInterface[]) Configuration.getODR(Configuration.Plugins.pluginsKey);
        if (plugins != null) {
            for (PluginInterface plugin : plugins) {
                try {
                    Object data[] = new Object[3];
                    data[0] = "1";
                    data[1] = plugin.getName();
                    if (plugin.getIcon() == null) {
                        data[2] = new javax.swing.ImageIcon(getClass().getResource("/icons/plugin.png"));
                    } else {
                        data[2] = plugin.getIcon();
                    }
                    model.addElement(data.clone());
                } catch (Exception ex) {
                    Error.console(this.getClass().getCanonicalName() + "fillList(fun_frm_Main MainWindow) ", "Plugin " + plugin.getClass().getCanonicalName() + " poorly implemented or unsuccessfully loaded");
                }

            }
        }

    }
}
