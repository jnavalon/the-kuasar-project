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
 * ScreenShootTemplate.java
 *
 * Created on 29/07/2010, 12:21:25
 */

package kuasar.util.config;

import javax.swing.Icon;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class ScreenShootTemplate extends javax.swing.JPanel {

    /** Creates new form ScreenShootTemplate */
    public ScreenShootTemplate() {
        initComponents();
    }
    public Icon getIcon(){
        return lbl_ScreenShoot.getIcon();
    }
    public void setIcon(Icon icon){
       lbl_ScreenShoot.setIcon(icon);
    }
    public String getText(){
        return lbl_Main.getText();
    }
    public void setText(String text){
        lbl_Main.setText(text);
    }
    public int getMaxIconSize(){
        if(lbl_ScreenShoot.getPreferredSize().width >= lbl_ScreenShoot.getPreferredSize().height)
            return lbl_ScreenShoot.getPreferredSize().height;
        else
            return lbl_ScreenShoot.getPreferredSize().width;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_Main = new javax.swing.JLabel();
        lbl_ScreenShoot = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        lbl_Main.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lbl_Main.setPreferredSize(new java.awt.Dimension(300, 76));

        lbl_ScreenShoot.setPreferredSize(new java.awt.Dimension(107, 76));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbl_Main, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ScreenShoot, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Main, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
            .addComponent(lbl_ScreenShoot, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_Main;
    private javax.swing.JLabel lbl_ScreenShoot;
    // End of variables declaration//GEN-END:variables

}