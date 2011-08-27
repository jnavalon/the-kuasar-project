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
    public boolean createvm(String name, String os);
}
