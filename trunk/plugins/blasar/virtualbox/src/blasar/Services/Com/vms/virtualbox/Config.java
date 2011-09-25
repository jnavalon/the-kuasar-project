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
package blasar.Services.Com.vms.virtualbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Config {

    public static String startDir = getPluginDir();
    public static final String pluginName = "VirtualBox";
    public static String application=null;
    public static String vmipath=null;
    public static String bridgedif=null;
    public static String hostoif=null;
    public static final String VMAPP = "VBoxManage";
    public static String EngineCode="VBX";
    public static String EngineName="VirtualBox";

    public static void load() {
        File cfgFile = new File(startDir + File.separator + pluginName.toLowerCase() + ".cfg");
        if (!cfgFile.exists()) {
            ConfigSetup cfgs = new ConfigSetup();
            cfgs.startConfig();
            return;
        }
        if (!cfgFile.canRead()) {
            System.err.println("Error reading " + cfgFile.getAbsolutePath() + " file.");
        }
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(cfgFile));
        } catch (FileNotFoundException ex) {
            return;
        }
        try {
            String data;
            while ((data = bf.readLine()) != null) {
                analyzeString(data);
            }
        } catch (IOException ex) {
        }

    }

    public static String getPluginDir() {
        try {

            String dir = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            if (!dir.toString().endsWith(java.io.File.separator)) {
                return dir.toString() + java.io.File.separator;
            }

            return dir.toString();
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private static void analyzeString(String data) {
        if(data.startsWith("#")) return;
        String element = data.substring(0, data.indexOf('='));
        if(element.equals("app")){
            application = data.substring(data.indexOf('=')+1).trim();
        }else if(element.equals("vmipath")){
            vmipath = data.substring(data.indexOf('=')+1).trim();
        }else if(element.equals("bridgedif")){
            bridgedif = data.substring(data.indexOf('=')+1).trim();
        }else if(element.equals("hostoif")){
            hostoif = data.substring(data.indexOf('=')+1).trim();
        }
    }
}
