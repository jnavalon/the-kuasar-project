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
package kuasar;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import kuasar.gui.PluginScan;
import kuasar.gui.fun_frm_Main;
import kuasar.util.SetVariables;
import kuasar.util.config.Configuration;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalAccessException {

        Splash splash = new Splash();
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        SetVariables sv = new kuasar.util.SetVariables();
        if (!sv.isOK()) {
            splash.dispose();
            return;
        }
        setTheme();
        fun_frm_Main MainWindow = new fun_frm_Main();
        MainWindow.setLocationRelativeTo(null);
        PluginScan plugins = new PluginScan();
        plugins.fillList(MainWindow);
        MainWindow.setVisible(true);
        splash.dispose();

    }

    private static void setTheme() {
        if (!(new File(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.nimrod_theme)).exists()) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ex) {
                System.err.println("Nimbus Theme not supported");
            }
            return;
        }

        NimRODLookAndFeel lf = new NimRODLookAndFeel();
        NimRODLookAndFeel.setCurrentTheme(new NimRODTheme(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.nimrod_theme));
        try {
            UIManager.setLookAndFeel(lf);
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("NimROD Theme not supported");
        }
    }
}
