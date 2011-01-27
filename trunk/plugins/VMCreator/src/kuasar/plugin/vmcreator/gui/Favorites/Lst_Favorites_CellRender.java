/*
 *  Copyright (C) 2011 jnavalon
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

package kuasar.plugin.vmcreator.gui.Favorites;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author jnavalon
 */
public class Lst_Favorites_CellRender implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        pn_FavoriteList panel = new pn_FavoriteList((Object[]) value, isSelected);
        return panel;
        
    }

}
