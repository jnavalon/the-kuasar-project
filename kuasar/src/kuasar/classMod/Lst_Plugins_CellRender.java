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
package kuasar.classMod;

import kuasar.plugin.classMod.Panel_Opaque;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import kuasar.templates.pn_PluginTemplate;
import kuasar.templates.pn_PluginTemplateT;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Lst_Plugins_CellRender implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        try {
         
            Object data[] = (Object[]) value;
            if (isSelected) {
                data[0] = "0";
            } else {
                data[0] = "1";
            }
            switch (Integer.parseInt((String) data[0])) {
                case 0:
                    pn_PluginTemplate pn_Transparent = new pn_PluginTemplate();
                    pn_Transparent.lbl_Plugin.setText((String) data[1]);
                    pn_Transparent.lbl_Plugin.setIcon((ImageIcon) data[2]);
                    pn_Transparent.setEnabled(list.isEnabled());
                    pn_Transparent.setFont(list.getFont());
                    return pn_Transparent;
                case 1:
                    pn_PluginTemplateT pn_Template = new pn_PluginTemplateT();
                    pn_Template.lbl_Plugin.setText((String) data[1]);
                    pn_Template.lbl_Plugin.setIcon((ImageIcon) data[2]);
                    pn_Template.setEnabled(list.isEnabled());
                    pn_Template.setFont(list.getFont());
                    return pn_Template;
                default:
                    return new Panel_Opaque();
            }
        }catch(Exception ex){
            return new Panel_Opaque();
        }
    }
}
