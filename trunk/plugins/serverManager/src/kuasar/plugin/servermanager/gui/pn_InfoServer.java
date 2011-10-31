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
 * pn_InfoServer.java
 *
 * Created on 20/09/2011, 22:13:16
 */
package kuasar.plugin.servermanager.gui;


import javax.swing.JPanel;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.servermanager.gui.infoserver.pn_Hvs;


/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_InfoServer extends kuasar.plugin.classMod.AbstractPanel {
    
    JPanel parent, container;
    /** Creates new form pn_InfoServer */
    public pn_InfoServer(JPanel parent, JPanel container) {
        this.parent = parent;
        this.container= container;
        initComponents();
    }
    
    public void showPanel(String address, String port){
        GUI.loadPlugin(this);
        GUI.updateUI();
        loadPanel(address,port);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_Data = new javax.swing.JPanel();
        btn_Back = new javax.swing.JButton();

        setOpaque(false);

        pn_Data.setOpaque(false);
        pn_Data.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pn_DataComponentResized(evt);
            }
        });

        javax.swing.GroupLayout pn_DataLayout = new javax.swing.GroupLayout(pn_Data);
        pn_Data.setLayout(pn_DataLayout);
        pn_DataLayout.setHorizontalGroup(
            pn_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 667, Short.MAX_VALUE)
        );
        pn_DataLayout.setVerticalGroup(
            pn_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );

        btn_Back.setBackground(new java.awt.Color(0, 0, 0));
        btn_Back.setForeground(new java.awt.Color(204, 204, 204));
        btn_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/previous.png"))); // NOI18N
        btn_Back.setText("Back");
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(566, Short.MAX_VALUE)
                .addComponent(btn_Back)
                .addContainerGap())
            .addComponent(pn_Data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pn_Data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Back)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        GUI.loadPlugin(parent);
        GUI.updateUI();
    }//GEN-LAST:event_btn_BackActionPerformed

    private void pn_DataComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pn_DataComponentResized
        pn_Data.getComponent(0).setBounds(0, 0, pn_Data.getWidth(), pn_Data.getHeight());
        pn_Data.updateUI();
    }//GEN-LAST:event_pn_DataComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Back;
    private javax.swing.JPanel pn_Data;
    // End of variables declaration//GEN-END:variables

   private void loadPanel(String address, String port) {
        pn_Hvs pn_hvs = new pn_Hvs(address, port);
        pn_Data.removeAll();
        pn_hvs.setBounds(0, 0, pn_Data.getWidth(), pn_Data.getHeight());
        pn_Data.add(pn_hvs);
        pn_Data.updateUI();
        pn_hvs.loadData();
    }
}