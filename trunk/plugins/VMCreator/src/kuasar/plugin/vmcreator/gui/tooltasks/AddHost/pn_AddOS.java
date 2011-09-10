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
 * pn_AddOS.java
 *
 * Created on 02/11/2010, 17:14:35
 */

package kuasar.plugin.vmcreator.gui.tooltasks.AddHost;

import java.awt.event.ItemEvent;
import javax.swing.ImageIcon;
import kuasar.plugin.vmcreator.gui.tooltasks.AddHost.Namespace.*;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_AddOS extends kuasar.plugin.classMod.AbstractPanel {

    pn_TB_AddHost toolbar = null;
    /** Creates new form pn_AddOS */
    public pn_AddOS(pn_TB_AddHost toolbar) {
        this.toolbar = toolbar;
        initComponents();
        loadSystems();
        load();
    }
    private void load(){
        if(!toolbar.data.containsKey(keyMaps.OS))
            return;
        int i;
        for(i=0; i<cmb_OS.getItemCount(); i++){
            if(cmb_OS.getItemAt(i).equals(toolbar.data.get(keyMaps.OS))){
                cmb_OS.setSelectedIndex(i);
                break;
            }
        }
        loadVersions(i);
        for(i=0; i<cmb_Version.getItemCount(); i++){
            if(cmb_Version.getItemAt(i).equals(toolbar.data.get(keyMaps.OS_VERSION))){
                cmb_Version.setSelectedIndex(i);
                break;
            }
        }
        if(toolbar.data.get(keyMaps.OS_VERSION + ".arc").equals("64")){
            rbt_64.setSelected(true);
        }else{
            rbt_32.setSelected(true);
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Arc = new javax.swing.ButtonGroup();
        btn_Next = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        btn_Previous = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_logo = new javax.swing.JLabel();
        cmb_OS = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmb_Version = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        rbt_32 = new javax.swing.JRadioButton();
        rbt_64 = new javax.swing.JRadioButton();

        setOpaque(false);

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

        btn_Previous.setBackground(new java.awt.Color(0, 0, 0));
        btn_Previous.setForeground(new java.awt.Color(204, 204, 204));
        btn_Previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/dialog-previous.png"))); // NOI18N
        btn_Previous.setText("Previous");
        btn_Previous.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PreviousActionPerformed(evt);
            }
        });

        jPanel1.setOpaque(false);

        lbl_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/windows.png"))); // NOI18N

        cmb_OS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_OSItemStateChanged(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("OS:");

        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Version:");

        cmb_Version.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_VersionItemStateChanged(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Choose an Operating System:");

        Arc.add(rbt_32);
        rbt_32.setForeground(new java.awt.Color(204, 204, 204));
        rbt_32.setSelected(true);
        rbt_32.setText("32 bits");
        rbt_32.setOpaque(false);

        Arc.add(rbt_64);
        rbt_64.setForeground(new java.awt.Color(204, 204, 204));
        rbt_64.setText("64 bits");
        rbt_64.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbl_logo)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmb_OS, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_Version, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbt_32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbt_64)))))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lbl_logo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel2)
                                .addGap(15, 15, 15)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmb_OS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmb_Version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbt_32)
                            .addComponent(rbt_64))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addComponent(btn_Previous)
                .addGap(7, 7, 7)
                .addComponent(btn_Next)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Cancel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Cancel)
                    .addComponent(btn_Previous)
                    .addComponent(btn_Next))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NextActionPerformed
        save();
        toolbar.next();
}//GEN-LAST:event_btn_NextActionPerformed

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        toolbar.exit();
}//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_PreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PreviousActionPerformed
        toolbar.previous();
    }//GEN-LAST:event_btn_PreviousActionPerformed

    private void cmb_OSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_OSItemStateChanged
        if(evt.getStateChange() != ItemEvent.SELECTED) return;
        loadVersions(cmb_OS.getSelectedIndex());
        changeIcon();
        checkArc();
    }//GEN-LAST:event_cmb_OSItemStateChanged

    private void cmb_VersionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_VersionItemStateChanged
        if(evt.getStateChange() != ItemEvent.SELECTED) return;
        changeIcon();
        checkArc();
    }//GEN-LAST:event_cmb_VersionItemStateChanged

    private void changeIcon(){
        lbl_logo.setIcon(getIcon(cmb_OS.getSelectedIndex(), cmb_Version.getSelectedIndex()));
        this.updateUI();
    }
    private ImageIcon getIcon(int OS, int version){
        switch(OS){
            case 0:
                return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/windows.png"));
            case 1:
                switch(version){
                    case 0:
                    case 1:
                    case 2:
                    case 10:
                    case 12:
                    case 14:
                         return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/penguin.png"));
                    case 3:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/archlin.png"));
                    case 4:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/debian.png"));
                    case 5:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/suse.png"));
                    case 6:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/fedora.png"));
                    case 7:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/gentoo.png"));
                    case 8:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/mandriva.png"));
                    case 9:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/redhat.png"));
                    case 11:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/ubuntu.png"));
                    case 13:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/oracle.png"));
                }
            case 2:
                return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/opensolaris.png"));
            case 3:
                switch(version){
                    case 0:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/freebsd.png"));
                    case 1:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/openbsd.png"));
                    case 2:
                        return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/netbsd.png"));
                }
            case 4:
            case 6:
                return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/oos.png"));
            case 5:
                return new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/mac.png"));
        }
        return null;
    }
    private void loadSystems(){
        cmb_OS.removeAllItems();
        for(String system : OSystems.SYSTEMS){
            cmb_OS.addItem(system);
        }
        loadVersions(0);
    }

    private void loadVersions(int os){
        cmb_Version.removeAllItems();
        for(String version: getVersionsList(os)){
            cmb_Version.addItem(version);
        }
    }

    private String[] getVersionsList(int os){
        switch(os){
            case 0:
                return OSystems.WINDOWS;
            case 1:
                return OSystems.LINUX;
            case 2:
                return OSystems.SOLARIS;
            case 3:
                return OSystems.BSD;
            case 4:
                return OSystems.IBMOS2;
            case 5:
                return OSystems.MACOSX;
            case 6:
                return OSystems.OTHER;
            default:
                return null;
        }
    }
    private String[] getVersionKeyList(int os){
        switch(os){
            case 0:
                return OSKeys.WINDOWS;
            case 1:
                return OSKeys.LINUX;
            case 2:
                return OSKeys.SOLARIS;
            case 3:
                return OSKeys.BSD;
            case 4:
                return OSKeys.IBMOS2;
            case 5:
                return OSKeys.MACOSX;
            case 6:
                return OSKeys.OTHER;
            default:
                return null;
        }
    }
    
    private void save(){
        toolbar.data.put(keyMaps.OS, (String) cmb_OS.getSelectedItem());
        toolbar.data.put(keyMaps.OS+".id", cmb_OS.getSelectedIndex());
        toolbar.data.put(keyMaps.OS_VERSION, (String) cmb_Version.getSelectedItem());
        toolbar.data.put(keyMaps.OS+".key", OSKeys.SYSTEMS[cmb_OS.getSelectedIndex()]);
        toolbar.data.put(keyMaps.OS_VERSION + ".key", getVersionKeyList(cmb_OS.getSelectedIndex())[cmb_Version.getSelectedIndex()]);
        toolbar.data.put(keyMaps.OS_VERSION + ".arc", rbt_32.isSelected()?"32":"64");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Arc;
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_Next;
    private javax.swing.JButton btn_Previous;
    private javax.swing.JComboBox cmb_OS;
    private javax.swing.JComboBox cmb_Version;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JRadioButton rbt_32;
    private javax.swing.JRadioButton rbt_64;
    // End of variables declaration//GEN-END:variables

    private void checkArc() {
        switch(cmb_OS.getSelectedIndex()){
            case 0:
                checkWin();
                break;
            case 1:
                checkLin();
                break;
            case 2:
                checkSOL();
                break;
            case 3:
                checkBSD();
                break;
            case 4:
                checkIBM();
                break;
            case 5:
                checkMAC();
                break;
            case 6:
                checkOTH();
                break;
        }
    }
    private void checkWin(){
        switch(cmb_Version.getSelectedIndex()){
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                setOnly32(false);
                break;
            default:
                setOnly32(true);
        }
    }
    private void checkLin(){
        switch(cmb_Version.getSelectedIndex()){
            case 0:
            case 14:
                setOnly32(true);
                break;
            default:
                setOnly32(false);
        }
    }
    private void checkIBM(){
        setOnly32(true);
    }
    private void checkSOL(){
        setOnly32(false);
    }
    private void checkBSD(){
        setOnly32(false);
    }
    private void checkMAC(){
        setOnly32(false);
    }
    private void checkOTH(){
        setOnly32(true);
    }
    private void setOnly32(boolean activate){
        if(activate){
            rbt_32.setSelected(true);
        }
        rbt_64.setEnabled(!activate);
    }

}
