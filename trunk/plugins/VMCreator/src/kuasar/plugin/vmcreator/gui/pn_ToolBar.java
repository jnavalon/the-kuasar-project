/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pn_ToolBar.java
 *
 * Created on 05/08/2010, 16:30:39
 */
package kuasar.plugin.vmcreator.gui;

import java.util.EmptyStackException;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.vmcreator.gui.tooltasks.AddHost.pn_TB_AddHost;
import kuasar.plugin.vmcreator.gui.tooltasks.pn_AddGroup;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_ToolBar extends javax.swing.JPanel {

    /** Creates new form pn_ToolBar */
    pn_Main panel = null;
    boolean favorites=false;

    public pn_ToolBar(pn_Main panel) {
        initComponents();
        this.panel = panel;
        panel.tb= (pn_ToolBar) this;
        lbl_back.setEnabled(false);
        lbl_up.setEnabled(false);
        lbl_next.setEnabled(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tob_Dialog = new javax.swing.JToolBar();
        lbl_Edit = new javax.swing.JLabel();
        lbl_DialogClose = new javax.swing.JLabel();
        tob_Add = new javax.swing.JToolBar();
        lbl_addGroup = new javax.swing.JLabel();
        lbl_Favorite = new javax.swing.JLabel();
        lbl_addHost = new javax.swing.JLabel();
        tob_Nav = new javax.swing.JToolBar();
        lbl_back = new javax.swing.JLabel();
        lbl_up = new javax.swing.JLabel();
        lbl_next = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(220, 60));

        tob_Dialog.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tob_Dialog.setFloatable(false);
        tob_Dialog.setBorderPainted(false);
        tob_Dialog.setOpaque(false);

        lbl_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/edit.png"))); // NOI18N
        lbl_Edit.setToolTipText("Edit");
        lbl_Edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_EditMouseReleased(evt);
            }
        });
        tob_Dialog.add(lbl_Edit);

        lbl_DialogClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/dialog-close.png"))); // NOI18N
        lbl_DialogClose.setToolTipText("Exit");
        lbl_DialogClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_DialogClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_DialogCloseMouseReleased(evt);
            }
        });
        tob_Dialog.add(lbl_DialogClose);

        tob_Add.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tob_Add.setFloatable(false);
        tob_Add.setOpaque(false);

        lbl_addGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/Group.png"))); // NOI18N
        lbl_addGroup.setToolTipText("Add Group");
        lbl_addGroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_addGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_addGroupMouseReleased(evt);
            }
        });
        tob_Add.add(lbl_addGroup);

        lbl_Favorite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/favorites_deact.png"))); // NOI18N
        lbl_Favorite.setToolTipText("Show Favorites");
        lbl_Favorite.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Favorite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_FavoriteMouseReleased(evt);
            }
        });
        tob_Add.add(lbl_Favorite);

        lbl_addHost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/host.png"))); // NOI18N
        lbl_addHost.setToolTipText("Add Virtual Host");
        lbl_addHost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_addHost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_addHostMouseReleased(evt);
            }
        });
        tob_Add.add(lbl_addHost);

        tob_Nav.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tob_Nav.setFloatable(false);
        tob_Nav.setBorderPainted(false);
        tob_Nav.setOpaque(false);

        lbl_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/previous.png"))); // NOI18N
        lbl_back.setToolTipText("Back");
        lbl_back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_back.setEnabled(false);
        lbl_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_backMouseReleased(evt);
            }
        });
        tob_Nav.add(lbl_back);

        lbl_up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/up.png"))); // NOI18N
        lbl_up.setToolTipText("Up");
        lbl_up.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_up.setEnabled(false);
        lbl_up.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_upMouseReleased(evt);
            }
        });
        tob_Nav.add(lbl_up);

        lbl_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/next.png"))); // NOI18N
        lbl_next.setToolTipText("Forward");
        lbl_next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_next.setEnabled(false);
        lbl_next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_nextMouseReleased(evt);
            }
        });
        tob_Nav.add(lbl_next);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tob_Nav, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tob_Add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tob_Dialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tob_Dialog, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(tob_Add, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(tob_Nav, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        tob_Dialog.setVisible(false);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_addGroupMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addGroupMouseReleased
        pn_AddGroup ag = new pn_AddGroup(panel);
        GUI.loadPlugin(ag);
        ag.setFocus();
        GUI.updateUI();
    }//GEN-LAST:event_lbl_addGroupMouseReleased

    private void lbl_backMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_backMouseReleased
         String netpath = null;
        try {
            netpath = panel.back.pop();
        } catch (EmptyStackException es) {
            return;
        }
        panel.next.push(panel.onAir);
        if (!panel.fillList(netpath,false)) {
            GUI.launchInfo("Netpath not found!");
            panel.onAir = panel.next.pop();
        }
        panel.onAir=netpath;
        checkAddrAcc();
    }//GEN-LAST:event_lbl_backMouseReleased

    private void lbl_nextMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_nextMouseReleased
         String netpath = null;
        try {
            netpath = panel.next.pop();
        } catch (EmptyStackException es) {
            return;
        }
        panel.back.push(panel.onAir);
        if (!panel.fillList(netpath,false)) {
            GUI.launchInfo("Netpath not found!");
            panel.onAir = panel.back.pop();
        }
        panel.onAir=netpath;
        checkAddrAcc();
    }//GEN-LAST:event_lbl_nextMouseReleased

    private void lbl_upMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_upMouseReleased
        if(!lbl_up.isEnabled())
            return;
        String up = panel.onAir;
        up = up.substring(0, up.lastIndexOf('.'));
        if(up.length()==0) up= ".";
        panel.back.push(panel.onAir);
        panel.fillList(up,true);
    }//GEN-LAST:event_lbl_upMouseReleased

    private void lbl_addHostMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_addHostMouseReleased
        GUI.loadToolBar(new pn_TB_AddHost(panel));
    }//GEN-LAST:event_lbl_addHostMouseReleased

    private void lbl_DialogCloseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DialogCloseMouseReleased
        panel.restoreList();
    }//GEN-LAST:event_lbl_DialogCloseMouseReleased

    private void lbl_EditMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_EditMouseReleased
        panel.editVM();
    }//GEN-LAST:event_lbl_EditMouseReleased

    private void lbl_FavoriteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_FavoriteMouseReleased
        if(favorites){
            lbl_Favorite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/favorites_deact.png")));
            lbl_Favorite.setToolTipText("Show Favorites");
            panel.hideFavorites();
        }else{
            lbl_Favorite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/vmcreator/icons/favorites.png")));
            lbl_Favorite.setToolTipText("Hide Favorites");
            panel.showFavorites();
        }
        favorites = !favorites;
    }//GEN-LAST:event_lbl_FavoriteMouseReleased

    protected void activateDialog(){
        tob_Nav.setVisible(false);
        tob_Add.setVisible(false);
        tob_Dialog.setVisible(true);
        
    }
    protected void deactivateDialog(){
        tob_Nav.setVisible(true);
        tob_Add.setVisible(true);
        tob_Dialog.setVisible(false);
    }

    protected void checkAddrAcc() {

        if (panel.back.size() == 0) {
            lbl_back.setEnabled(false);
        } else {
            lbl_back.setEnabled(true);
        }
        if (panel.next.size() == 0) {
            lbl_next.setEnabled(false);
        } else {
            lbl_next.setEnabled(true);
        }
         if (panel.onAir.equals(".")) {
            lbl_up.setEnabled(false);
        } else {
            lbl_up.setEnabled(true);
        }
        GUI.updateUI();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_DialogClose;
    private javax.swing.JLabel lbl_Edit;
    private javax.swing.JLabel lbl_Favorite;
    private javax.swing.JLabel lbl_addGroup;
    private javax.swing.JLabel lbl_addHost;
    private javax.swing.JLabel lbl_back;
    private javax.swing.JLabel lbl_next;
    private javax.swing.JLabel lbl_up;
    private javax.swing.JToolBar tob_Add;
    private javax.swing.JToolBar tob_Dialog;
    private javax.swing.JToolBar tob_Nav;
    // End of variables declaration//GEN-END:variables
}
