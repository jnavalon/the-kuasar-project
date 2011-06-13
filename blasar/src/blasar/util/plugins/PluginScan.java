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
package blasar.util.plugins;

import blasar.Config;
import blasar.Info;
import blasar.Services.Com.vms.PluginInterface;


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
                    for (PluginInterface plugin : plugins) {
                        System.out.println("Plugin " + plugin.getPluginName() + " loaded   :-)");
                        Config.BLASAR.plugins.put(plugin.getEngine(), plugin);
                    }
                } else {
                    System.out.println("Sorry, I couldn't find any plugin!     :-(");
                }
            } catch (Exception ex) {
                Info.showError(ex.getMessage());
            }
        } else {
            System.out.println("Sorry, I couldn't load any plugin!     :-(");
        }
    }

}
