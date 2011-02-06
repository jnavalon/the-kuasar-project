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
 * pn_AddGroup.java
 *
 * Created on 25/08/2010, 12:41:50
 */
package kuasar.plugin.vmcreator.gui.tooltasks;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.utils.XML;
import kuasar.plugin.vmcreator.Config;
import kuasar.plugin.vmcreator.gui.pn_Main;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_AddGroup extends kuasar.plugin.classMod.AbstractPanel {

    private pn_Main panel;

    /** Creates new form pn_AddGroup */
    public pn_AddGroup(pn_Main panel) {
        panel.tb.setVisible(false);
        initComponents();
        this.panel = panel;
        lbl_Error.setVisible(false);
        this.requestFocus();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_info = new javax.swing.JLabel();
        btn_Add = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        txt_name = new javax.swing.JTextField();
        lbl_Error = new javax.swing.JLabel();

        setOpaque(false);

        lbl_info.setFont(new java.awt.Font("URW Gothic L", 1, 14)); // NOI18N
        lbl_info.setForeground(new java.awt.Color(204, 204, 204));
        lbl_info.setText("Insert a project name:");

        btn_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/dialog-ok-apply.png"))); // NOI18N
        btn_Add.setText("Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        btn_Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/dialog-cancel.png"))); // NOI18N
        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelActionPerformed(evt);
            }
        });

        txt_name.setFont(new java.awt.Font("URW Gothic L", 0, 16)); // NOI18N
        txt_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_name.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_nameCaretUpdate(evt);
            }
        });
        txt_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nameKeyReleased(evt);
            }
        });

        lbl_Error.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Error.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/forbidden.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_Error, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_info))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(txt_name, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(235, Short.MAX_VALUE)
                                .addComponent(btn_Add)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lbl_info)
                .addGap(42, 42, 42)
                .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_Error, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Add)
                    .addComponent(btn_Cancel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelActionPerformed
        close();
    }//GEN-LAST:event_btn_CancelActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        accept();
    }//GEN-LAST:event_btn_AddActionPerformed

    private void txt_nameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_nameCaretUpdate

         lockANDnotify(false, "");
        if(txt_name.getText().trim().length() > 9){
            lbl_Error.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/warning.png")));
            lbl_Error.setText("The group name is too long. Name won't be shown completly at a glance.");
            lbl_Error.setVisible(true);
        }
    }//GEN-LAST:event_txt_nameCaretUpdate

    private void txt_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nameKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            accept();
    }//GEN-LAST:event_txt_nameKeyReleased
    private void accept(){
        Element root = XML.getElementOnPath(panel.onAir, (Element) ODR.getValue(Config.path + "." + Config.network));
        if (txt_name.getText().trim().length() == 0) {
            lockANDnotify(true, "Please, insert a name.");
            return;
        }

        if(Character.isDigit(txt_name.getText().charAt(0))){
            lockANDnotify(true, "Name can't begin with numbers.");
            return;
        }

        if (!checkName()) {
            lockANDnotify(true, "Name only can contains letters, numbers and spaces.");
            return;
        }
        Element test = root.getChild(transformNodeName());
        if (test != null) {
            lockANDnotify(true, "Group exists, Please check the name and try again.");
            return;
        }
        List<String[]> attributs = new ArrayList<String[]>();
        attributs.add(new String[]{"name", txt_name.getText()});
        attributs.add(new String[]{"type", ""});
        root= XML.getElementOnPath(".", (Element) ODR.getValue(Config.path + "." + Config.network));
        if(XML.AddElement(panel.onAir, root, transformNodeName(), attributs))
            kuasar.plugin.Intercom.GUI.launchInfo("Group \"" + txt_name.getText().trim() + "\" added successfully!" );
        XML.Save(Config.path, Config.network, root);
        panel.fillList(panel.onAir,false);
        close();
    }
    public void setFocus(){
        txt_name.requestFocus();
    }
    private String transformNodeName() {
        return txt_name.getText().replace(" ", "_");
    }

    private void lockANDnotify(boolean activate, String error) {
        lbl_Error.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/forbidden.png")));
        lbl_Error.setVisible(activate);
        lbl_Error.setText(error);
        btn_Add.setEnabled(!activate);
    }

    private boolean checkName() {
        char c[] = txt_name.getText().toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (!Character.isDigit(c[i]) && !Character.isLetter(c[i]) && c[i] != ' ') {
                return false;
            }
        }
        return true;
    }

    private void close() {
        GUI.loadPlugin(panel);
        GUI.updateUI();
        panel.tb.setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JLabel lbl_Error;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JTextField txt_name;
    // End of variables declaration//GEN-END:variables
}
