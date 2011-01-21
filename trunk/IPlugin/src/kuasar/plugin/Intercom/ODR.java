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
package kuasar.plugin.Intercom;

import java.lang.reflect.Method;
import java.util.HashMap;
import kuasar.plugin.Global;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class ODR {

    public static Object getValue(String key) {
        Class args[] = new Class[1];
        args[0] = String.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.ODRClass.getMethod("getValue", args);
            return meth.invoke(Global.ODRClassInstance, key);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public static boolean setValue(String key, Object value) {
        Class args[] = new Class[2];
        args[0] = String.class;
        args[1] = Object.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.ODRClass.getMethod("setValue", args);
            Object op = meth.invoke(Global.ODRClassInstance, key, value);
            if (op.toString().equals("true")) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public static boolean putAll(HashMap map) {
        Class args[] = new Class[1];
        args[0] = Object.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.ODRClass.getMethod("putAll", args);
            Object op = meth.invoke(Global.ODRClassInstance, map);
            if (op.toString().equals("true")) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            //System.err.println(ex.getMessage());
            return false;
        }
    }

    public static boolean isSetValue(String key) {
        Class args[] = new Class[1];
        args[0] = String.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.ODRClass.getMethod("isSetValue", args);
            Object op = meth.invoke(Global.ODRClassInstance, key);
            if (op.toString().equals("true")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
