/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kuasar.plugin.vmcreator;

import kuasar.plugin.vmcreator.gui.pn_Main;
import kuasar.plugin.vmcreator.gui.pn_ToolBar;
import javax.swing.ImageIcon;
import kuasar.plugin.Global;
import kuasar.plugin.utils.XML;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class VMCreator implements kuasar.plugin.PluginInterface {

    public String getName() {
        return "VM Creator";
    }

    public ImageIcon getIcon() {
        try {
            ImageIcon icon = new javax.swing.ImageIcon(this.getClass().getResource("/kuasar/plugin/vmcreator/icons/icon"));
            return icon;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public boolean Start(Object ODRClassInstance, Class ODRClass) {
        
        Global.ODRClass=ODRClass;
        Global.ODRClassInstance = ODRClassInstance;
         XML.Load(Config.path, Config.network);
         return true;

    }

    public boolean Load(Object mainClassInstance, Class mainClass) {
        Global.mainClass = mainClass;
        Global.mainClassInstance = mainClassInstance;
        pn_Main panel = new pn_Main();
        kuasar.plugin.Intercom.GUI.loadToolBar(new pn_ToolBar(panel));
        kuasar.plugin.Intercom.GUI.loadPlugin(panel);
        kuasar.plugin.Intercom.GUI.visibleToolBar();
        return true;
    }

    public boolean unLoad() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
