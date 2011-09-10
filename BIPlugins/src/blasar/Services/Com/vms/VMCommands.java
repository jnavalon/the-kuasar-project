/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blasar.Services.Com.vms;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public interface VMCommands {
    public long getFreeSpace();
    public String[] getRegisteredMachines();
    public String getSysProperites(String key);
    public String createvm(String name, String os);
    public String getImagePath();
    public boolean setMemory(String uuid, Long memory);
    public boolean storageCtl(String uuid, String type, String controller, boolean cache);
    public boolean storageattach(String uuid, String storagectl, int port, int device, String type, String filepath, boolean passthrought);
    public boolean addNIC(String uuid, String nicID, String type);
}
