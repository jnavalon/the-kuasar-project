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

import blasar.Services.Com.vms.VMCommands;
import blasar.Services.Com.vms.virtualbox.processes.Machines;
import java.io.File;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Commands implements VMCommands {

    @Override
    public long getFreeSpace() {
        File dir = new File(Config.vmipath);
        return dir.getFreeSpace();
    }

    @Override
    public String[] getRegisteredMachines() {
        return Machines.getRegisteredMachines();
    }

    @Override
    public String getSysProperites(String key) {
        return Machines.getSysProperties(key);
    }

    @Override
    public String createvm(String name, String os) {
        return Machines.createMachine(os, name);
    }

    @Override
    public String getImagePath() {
        return Config.vmipath;
    }

    @Override
    public boolean setMemory(String uuid, Long memory) {
        return Machines.setMemory(uuid, memory);
    }

    @Override
    public boolean storageCtl(String uuid, String type, String controller, boolean cache) {
         return Machines.setStorageCtl(type.toUpperCase() + " Module", uuid, type, controller, cache);
    }

    @Override
    public boolean storageattach(String uuid, String storagectl, int port, int device, String type, String filepath, boolean passthrought) {
        return Machines.addStorage(uuid, storagectl, port, device, type, filepath, passthrought);
    }

    @Override
    public boolean addNIC(String uuid, int nicID, String type, String mac) {
        return Machines.addNIC(uuid, nicID, type, mac);
    }

    @Override
    public boolean storageattach(String uuid, String storagectl, int port, int device, String type, boolean passthrought) {
        return Machines.addEmptyDevice(uuid, storagectl, port, device, type, passthrought);
    }

    @Override
    public boolean setNetwork(String uuid, String operator, String username, String password, String mac, String ip, String mask, String gw, String dns) {
        return Machines.setNetwork(uuid, operator, username, password, mac, ip, mask, gw, dns);
    }

    @Override
    public String[] getRunningMachines() {
        return Machines.getRunningMachines();
    }

    @Override
    public String getEngineName() {
        return Config.EngineName;
    }

    @Override
    public String getMachineName(String uuid) {
        return Machines.getMachineName(uuid);
    }

    @Override
    public String getMachineUUID(String name) {
        return Machines.getMachineUUID(name);
    }

    @Override
    public boolean isRunning(String uuid) {
        return Machines.isRunning(uuid);
    }

    @Override
    public boolean RunMachine(String uuid) {
        return Machines.sendPower(uuid, Machines.PW_START);
    }

    @Override
    public boolean ShutdownMachine(String uuid) {
        return Machines.sendPower(uuid, Machines.PW_ACPIOFF);
    }

    @Override
    public boolean ResetMachine(String uuid) {
        return Machines.sendPower(uuid, Machines.PW_RESET);
    }

    @Override
    public boolean PowerOffMachine(String uuid) {
        return Machines.sendPower(uuid, Machines.PW_POWEROFF);
    }

    @Override
    public boolean deleteVM(String uuid) {
        return Machines.deleteVM(uuid);
    }
    
}
