/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blasar.Services.Com.vms;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public interface PluginInterface {

    String getEngine();

    boolean Start();

    boolean Stop();

    String getPluginName();
    
    VMCommands getInterCom();
}
