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
import blasar.Services.Com.vms.VMCommands;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Main implements PluginInterface{

    private Commands curCMD = null;
    @Override
    public String getPluginName(){
        return Config.pluginName;
    }
    @Override
    public String getEngine() {
       return Config.EngineCode;
    }

    @Override
    public boolean Start() {
        Config.load();
        return true;
    }

    @Override
    public boolean Stop() {
        return true;
    }

    @Override
    public VMCommands getInterCom() {
        if(curCMD==null)
            curCMD = new Commands();
        return curCMD;
    }
    
}
