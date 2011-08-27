/*
 *  Copyright (C) 2010 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
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
 * pn_Add.java
 *
 * Created on 31/10/2010, 12:02:22
 */

package kuasar.plugin.vmcreator.gui.tooltasks.AddHost;

import java.awt.Color;
import java.net.URL;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.vmcreator.gui.tooltasks.AddHost.Namespace.*;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_AddHV extends kuasar.plugin.classMod.AbstractPanel {

    private boolean locked = false;
    private pn_TB_AddHost toolbar = null;

    public pn_AddHV(pn_TB_AddHost toolbar) {
        this.toolbar = toolbar;
        initComponents();
        hideID();
        load();
    }

    private void load(){

        if(!toolbar.data.containsKey(keyMaps.HYPERVISOR))
            return;

        changelock((Boolean) toolbar.data.get(keyMaps.HYPERVISOR_SAFE));
        if(toolbar.data.get(keyMaps.HYPERVISOR).equals(Namespace.HiperVisors.UNDEFINED)){
            cmb_hvs.setSelectedIndex(0);
            return;
        }

        int i;
        for( i = 1; i<cmb_hvs.getItemCount()-1; i++){
            if(getHVCode(i).equals((String) toolbar.data.get(keyMaps.HYPERVISOR))){
                cmb_hvs.setSelectedIndex(i);
                return;
            }
        }
        cmb_hvs.setSelectedIndex(i);
        txt_ID.setText((String)toolbar.data.get(keyMaps.HYPERVISOR));

    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_title = new javax.swing.JLabel();
        lbl_icon = new javax.swing.JLabel();
        cmb_hvs = new javax.swing.JComboBox();
        lbl_default = new javax.swing.JLabel();
        lbl_ID = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        lbl_help = new javax.swing.JLabel();
        btn_Cancel = new javax.swing.JButton();
        btn_Next = new javax.swing.JButton();

        setOpaque(false);

        lbl_title.setForeground(new java.awt.Color(204, 204, 204));
        lbl_title.setText("Select a Virtual Machine Hypervisor");

        lbl_icon.setForeground(new java.awt.Color(204, 204, 204));
        lbl_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/random.png"))); // NOI18N
        lbl_icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl_icon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        cmb_hvs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Undefined", "VirtualBox", "VMWare", "QEMU / KVM", "XEN", "Virtual PC", "OTHERS HVs" }));
        cmb_hvs.setLightWeightPopupEnabled(false);
        cmb_hvs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_hvsItemStateChanged(evt);
            }
        });

        lbl_default.setForeground(new java.awt.Color(204, 204, 204));
        lbl_default.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_default.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/unlock.png"))); // NOI18N
        lbl_default.setText("Define as default Hipervisor for this session");
        lbl_default.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_defaultMouseReleased(evt);
            }
        });

        lbl_ID.setForeground(new java.awt.Color(204, 204, 204));
        lbl_ID.setText("Hypervisor ID:");

        txt_ID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ID.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        txt_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDActionPerformed(evt);
            }
        });
        txt_ID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_IDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_IDFocusLost(evt);
            }
        });

        lbl_help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/info.png"))); // NOI18N
        lbl_help.setToolTipText("Hypervisor ID is required for identify a Hypervisor Virtual Machine.\nFor exemple 'VBOX' ID represents a VirtualBox Hypervisor.\nYou'll find available ID's on blasar help, just type the argument '-I'. ");
        lbl_help.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_Cancel.setBackground(new java.awt.Color(0, 0, 0));
        btn_Cancel.setForeground(new java.awt.Color(204, 204, 204));
        btn_Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/dialog-cancel.png"))); // NOI18N
        btn_Cancel.setText("Cancel");
        btn_Cancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });

        btn_Next.setBackground(new java.awt.Color(0, 0, 0));
        btn_Next.setForeground(new java.awt.Color(204, 204, 204));
        btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/dialog-next.png"))); // NOI18N
        btn_Next.setText("Next");
        btn_Next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_default, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_help))
                                    .addComponent(lbl_ID)
                                    .addComponent(cmb_hvs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_Next)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(cmb_hvs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lbl_ID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_help)
                            .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbl_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(lbl_default)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Cancel)
                    .addComponent(btn_Next))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_defaultMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_defaultMouseReleased
        changelock(!locked);
    }//GEN-LAST:event_lbl_defaultMouseReleased

    private void cmb_hvsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_hvsItemStateChanged
        URL iconpath = null;
        switch(cmb_hvs.getSelectedIndex()){
            case 0:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/random.png");
                break;
            case 1:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/noGPL/virtualbox.png");
                break;
            case 2:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/noGPL/vmware.png");
                break;
            case 3:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/noGPL/qemu.png");
                break;
            case 4:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/noGPL/xen.png");
                break;
            case 5:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/noGPL/virtualpc.png");
                break;
            case 6:
            default:
                iconpath = getClass().getResource("/kuasar/plugin/vmcreator/icons/unknownhv.png");
        }
        if(cmb_hvs.getSelectedIndex()==6){
            showID();
        }else{
            hideID();
        }
        lbl_icon.setIcon(new javax.swing.ImageIcon(iconpath));
        GUI.updateUI();
    }//GEN-LAST:event_cmb_hvsItemStateChanged

    private void txt_IDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_IDFocusLost
        repareID();
    }//GEN-LAST:event_txt_IDFocusLost

    private void txt_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDActionPerformed
        repareID();
    }//GEN-LAST:event_txt_IDActionPerformed

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
       toolbar.exit();
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NextActionPerformed
        if(save())
            toolbar.next();
    }//GEN-LAST:event_btn_NextActionPerformed

    private void txt_IDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_IDFocusGained
        txt_ID.setBackground(Color.WHITE);
    }//GEN-LAST:event_txt_IDFocusGained

    private void changelock(boolean lock){
         locked =lock;
         if(locked)
            lbl_default.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/lock.png")));
        else
            lbl_default.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/unlock.png")));
        GUI.updateUI();
    }
    
    protected boolean save(){
        if(cmb_hvs.getSelectedIndex()== cmb_hvs.getItemCount()-1){
            if(txt_ID.getText().trim().isEmpty()){
                txt_ID.setBackground(Color.RED);
                return false;
            }
        }
        
        toolbar.data.put(keyMaps.HYPERVISOR_SAFE, locked);
        toolbar.data.put(keyMaps.HYPERVISOR, getHVCode());
        toolbar.data.put(keyMaps.HYPERVISOR+".name", cmb_hvs.getSelectedItem());
        return true;
    }

    private void repareID(){
        String aux = txt_ID.getText().toUpperCase().trim();
        if(aux.isEmpty())return;
        if(aux.length()>3)
            aux=aux.substring(0,3);
        while(aux.length()<3)
            aux=aux.concat("_");
        txt_ID.setText(aux);
    }
    private String getHVCode(){
        return getHVCode(cmb_hvs.getSelectedIndex());
    }
    private String getHVCode(int id){
                switch(id){
            case 1:
                return HiperVisors.VIRTUALBOX;
            case 2:
                return HiperVisors.VMWARE;
            case 3:
                return HiperVisors.QEMU;
            case 4:
                return HiperVisors.XEN;
            case 5:
                return HiperVisors.VIRTUALPC;
            case 6:
               return txt_ID.getText().substring(0, 3);
            default:
                return HiperVisors.UNDEFINED;
        }
    }

    private void showID(){
        lbl_ID.setVisible(true);
        lbl_help.setVisible(true);
        txt_ID.setVisible(true);
    }

    private void hideID(){
        lbl_ID.setVisible(false);
        lbl_help.setVisible(false);
        txt_ID.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_Next;
    private javax.swing.JComboBox cmb_hvs;
    private javax.swing.JLabel lbl_ID;
    private javax.swing.JLabel lbl_default;
    private javax.swing.JLabel lbl_help;
    private javax.swing.JLabel lbl_icon;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JTextField txt_ID;
    // End of variables declaration//GEN-END:variables

}
