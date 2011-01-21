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

package kuasar.util.plugins;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Stack;
import kuasar.plugin.PluginInterface;
import kuasar.util.config.Configuration;
import kuasar.util.config.Configuration.Plugins;
import kuasar.util.config.ODRIntercom;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class PluginLoader {

    public static boolean loadPlugins() {
        try {
            File[] files = getFiles();
            if (files.length > 0) {
                SetClassPath scp = new SetClassPath();
                for (File file : files) {
                    try {
                        scp.add(file);
                    } catch (MalformedURLException ex) {
                        kuasar.util.Error.console(PluginLoader.class.getCanonicalName() + ".loadPlugins()", "BAD URL \n" + ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            kuasar.util.Error.console(PluginLoader.class.getCanonicalName() + ".loadPlugins()", ex.getMessage());
            return false;
        }
        return true;
    }

    private static File[] getFiles() {
        Stack<File> files = new Stack<File>();
        File dirPlugins = new File( Configuration.startDir + Plugins.pathplugins  + "/");
        try {
            if (dirPlugins.isDirectory() && dirPlugins.exists()) {
                File[] plugins = dirPlugins.listFiles(new FilenameFilter() {

                    public boolean accept(File dir, String name) {
                        return name.endsWith(Plugins.extPlug);
                    }
                });
                files.addAll(Arrays.asList(plugins));
            }

        } catch (SecurityException ex) {
            kuasar.util.Error.console(PluginLoader.class.getCanonicalName() + ".loadPlugins()", ex.getMessage());
        }
        return files.toArray(new File[0]);
    }

    public static PluginInterface[] getPlugins(){
        ServiceLoader < PluginInterface > sl = ServiceLoader.load(PluginInterface.class);
        sl.reload();
        Stack < PluginInterface > plugins = new Stack< PluginInterface >();
        ODRIntercom odri = new ODRIntercom();
        for(Iterator < PluginInterface > iterator = sl.iterator(); iterator.hasNext();){
            try{
                PluginInterface init = iterator.next();
                if(init.Start(odri, odri.getClass()))
                plugins.add(init);
            }catch(Exception ex){
                kuasar.util.Error.console(PluginLoader.class.getCanonicalName() + ".getPlugins()", ex.getMessage());
            }
        }
        return plugins.toArray(new PluginInterface[0]);
    }
}
