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

/*
 * pn_ServerData.java
 *
 * Created on 22/09/2011, 20:42:12
 */
package kuasar.plugin.servermanager.gui.infoserver;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import kuasar.plugin.utils.Connection;
import kuasar.plugin.utils.SSocketTools;
import kuasar.plugin.utils.Server;
import kuasar.plugin.utils.pn_Info;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_ServerData extends javax.swing.JPanel {

    private DefaultTableModel model = new DefaultTableModel();
    private String address, ks, user;
    private int port;
    private char[] kspwd, userpwd;
    
    public pn_ServerData(String address, int port, String ks, char[] kspwd, String user, char[] userpwd) {
        this.address = address;
        this.ks = ks;
        this.user= user;
        this.port = port;
        this.kspwd = kspwd;
        this.userpwd=userpwd;
        initComponents();
        model.addColumn("Object");
        model.addColumn("Value");

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_mem = new javax.swing.JLabel();
        pgb_mem = new javax.swing.JProgressBar();
        lbl_disk = new javax.swing.JLabel();
        pgb_disk = new javax.swing.JProgressBar();
        spn_data = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        btn_Disk = new javax.swing.JButton();
        btn_OS = new javax.swing.JButton();
        btn_CPU = new javax.swing.JButton();
        btn_Mem = new javax.swing.JButton();

        setOpaque(false);

        lbl_mem.setForeground(new java.awt.Color(204, 204, 204));
        lbl_mem.setText("Memory:");

        pgb_mem.setValue(50);
        pgb_mem.setStringPainted(true);

        lbl_disk.setForeground(new java.awt.Color(204, 204, 204));
        lbl_disk.setText("Disk:");

        pgb_disk.setStringPainted(true);

        spn_data.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        spn_data.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        spn_data.setOpaque(false);

        tbl_data.setModel(model);
        tbl_data.setTableHeader(null);
        spn_data.setViewportView(tbl_data);

        btn_Disk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/hd16.png"))); // NOI18N
        btn_Disk.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_Disk.setBorderPainted(false);
        btn_Disk.setContentAreaFilled(false);
        btn_Disk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Disk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DiskActionPerformed(evt);
            }
        });

        btn_OS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/nogpl/os.png"))); // NOI18N
        btn_OS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_OS.setBorderPainted(false);
        btn_OS.setContentAreaFilled(false);
        btn_OS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_OS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OSActionPerformed(evt);
            }
        });

        btn_CPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/cpu.png"))); // NOI18N
        btn_CPU.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_CPU.setBorderPainted(false);
        btn_CPU.setContentAreaFilled(false);
        btn_CPU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_CPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CPUActionPerformed(evt);
            }
        });

        btn_Mem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/mem.png"))); // NOI18N
        btn_Mem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_Mem.setBorderPainted(false);
        btn_Mem.setContentAreaFilled(false);
        btn_Mem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Mem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spn_data, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_disk)
                            .addComponent(lbl_mem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(pgb_disk, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Disk))
                            .addComponent(pgb_mem, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_OS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_CPU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Mem)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_mem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgb_mem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pgb_disk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_disk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btn_Disk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_OS)
                    .addComponent(btn_CPU, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Mem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spn_data, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        spn_data.getViewport().setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_OSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OSActionPerformed
        loadOS();
    }//GEN-LAST:event_btn_OSActionPerformed

    private void btn_CPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CPUActionPerformed
        loadCPU();
    }//GEN-LAST:event_btn_CPUActionPerformed

    private void btn_MemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MemActionPerformed
        loadMem();
    }//GEN-LAST:event_btn_MemActionPerformed

    private void btn_DiskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DiskActionPerformed
        showFS();
    }//GEN-LAST:event_btn_DiskActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CPU;
    private javax.swing.JButton btn_Disk;
    private javax.swing.JButton btn_Mem;
    private javax.swing.JButton btn_OS;
    private javax.swing.JLabel lbl_disk;
    private javax.swing.JLabel lbl_mem;
    private javax.swing.JProgressBar pgb_disk;
    private javax.swing.JProgressBar pgb_mem;
    private javax.swing.JScrollPane spn_data;
    private javax.swing.JTable tbl_data;
    // End of variables declaration//GEN-END:variables

    public void loadData(){
        SSocketTools st = startConnection();
        if(st==null)return;
        pgb_mem.setValue(Server.getPercentMem(st));
        pgb_disk.setValue(Server.getPercentDisk(st));
        Server.closeServer(st);
        st.closeAll();
    }

    private void loadOS() {
        SSocketTools st = startConnection();
        if(st==null)return;
        model= new DefaultTableModel();
        model.addColumn("Object");
        model.addColumn("Value");
        model.addRow(new String[]{"Name", Server.getOSName(st)});
        model.addRow(new String[]{"Vendor", Server.getOSVendor(st)});
        model.addRow(new String[]{"Arch", Server.getOSArch(st)});
        model.addRow(new String[]{"Version", Server.getOSVersion(st)});
        model.addRow(new String[]{"Uptime", Server.getOSUptime(st)});
        model.addRow(new String[]{"Patch Level", Server.getOSPatchLvl(st)});
        Server.closeServer(st);
        st.closeAll();
        tbl_data.setModel(model);
    }
    private void loadMem() {
        SSocketTools st = startConnection();
        if(st==null)return;
        model= new DefaultTableModel();
        model.addColumn("Object");
        model.addColumn("Value");
        model.addRow(new String[]{"Total", Server.getMEMTotal(st)});
        model.addRow(new String[]{"Used", Server.getMEMUsed(st)});
        model.addRow(new String[]{"Free", Server.getMEMFree(st)});
        model.addRow(new String[]{"Total Swap", Server.getMEMTotalSwap(st)});
        model.addRow(new String[]{"Used Swap", Server.getMEMUsedSwap(st)});
        model.addRow(new String[]{"Free Swap", Server.getMEMFreeSwap(st)});
        Server.closeServer(st);
        st.closeAll();
        tbl_data.setModel(model);
    }
    private void loadCPU(){
        SSocketTools st = startConnection();
        if(st==null)return;
        model= new DefaultTableModel();
        model.addColumn("Object");
        model.addColumn("Value");
        int tInfo = Server.getCPUInfoSize(st);
        for(int i =0; i<tInfo; i++){
            model.addRow(new String[]{"Device #"+ (i+1), ""});
            model.addRow(new String[]{"Vendor", Server.getCPUVendor(st, i)});
            model.addRow(new String[]{"Model", Server.getCPUModel(st, i)});
            model.addRow(new String[]{"Mhz", Server.getCPUMhz(st, i)});
            model.addRow(new String[]{"Cores", Server.getCPUCores(st, i)});
            model.addRow(new String[]{"Cache", Server.getCPUCache(st, i)});
            model.addRow(new String[]{"", ""});
        }
        
        tInfo =Server.getCPUSize(st);
        for(int i=0; i<tInfo; i++){
            model.addRow(new String[]{"CPU #" + (i+1) +" Load", Server.getCPULoad(st, i)});
        }
        Server.closeServer(st);
        st.closeAll();
        tbl_data.setModel(model);
    }
    
    
    
    private SSocketTools startConnection(){
        SSocketTools st = new SSocketTools(address, port, ks, kspwd, user, userpwd);
        if (!st.initSocket()) {
            int status = Connection.checkServer(address, port, ks, kspwd, user, userpwd, false);
            pn_Info.Load((JPanel) this.getParent(), this, "Error connecting to sercer",
                    "Impossible connect to the server <p> Message error :: " + Connection.getErrorDescription(status),
                    pn_Info.ICON_ERROR);
            return null;
        }
        if (!Server.startConnection(st)) {
            pn_Info.Load((JPanel) this.getParent(), this, "Error connecting to server",
                    "It was impossible to connect to the server", pn_Info.ICON_ERROR);
            st.closeAll();
            return null;
        }
        return st;
    }

    private void showFS() {
        pn_Disks disks = new pn_Disks(this,address, port, ks, kspwd, user, userpwd);
        JPanel parent = (JPanel) this.getParent();
        disks.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        parent.removeAll();
        parent.add(disks);
        parent.updateUI();
        disks.loadData();
    }

}
