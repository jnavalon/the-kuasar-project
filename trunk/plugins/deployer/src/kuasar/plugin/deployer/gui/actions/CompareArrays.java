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
        long d1RAM = Long.parseLong(d1[3]);
        long d2RAM = Long.parseLong(d2[3]);
        long d1SZ = Long.parseLong(d1[2]);
        long d2SZ = Long.parseLong(d2[2]);
        
        int r;
        if(firstRAM){
            r=CompareNum(d1RAM,d2RAM);
        }else{
            r=CompareNum(d1SZ,d2SZ);
        }
        
        if (r == 0) {
            if(firstRAM)
                r = CompareNum(d1SZ,d2SZ);
            else
                r = CompareNum(d1RAM,d2RAM);
            if(r==0){
                r=1;
            }
        }
        return r;
    }
    
    private short CompareNum(long o1, long o2){
            if(o1<o2){
                return 1;
            }else if(o1>o2){
                return -1;
            }
            return 0;
    }
    
}
