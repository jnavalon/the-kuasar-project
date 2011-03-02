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
 * pn_DirList.java
 *
 * Created on 13/02/2011, 19:40:46
 */

package kuasar.plugin.netcreator.gui.dirlist;

import javax.swing.ImageIcon;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_DirListSelected extends kuasar.plugin.classMod.AbstractPanel {

    /** Creates new form pn_DirList */
    public pn_DirListSelected(String text, boolean isFolder) {
        initComponents();
        lbl_Name.setText(text);
        lbl_Name.setIcon(isFolder ? new ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/folder.png")): new ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/computer.png")));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_Name = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(5, 40));

        lbl_Name.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        lbl_Name.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Name.setText("<Insert Text>");
        lbl_Name.setMinimumSize(new java.awt.Dimension(1, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Name, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Name, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_Name;
    // End of variables declaration//GEN-END:variables

}