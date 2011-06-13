/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blasar.Services.Com.vms;

import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public interface PluginInterface {
    
    String getEngine();
    boolean execCMD(StringTokenizer st);
    boolean Start();
    boolean Stop();
   String getPluginName();

    
}
