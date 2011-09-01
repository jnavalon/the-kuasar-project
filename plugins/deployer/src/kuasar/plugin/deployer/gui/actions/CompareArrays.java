/*
 * Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kuasar.plugin.deployer.gui.actions;

import java.util.Comparator;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class CompareArrays implements Comparator {

    private boolean firstRAM; 
    public CompareArrays(boolean firstRAM){
        this.firstRAM = firstRAM;
    }
     
    @Override
    public int compare(Object o1, Object o2) {
        String[] d1 = (String[]) o1;
        String[] d2 = (String[]) o2;
        
        int r;
        if(firstRAM)
            r = d1[3].compareTo(d2[3]);
        else
            r = d1[2].compareTo(d2[2]);
        if (r == 0) {
            if(firstRAM)
                return d1[2].compareTo(d2[2]);
            else
                return d1[3].compareTo(d2[3]);
        } else {
            return r;
        }
    }
    
}
