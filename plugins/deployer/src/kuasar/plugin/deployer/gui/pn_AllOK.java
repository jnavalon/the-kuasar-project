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
 * pn_AllOK.java
 *
 * Created on 17/09/2011, 00:15:22
 */
package kuasar.plugin.deployer.gui;

import javax.swing.JPanel;
import kuasar.plugin.Intercom.GUI;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_AllOK extends kuasar.plugin.classMod.AbstractPanel {

    private JPanel first;
    /** Creates new form pn_AllOK */
    public pn_AllOK(JPanel first) {
        this.first = first;
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

        btn_Finish = new javax.swing.JButton();
        lbl_Info = new javax.swing.JLabel();

        setOpaque(false);

        btn_Finish.setBackground(new java.awt.Color(0, 0, 0));
        btn_Finish.setForeground(new java.awt.Color(204, 204, 204));
        btn_Finish.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/ok22.png"))); // NOI18N
        btn_Finish.setText("End");
        btn_Finish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FinishActionPerformed(evt);
            }
        });

        lbl_Info.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        lbl_Info.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Info.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/allRight128.png"))); // NOI18N
        lbl_Info.setText("All was well!");
        lbl_Info.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl_Info.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Info, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .addComponent(btn_Finish))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Info, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Finish)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_FinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FinishActionPerformed
        GUI.loadPlugin(first);
        GUI.updateUI();
    }//GEN-LAST:event_btn_FinishActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Finish;
    private javax.swing.JLabel lbl_Info;
    // End of variables declaration//GEN-END:variables
}
