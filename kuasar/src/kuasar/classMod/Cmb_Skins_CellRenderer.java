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

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import kuasar.util.config.ScreenShootTemplate;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Cmb_Skins_CellRenderer implements ListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        try{
            ScreenShootTemplate template = new ScreenShootTemplate();
            Object data[] = (Object[]) value;
            if(data[0]!=null)
                template.setText((String) data[0]);
            if(data[1]!=null)
                template.setIcon(kuasar.util.Image.resize((Icon) data[1], template.getMaxIconSize(), template.getMaxIconSize()));
            return template;
        }catch(Exception ex){
            return new javax.swing.JPanel();
        }
    }

}
