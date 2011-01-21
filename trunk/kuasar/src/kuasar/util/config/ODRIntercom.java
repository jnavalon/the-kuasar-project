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
public class ODRIntercom {

    public Object getValue(String key) {
        return Configuration.ODR.get(key);
    }

    public boolean setValue(String key, Object value) {
        if (key.startsWith("$")) {
            return false;
        }
        Configuration.ODR.put(key, value);
        return true;
    }

    public boolean putAll(HashMap<String, Object> map) {
        try {
            Configuration.ODR.putAll(map);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean isSetValue(String key) {
        if (Configuration.ODR.get(key) == null) {
            return false;
        }
        return true;
    }
}
