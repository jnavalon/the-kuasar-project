/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * dg_Password.java
 *
 * Created on 05/06/2011, 02:15:13
 */
package kuasar.plugin.servermanager.network;

import java.awt.AWTError;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class dg_Password extends javax.swing.JDialog {
    private char[] password=null;
    private Toolkit tk=null;

    public dg_Password(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(Color.black);
        this.getRootPane().setOpaque(false);
        lbl_capson.setVisible(false);
        try{
            tk = Toolkit.getDefaultToolkit();
        }catch(AWTError ex){}
        if(tk != null){
            pwd_Password.addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent ke){
                    if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                        password = pwd_Password.getPassword();
                        close();
                    }
                    try{
                        lbl_capson.setVisible(tk.getLockingKeyState(KeyEvent.VK_CAPS_LOCK));
                    }catch(Exception ex){}
                }
            });
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

        pn_Square_Container = new kuasar.plugin.classMod.Panel_Trans_Square();
        pn_Round_Container = new kuasar.plugin.classMod.Panel_Opaque();
        pwd_Password = new javax.swing.JPasswordField();
        lbl_capson = new javax.swing.JLabel();
        lbl_title = new javax.swing.JLabel();
        bt_Cancel = new javax.swing.JButton();
        tgb_top = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password Request");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);
        setUndecorated(true);

        pn_Square_Container.setBackground(new java.awt.Color(0, 0, 0));
        pn_Square_Container.setOpaque(false);

        pn_Round_Container.setOpaque(false);

        pwd_Password.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        pwd_Password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pwd_Password.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        lbl_capson.setForeground(new java.awt.Color(255, 204, 0));
        lbl_capson.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/Attention.png"))); // NOI18N
        lbl_capson.setText("Notice: Caps lock is activate!");

        javax.swing.GroupLayout pn_Round_ContainerLayout = new javax.swing.GroupLayout(pn_Round_Container);
        pn_Round_Container.setLayout(pn_Round_ContainerLayout);
        pn_Round_ContainerLayout.setHorizontalGroup(
            pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_Round_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_capson, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addComponent(pwd_Password, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_Round_ContainerLayout.setVerticalGroup(
            pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_Round_ContainerLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(pwd_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(lbl_capson)
                .addContainerGap())
        );

        lbl_title.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbl_title.setForeground(new java.awt.Color(204, 204, 204));
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/passwd.png"))); // NOI18N
        lbl_title.setText("Insert password");

        javax.swing.GroupLayout pn_Square_ContainerLayout = new javax.swing.GroupLayout(pn_Square_Container);
        pn_Square_Container.setLayout(pn_Square_ContainerLayout);
        pn_Square_ContainerLayout.setHorizontalGroup(
            pn_Square_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_Square_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_Square_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pn_Round_Container, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_title, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_Square_ContainerLayout.setVerticalGroup(
            pn_Square_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_Square_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_Round_Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        bt_Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/exit.png"))); // NOI18N
        bt_Cancel.setBorderPainted(false);
        bt_Cancel.setContentAreaFilled(false);
        bt_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_CancelActionPerformed(evt);
            }
        });

        tgb_top.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/go-down.png"))); // NOI18N
        tgb_top.setSelected(true);
        tgb_top.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tgb_top.setBorderPainted(false);
        tgb_top.setContentAreaFilled(false);
        tgb_top.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/go-top.png"))); // NOI18N
        tgb_top.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tgb_topStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_Square_Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(417, Short.MAX_VALUE)
                .addComponent(tgb_top)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pn_Square_Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tgb_top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CancelActionPerformed
        close();
    }//GEN-LAST:event_bt_CancelActionPerformed

    private void tgb_topStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tgb_topStateChanged
        this.setAlwaysOnTop(tgb_top.isSelected());
    }//GEN-LAST:event_tgb_topStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Cancel;
    private javax.swing.JLabel lbl_capson;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JPanel pn_Round_Container;
    private javax.swing.JPanel pn_Square_Container;
    private javax.swing.JPasswordField pwd_Password;
    private javax.swing.JToggleButton tgb_top;
    // End of variables declaration//GEN-END:variables
    public void setHeader(String title){
        lbl_title.setText(title);
    }
    public char[] getPassword(){
        return password;
    }
    private void close(){
        this.dispose();
    }
}
