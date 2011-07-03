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
package kuasar.plugin.servermanager.gui.actions;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.util.List;
import javax.swing.JPanel;
import kuasar.plugin.servermanager.network.utils.Connection;
import kuasar.plugin.servermanager.network.utils.IP;
import kuasar.plugin.utils.pn_Info;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class th_Scan extends Thread {

    private boolean stop=false;
    private pn_Searcher parent;

    public th_Scan(pn_Searcher parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        scan();
        parent.hideStop();
    }
    
    public void abort(){
        stop=true;
    }

    private void scan() {
        List<InterfaceAddress> ias = parent.getInterfaces();
        InterfaceAddress ia;
        parent.setMaxProgress(100);
        float percent=0;
        long addresses=0;
        long current=0;
        float weight=0;
        for(int i =0; i<ias.size(); i++){
            ia =ias.get(i);
            addresses += Math.pow(2,(ia.getAddress().getAddress().length==4 ? 32 : 128) - ia.getNetworkPrefixLength())-2;  
        }
        weight = (float) (100f/(float)addresses);
        for (int i=0; i < ias.size() && !stop; i++) {
            ia=ias.get(i);
            InetAddress inet = ia.getAddress();
            if (ia.getNetworkPrefixLength() > 0) {
                parent.addLog("Local interface address: " + inet.getHostAddress() + "/" + ia.getNetworkPrefixLength());
                byte[] mask = null;
                if (inet.getAddress().length == 4) {
                    mask = IP.getMask4(ia.getNetworkPrefixLength());
                } else {
                    mask = IP.getMask6(ia.getNetworkPrefixLength());
                }
                InetAddress networkIP = null;
                networkIP = IP.getNetAddress(inet, mask);
                if (networkIP == null) {
                    pn_Info.Load((JPanel) parent.getParent(), parent, "Broadcast Exception", "This was an error while broadcast address was getting", pn_Info.ICON_ERROR);
                    return;
                }
                parent.addLog("Network: " + networkIP.getHostAddress());
                InetAddress curIP = IP.getNextIP(networkIP);
                InetAddress broadcast = null;
                try {
                    broadcast = InetAddress.getByAddress(IP.getBroadcast(networkIP, mask));
                } catch (UnknownHostException ex) {
                    broadcast = null;
                }
                if (broadcast != null) {
                    while (!curIP.equals(broadcast) && !stop) {
                        current++;
                        parent.addLog("-> " + curIP.getHostAddress());
                        int status = Connection.checkServer(curIP.getHostAddress(), parent.port, parent.keyStore, parent.kspass, parent.user, parent.pass, parent.dnie);
                        if (status <= 0) {
                            logError(status, curIP.getHostAddress());
                        } else {
                            parent.addServer(curIP.getHostName(), curIP.getHostAddress());
                        }
                        curIP = IP.getNextIP(curIP);
                        percent+=weight;
                        int round = Math.round(percent);
                        parent.setProgress(round);
                        parent.setProgressText(round + "% ( " + current + " / " + addresses + " )");
                    }
                }
            }
        }
    }

    private void logError(int status, String address) {
        String error = "";
        switch (status) {
            case 0:
            case -10:
            case -20:
                error = "Error connection";
                break;
            case -1:
                error = "Server unknown";
                break;
            case -11:
                error = "Error KeyStore";
                break;
            case -12:
                error = "Error algorithm";
                break;
            case -13:
                error = "Error certificate";
                break;
            case -14:
                error = "Error card";
                break;
            case -15:
                error = "Bad PIN";
                break;
            case -16:
                error = "Error gettink key";
                break;
            case -17:
                error = "Bad sign";
                break;
            case -18:
            case -22:
                error = "Bad server";
                break;
            case -19:
                error = "Bad user";
                break;
            case -21:
                error = "Bad user or password";
                break;
            default:
                error = "Unknown error";
        }
        parent.addLog(address + " : " + error);
    }
}
