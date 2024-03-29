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

/*
 * pn_CheckServer.java
 *
 * Created on 04/06/2011, 17:57:52
 */
package kuasar.plugin.servermanager.gui.actions;

import java.util.HashMap;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.servermanager.Config;
import kuasar.plugin.servermanager.network.utils.IP;
import kuasar.plugin.utils.Connection;
import kuasar.plugin.utils.dialogs.dg_KeyStore;
import kuasar.plugin.utils.dialogs.dg_Password;
import kuasar.plugin.utils.dialogs.dg_Username;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_CheckServer extends kuasar.plugin.classMod.AbstractPanel implements Runnable {

    /** Creates new form pn_CheckServer */
    pn_AddServer parent;
    String address;
    int port;

    public pn_CheckServer(pn_AddServer parent, String address, int port) {
        this.address = address;
        this.parent = parent;
        this.port = port;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_echo = new javax.swing.JLabel();
        lbl_port = new javax.swing.JLabel();
        lbl_server = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        pb_progress = new javax.swing.JProgressBar();
        lbl_status = new javax.swing.JLabel();
        lbl_title = new javax.swing.JLabel();
        lbl_info = new javax.swing.JLabel();

        setOpaque(false);

        lbl_echo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_echo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/off.png"))); // NOI18N

        lbl_port.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_port.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/closed.png"))); // NOI18N

        lbl_server.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_server.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/bad_server.png"))); // NOI18N

        lbl_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/nologged.png"))); // NOI18N

        pb_progress.setIndeterminate(true);

        lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/face-sad.png"))); // NOI18N
        lbl_status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lbl_title.setFont(new java.awt.Font("Dialog", 1, 18));
        lbl_title.setForeground(new java.awt.Color(204, 204, 204));

        lbl_info.setForeground(new java.awt.Color(204, 204, 204));
        lbl_info.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_echo, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_port, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                        .addGap(47, 47, 47)
                        .addComponent(lbl_server, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_user, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                    .addComponent(pb_progress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                            .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_server, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_echo, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pb_progress, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_title)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                    .addComponent(lbl_status, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_echo;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JLabel lbl_port;
    private javax.swing.JLabel lbl_server;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JProgressBar pb_progress;
    // End of variables declaration//GEN-END:variables

    private boolean check() {
        if(port<1 || port > 65535){
            port = Config.GlobalServerCFG.port;
        }
        if (!Config.GlobalServerCFG.checkServer) {
            return true;
        }
        lbl_title.setText("Is server online?");
        lbl_info.setText("<html>We 're doing a echo test to check if server is online. If check fail, doesn't main server isn't working. Server o firewall can be filter echo test.");
        this.updateUI();
        if (IP.ping(address) > 0) {
            lbl_echo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/on.png")));
            lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/face-plain.png")));
            this.updateUI();
        }
        
        lbl_title.setText("Is port opened?");
        lbl_info.setText("<html>We 're trying to connect to the server. This test can take several minutes.");
        this.updateUI();
        if (Connection.tryConnect(address, port)) {
            lbl_port.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/opened.png")));
            this.updateUI();
        }else{
            lbl_title.setText("No service listening on port!");
            lbl_info.setText("<html>It seems there isn't any service listen on port " + port + "<p> Aborted then.");
            this.updateUI();
            return false;
        }
        lbl_title.setText("Is blasar listening?");
        lbl_info.setText("<html>We 're trying to detect a blasar server");
        this.updateUI();

        String ks;
        char[] kspasswd = null;
        ks = Connection.getKeyStore(address);
        if (ks == null) {
            ks = Config.GlobalServerCFG.keyStore;
            if (ks == null) {
                dg_KeyStore dks = new dg_KeyStore(null, true, address);
                dks.setLocationRelativeTo(this);
                dks.setVisible(true);
                ks = dks.getKeyStore();
                if (ks == null) {
                    lbl_title.setText("Aborted!");
                    lbl_info.setText("<html>A KeyStore is essential to try to connect!");
                    this.updateUI();
                    return false;
                }
                kspasswd = dks.getPassword();
            }
        }
        if (kspasswd == null) {
            kspasswd = Connection.getKeyStorePWD(address);
            if (kspasswd == null) {
                kspasswd = Config.GlobalServerCFG.ksPasswd;
                if (kspasswd == null) {
                    dg_Password dpwd = new dg_Password(null, true);
                    dpwd.setLocationRelativeTo(this);
                    dpwd.setVisible(true);
                    kspasswd = dpwd.getPassword();
                    if (kspasswd == null) {
                        lbl_title.setText("Aborted!");
                        lbl_info.setText("<html>Password is needded!");
                        this.updateUI();
                        return false;
                    }
                }
            }
        }

        int status = Connection.checkServer(address, port, ks, kspasswd, null, null, false);
        if (status < 1) {
            lbl_title.setText("BLASAR IS NOT LISTENING!");
            Connection.delKSPassword(address);
            Connection.delKeyStore(address);
            if (status == -1) {
                lbl_info.setText("<html>We couldn't connect to the Server again! Maybe caused by:<p> - Server was disconnected <br> - Firewall rejects our connection");
            } else {
                lbl_info.setText("<html>Error connecting to the server. Maybe caused by:<p> - Bad certification password <br> - Bad Server");
                
            }
            this.updateUI();
            return false;
        }
        
        lbl_server.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/ok_server.png")));
        lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/face-smile.png")));

        char[] password = null;
        boolean dnie = false;
        if (Config.GlobalServerCFG.checkClient) {
            lbl_title.setText("Can I Loggin?");
            lbl_info.setText("<html>We 're trying to connect to blasar server with your USERNAME/Password or SPANISH DNIe.");
            this.updateUI();
            boolean badClient = false;
            HashMap<String,Object> map = (HashMap<String,Object>) ODR.getValue("#USER_SECRET");
            Object data = (Object) map.get(address + Connection.DNIE);
            String user = null;
            if (data != null) {
                dnie = (Boolean) data;
                if (!dnie) {
                    user = (String) map.get(address + Connection.USERNAME);
                    password =(char[]) map.get(address + Connection.PASSWD);
                    if (user == null) {
                        data = null;
                    }
     
                }
            }
            if (data == null) {
                user = Config.GlobalServerCFG.user;
                if (user == null) {
                    dg_Username duser = new dg_Username(null, true, address);
                    duser.setLocationRelativeTo(this);
                    duser.setVisible(true);
                    user = duser.getUserName();
                    if (user == null) {
                        badClient = true;
                    }
                    password = duser.getPassword();
                    dnie= duser.isDNIe();
                }
            }
            
            if (password==null) {
                dg_Password dpwd = new dg_Password(null, true);
                dpwd.setHeader(dnie?"Insert PIN":"Insert Password");
                dpwd.setLocationRelativeTo(this);
                dpwd.setVisible(true);
                password = dpwd.getPassword();
                if (password == null) {
                    badClient = true;
                }
            }

            if (badClient) {
                lbl_title.setText("Loggin Canceled!");
                lbl_info.setText("<html>Loggin test was canceled because you haven't inserted essential information");
                this.updateUI();
                return false;
            }

            status = 3;
            if (dnie) {
                status = Connection.checkServer(address, port, ks, kspasswd, "", password, true);
            } else {
                status = Connection.checkServer(address, port, ks, kspasswd, user, password, true);
            }
            if (status < 0) {
                lbl_title.setText("Error Logging!");
                switch (status) {
                    case 0:
                        lbl_info.setText("<html>Error Connection:");
                        break;
                    case -1:
                    case -10:
                    case -20:
                        lbl_info.setText("<html>Unknow Server, Impossible to connect to the server.");
                        break;
                    case -11:
                        lbl_info.setText("<html>Error accessing to KeyStore.");
                        break;
                    case -12:
                        lbl_info.setText("<html>Algorithm to sign tickets is not available on this system.");
                        break;
                    case -13:
                        lbl_info.setText("<html>Error getting your certificate (DNIe). Check your device or card!");
                        break;
                    case -14:
                        lbl_info.setText("<html>Error connecting to DNIe. Check if your device is your card is well introduced");
                        break;
                    case -15:
                        lbl_info.setText("<html>PIN is incorrect! Be careful!");
                        lbl_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/logged-error.png")));
                        break;
                    case -16:
                        lbl_info.setText("<html>Impossible to get your PrivateKey to sign ticket.");
                        break;
                    case -17:
                        lbl_info.setText("<html>Error signing ticket.");
                        break;
                    case -18:
                    case -22:
                        lbl_info.setText("<html>Bad Server. Maybe it's not a blasar server or it's an old version.");
                        break;
                    case -19:
                    case -21:
                        lbl_info.setText("<html>Bad User/Password or your DNIe is not accepted on blasar server.");
                        lbl_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/logged-error.png")));
                        deletePassword();
                        break;
                }
                this.updateUI();
                return false;
            }
            lbl_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/logged.png")));
            lbl_status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/face-laugh.png")));
            lbl_title.setText("Congrats!");
            lbl_info.setText("<html>All went fine!");
            this.updateUI();
        }

        return true;
    }
    private void deletePassword(){
        Connection.delUserSecret(address);
    }

    @Override
    public void run() {
        if (check()) {
            parent.Save();
        } else {
            parent.setManual();
        }
        pb_progress.setIndeterminate(false);
        pb_progress.setMaximum(100);
        pb_progress.setValue(100);
    }
}
