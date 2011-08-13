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
import javax.swing.JPanel;
import kuasar.plugin.Global;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class GUI {

    public static boolean launchInfo(String msg) {
        Class args[] = new Class[1];
        args[0] = String.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.mainClass.getMethod("launchInfo", args);
            meth.invoke(Global.mainClassInstance, msg);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public static java.awt.Point getFrameLocation(){
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.mainClass.getMethod("getFrameLocation");
            return (java.awt.Point) meth.invoke(Global.mainClassInstance);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public static boolean loadPlugin(JPanel panel) {
        Class args[] = new Class[1];
        args[0] = JPanel.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.mainClass.getMethod("loadPlugin", args);
            meth.invoke(Global.mainClassInstance, panel);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public static boolean unLoadPlugin() {
        return send("unLoadPlugin");
    }

    public static boolean loadToolBar(JPanel panel) {
        Class args[] = new Class[1];
        args[0] = JPanel.class;
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.mainClass.getMethod("loadToolBar", args);
            meth.invoke(Global.mainClassInstance, panel);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public static boolean unLoadToolBar() {
        return send("unLoadToolBar");
    }

    public static boolean clearInfo() {
        return send("clearInfo");
    }

    public static boolean updateUI() {
        return send("updateUI");
    }

    public static boolean visibleToolBar() {
        return send("visibleToolBar");
    }

    public static boolean invisibleToolBar() {
        return send("invisibleToolBar");
    }

    public static String getInfo() {
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.mainClass.getMethod("getInfo");
            return (String) meth.invoke(Global.mainClassInstance);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    private static boolean send(String method) {
        try {
            @SuppressWarnings("unchecked")
            Method meth = Global.mainClass.getMethod(method);
            meth.invoke(Global.mainClassInstance);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
