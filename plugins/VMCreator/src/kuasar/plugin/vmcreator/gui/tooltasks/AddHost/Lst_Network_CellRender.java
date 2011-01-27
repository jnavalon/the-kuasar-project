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
package kuasar.plugin.vmcreator.gui.tooltasks.AddHost;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Lst_Network_CellRender implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        try {   
            Object data[] = (Object[]) value;
            pn_NetList pn_Device=null;
            ImageIcon icon = null;
            switch ((Integer) data[1]) {
                case 0:
                    icon=new javax.swing.ImageIcon(getClass().getResource("/icons/nat.png"));
                    break;
                case 1:
                    icon=new javax.swing.ImageIcon(getClass().getResource("/icons/bridge.png"));
                    break;
                case 2:
                    icon=new javax.swing.ImageIcon(getClass().getResource("/icons/intnet.png"));
                    break;
                case 3:
                    icon=new javax.swing.ImageIcon(getClass().getResource("/icons/nethost.png"));
                    break;
            }
            if(data.length==2)
                pn_Device = new pn_NetList((String) data[0], icon, isSelected);
            else
                pn_Device = new pn_NetList((String) data[0], icon, false);
            return pn_Device;
        }catch(Exception ex){
            return null;
        }
    }
}
