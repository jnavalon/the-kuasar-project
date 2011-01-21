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
package kuasar.util.config;

import java.util.HashMap;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Configuration {

    public final class Interfice {

        public static final String pathskins = "skins";
        public static final String cfgskin = "config";
        public static final String skin = "default";
        public static final String nimrod_theme = "nimrod.theme";
        public static final String lang = "en";
        public static final String pathlang = "i18n";

        public final class Images {

            public static final String header = "header";
            public static final String downbar = "downbar";
            public static final String adjust = "adjust";
            public static final String hide = "hide";
            public static final String show = "show";
        }
    }

    public final class Plugins {

        public static final String pathplugins = "plugins";
        public static final String extPlug = "jet"; //Plugin extension
        public static final String pluginsKey = "$PLUGINS";
    }
    public static String startDir = "./";
    public static final String cfgFile = "preferences";
    protected static HashMap<String, Object> ODR = new HashMap<String, Object>();
    public static HashMap Preferences = new HashMap();

    public static void addODR(String key, Object object) {

        ODR.put(key, object);

    }

    public static Object getODR(String key) {

        return ODR.get(key);

    }
}
