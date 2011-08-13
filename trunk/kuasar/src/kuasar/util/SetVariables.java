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
package kuasar.util;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import kuasar.util.config.Configuration;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class SetVariables {

    private boolean ok = true;
    public SetVariables() {
        Configuration.startDir = getStartDir();
        short status;
        File file = new File(Configuration.startDir + Configuration.Interfice.pathskins);
        status = checkPaths(file);
        switch (status) {
            case 1:
                JOptionPane.showMessageDialog(null, "I can't create skin folder! Skin wont' be loaded, sorry :-(", "Skins won't be loaded!", JOptionPane.ERROR_MESSAGE);
                ok=false;
                break;
            case 2:
                ok=false;
                JOptionPane.showMessageDialog(null, "Skins path is empty, skin won't be loaded, install some skin please!", "Skin Path empty", JOptionPane.WARNING_MESSAGE);
                break;
        }
        file = new File(Configuration.startDir + Configuration.Plugins.pathplugins);
        status = checkPaths(file);
        switch (status) {
            case 1:
                JOptionPane.showMessageDialog(null, "I can't create plugin folder! Plugins wont' be loaded, sorry :-(", "Plugin won't be loaded!", JOptionPane.ERROR_MESSAGE);
                ok=false;
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Plugin path is empty, plugins won't be loaded, install some plugins please!", "Plugin Path empty", JOptionPane.WARNING_MESSAGE);
                ok=false;
                break;
        }
        Configuration.addODR("$STARTDIR", Configuration.startDir);
        Configuration.addODR("$PLUGINDIR", Configuration.startDir + Configuration.Plugins.pathplugins);
        Configuration.addODR("$KEYSTOREDIR",Configuration.pathkeystore);
        Configuration.addODR("$KEYSTORE_EXT", ".ks");
        Configuration.addODR("$KEYSTORE_PWD_EXT", ".pwd");
        Configuration.addODR("$SKINDIR", Configuration.Interfice.pathskins);
        Configuration.addODR("$SKINNAME", Configuration.Interfice.skin);
        Configuration.addODR("#KS_SECRET", new HashMap<String,Object>());
        Configuration.addODR("#USER_SECRET", new HashMap<String,Object>());
        loadPreferences();
    }
    public boolean isOK(){
        return ok;
    }
    private short checkPaths(File file) {

        if (!file.exists()) 
            if (!file.mkdir()) 
                return 1;
            else 
                return 2;
            
         else 
            if(file.listFiles().length == 0)
                return 2;
        
        return 0;
    }

    private String getStartDir() {
        try {

            String dir = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

            if (!dir.toString().endsWith(java.io.File.separator)) {
                return dir.toString() + java.io.File.separator;
            }

            return dir.toString();
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private void loadPreferences() {
        if (new File(Configuration.startDir + Configuration.cfgFile).exists()) {
            Configuration.Preferences = Files.loadMap(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.cfgskin);
        }
    }
}
