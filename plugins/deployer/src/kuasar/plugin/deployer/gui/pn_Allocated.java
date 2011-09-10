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
 * pn_awards.java
 *
 * Created on 26/08/2011, 23:29:36
 */
package kuasar.plugin.deployer.gui;

import java.awt.print.PrinterException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.deployer.gui.classmod.I_Panels;
import kuasar.plugin.utils.pn_Info;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_Allocated extends kuasar.plugin.classMod.AbstractPanel implements I_Panels {

    private Element vms;
    private DefaultTableModel model = new DefaultTableModel();
    private JPanel previous, first;
    /** Creates new form pn_awards */
    public pn_Allocated(Element vms, JPanel previous, JPanel first) {
        this.vms = vms;
        this.previous=previous;
        this.first = first;
        initComponents();
        loadData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_Title = new javax.swing.JLabel();
        pn_Data = new javax.swing.JPanel();
        spn_Data = new javax.swing.JScrollPane();
        tbl_Data = new javax.swing.JTable();
        btn_Next = new javax.swing.JButton();
        btn_Previous = new javax.swing.JButton();
        btn_Print = new javax.swing.JButton();
        btn_Abort = new javax.swing.JButton();

        setOpaque(false);

        lbl_Title.setFont(new java.awt.Font("Dialog", 1, 28));
        lbl_Title.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/switch48.png"))); // NOI18N
        lbl_Title.setText("Allocated VMs");

        pn_Data.setOpaque(false);

        tbl_Data.setModel(model);
        spn_Data.setViewportView(tbl_Data);

        javax.swing.GroupLayout pn_DataLayout = new javax.swing.GroupLayout(pn_Data);
        pn_Data.setLayout(pn_DataLayout);
        pn_DataLayout.setHorizontalGroup(
            pn_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spn_Data, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_DataLayout.setVerticalGroup(
            pn_DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spn_Data, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_Next.setBackground(new java.awt.Color(0, 0, 0));
        btn_Next.setForeground(new java.awt.Color(204, 204, 204));
        btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/gnext22.png"))); // NOI18N
        btn_Next.setText("Apply");

        btn_Previous.setBackground(new java.awt.Color(0, 0, 0));
        btn_Previous.setForeground(new java.awt.Color(204, 204, 204));
        btn_Previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/previous22.png"))); // NOI18N
        btn_Previous.setText("Previous");
        btn_Previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PreviousActionPerformed(evt);
            }
        });

        btn_Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/printer22.png"))); // NOI18N
        btn_Print.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_Print.setBorderPainted(false);
        btn_Print.setContentAreaFilled(false);
        btn_Print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrintActionPerformed(evt);
            }
        });

        btn_Abort.setBackground(new java.awt.Color(0, 0, 0));
        btn_Abort.setForeground(new java.awt.Color(204, 204, 204));
        btn_Abort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/deployer/icons/abort22.png"))); // NOI18N
        btn_Abort.setText("Abort");
        btn_Abort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AbortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pn_Data, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Title, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btn_Print)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                        .addComponent(btn_Abort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Previous)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Next)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pn_Data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Next)
                        .addComponent(btn_Previous)
                        .addComponent(btn_Abort))
                    .addComponent(btn_Print))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void btn_PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrintActionPerformed
        try {
            tbl_Data.print();
        } catch (PrinterException ex) {
            pn_Info.Load((JPanel)this.getParent(), this, "Error printing document", "Message: " + ex.getMessage(), pn_Info.ICON_ERROR);
        }
}//GEN-LAST:event_btn_PrintActionPerformed

private void btn_PreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PreviousActionPerformed
    goPrevious();
}//GEN-LAST:event_btn_PreviousActionPerformed

private void btn_AbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AbortActionPerformed
    GUI.loadPlugin(first);
    GUI.updateUI();
}//GEN-LAST:event_btn_AbortActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Abort;
    private javax.swing.JButton btn_Next;
    private javax.swing.JButton btn_Previous;
    private javax.swing.JButton btn_Print;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel pn_Data;
    private javax.swing.JScrollPane spn_Data;
    private javax.swing.JTable tbl_Data;
    // End of variables declaration//GEN-END:variables

    @Override
    public void goNext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goPrevious() {
        GUI.loadPlugin(previous);
        GUI.updateUI();
    }

    private void loadData() {
        recursiveScan(vms,"");
    }

    private void recursiveScan(Element root, String path) {
        List<Element> children = root.getChildren();
        initTable();
        for(Element child : children){
            if(!child.getAttributeValue("type").equals("vm")){
                recursiveScan(child,path+"/"+child.getAttributeValue("name"));
            }
            else{
                extractData(child, path+"/"+child.getAttributeValue("name"));
            }
        }
        
    }

    private void extractData(Element child, String path) {
        model.addRow(new String[]{path, child.getAttributeValue("server")});
    }
    private void initTable(){
        model = new DefaultTableModel();
        model.addColumn("Virtual Machine");
        model.addColumn("Server");
        tbl_Data.setModel(model);
        tbl_Data.updateUI();
    }
}
