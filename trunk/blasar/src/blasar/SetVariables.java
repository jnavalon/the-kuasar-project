/*
 *  Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
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

package blasar;

import java.io.File;
import java.net.URISyntaxException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class SetVariables {
    private boolean status = true;
    public SetVariables(){
        Config.BLASAR.startDir = getStartDir();
        if(Config.BLASAR.startDir == null) status = false;
    }

    public boolean isOK(){
        return status;
    }
    
    private String getStartDir() {
        try {

            String dir = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

            if (!dir.toString().endsWith(java.io.File.separator)) {
                return dir.toString() + java.io.File.separator;
            }

            return dir.toString();
        } catch (URISyntaxException ex) {
            return null;
        }
    }
}
