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
package blasar.Services.Com.vms.virtualbox;

import blasar.Services.Com.vms.PluginInterface;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Main implements PluginInterface{

    @Override
    public String getPluginName(){
        return "VirtualBox";
    }
    @Override
    public String getEngine() {
       return "VBOX";
    }

    @Override
    public boolean execCMD(StringTokenizer st) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean Start() {
        return true;
    }

    @Override
    public boolean Stop() {
        return true;
    }
    
}
