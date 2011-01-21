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

package kuasar.plugin.classMod;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public abstract class AbstractPanel extends kuasar.plugin.classMod.Panel_Opaque {

    public boolean changed = false;

    public boolean unLoad(JPanel parent){
        return unLoad(parent,"Data won't be saved, would you close it?", "Unsaved data" );
    }

    public boolean unLoad(JPanel parent, String title, String msg){
           if(changed)
            if(JOptionPane.showConfirmDialog(this, msg ,title ,JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)== JOptionPane.NO_OPTION)
                return false;
        return true;
    }

}
