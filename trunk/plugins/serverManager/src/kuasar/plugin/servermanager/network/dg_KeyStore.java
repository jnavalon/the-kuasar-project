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

import java.awt.Color;
import javax.swing.JFileChooser;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class dg_KeyStore extends javax.swing.JDialog {
    private String keystore=null;
    private String address = null;

    public dg_KeyStore(java.awt.Frame parent, boolean modal, String address) {
        super(parent, modal);
        this.address = address;
        initComponents();
        this.getContentPane().setBackground(Color.black);
        this.getRootPane().setOpaque(false);
        lbl_error.setVisible(false);
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
        spn_File = new javax.swing.JScrollPane();
        txa_File = new javax.swing.JTextArea();
        lbl_Password = new javax.swing.JLabel();
        pwd_password = new javax.swing.JPasswordField();
        tb_save = new javax.swing.JToggleButton();
        lbl_error = new javax.swing.JLabel();
        tb_saveKey = new javax.swing.JToggleButton();
        lbl_title = new javax.swing.JLabel();
        bt_Cancel = new javax.swing.JButton();
        tgb_top = new javax.swing.JToggleButton();
        bt_Accept = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password Request");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);
        setUndecorated(true);

        pn_Square_Container.setBackground(new java.awt.Color(0, 0, 0));
        pn_Square_Container.setOpaque(false);

        pn_Round_Container.setOpaque(false);

        txa_File.setColumns(20);
        txa_File.setEditable(false);
        txa_File.setRows(5);
        txa_File.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txa_FileMouseClicked(evt);
            }
        });
        spn_File.setViewportView(txa_File);

        lbl_Password.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_Password.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Password.setText("Password:");

        pwd_password.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        pwd_password.setForeground(new java.awt.Color(0, 0, 0));
        pwd_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tb_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/document-nosave.png"))); // NOI18N
        tb_save.setSelected(true);
        tb_save.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tb_save.setBorderPainted(false);
        tb_save.setContentAreaFilled(false);
        tb_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tb_save.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/document-save.png"))); // NOI18N

        lbl_error.setForeground(new java.awt.Color(204, 0, 51));

        tb_saveKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/nosave_key.png"))); // NOI18N
        tb_saveKey.setSelected(true);
        tb_saveKey.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tb_saveKey.setBorderPainted(false);
        tb_saveKey.setContentAreaFilled(false);
        tb_saveKey.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tb_saveKey.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/save_key.png"))); // NOI18N

        javax.swing.GroupLayout pn_Round_ContainerLayout = new javax.swing.GroupLayout(pn_Round_Container);
        pn_Round_Container.setLayout(pn_Round_ContainerLayout);
        pn_Round_ContainerLayout.setHorizontalGroup(
            pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_Round_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_Round_ContainerLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbl_Password)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pwd_password, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                    .addComponent(spn_File, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_Round_ContainerLayout.createSequentialGroup()
                        .addComponent(lbl_error, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb_saveKey)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tb_save)))
                .addContainerGap())
        );
        pn_Round_ContainerLayout.setVerticalGroup(
            pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_Round_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spn_File, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pwd_password, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(lbl_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_Round_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tb_save)
                    .addComponent(tb_saveKey)
                    .addComponent(lbl_error, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lbl_title.setFont(new java.awt.Font("Dialog", 1, 24));
        lbl_title.setForeground(new java.awt.Color(204, 204, 204));
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/certificate.png"))); // NOI18N
        lbl_title.setText("Insert KeyStore");

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
                .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_Round_Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        bt_Accept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/dialog-ok-apply.png"))); // NOI18N
        bt_Accept.setBorderPainted(false);
        bt_Accept.setContentAreaFilled(false);
        bt_Accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AcceptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(387, Short.MAX_VALUE)
                .addComponent(tgb_top)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_Accept, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(pn_Square_Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_Square_Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bt_Cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Accept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tgb_top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CancelActionPerformed
        keystore=null;
        close();
    }//GEN-LAST:event_bt_CancelActionPerformed

    private void tgb_topStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tgb_topStateChanged
        this.setAlwaysOnTop(tgb_top.isSelected());
    }//GEN-LAST:event_tgb_topStateChanged

    private void txa_FileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txa_FileMouseClicked
       lbl_error.setVisible(false);
       txa_File.setText("");
       keystore=null;
       JFileChooser jfc = new JFileChooser();
       jfc.setDialogTitle("Select a keyStore file");
       jfc.setDialogType(JFileChooser.OPEN_DIALOG);
       jfc.setMultiSelectionEnabled(false);
       jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
       int status = jfc.showOpenDialog(this);
       if(status == JFileChooser.CANCEL_OPTION) return;
       if(status == JFileChooser.ERROR_OPTION){
           lbl_error.setText("Error loading the selected file");
           lbl_error.setVisible(true);
           return;
       }
       if(jfc.getSelectedFile()== null)return;
       if(!jfc.getSelectedFile().canRead()){
           lbl_error.setText("File can't be readed! Check permissions.");
           lbl_error.setVisible(true);
           return;
       }
       txa_File.setText(jfc.getSelectedFile().getAbsolutePath());
       keystore=jfc.getSelectedFile().getAbsolutePath();
       
    }//GEN-LAST:event_txa_FileMouseClicked

    private void bt_AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AcceptActionPerformed
        if(tb_save.isSelected())
            saveKeyStore();
        if(tb_saveKey.isSelected())
            savePassword();
        close();
    }//GEN-LAST:event_bt_AcceptActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Accept;
    private javax.swing.JButton bt_Cancel;
    private javax.swing.JLabel lbl_Password;
    private javax.swing.JLabel lbl_error;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JPanel pn_Round_Container;
    private javax.swing.JPanel pn_Square_Container;
    private javax.swing.JPasswordField pwd_password;
    private javax.swing.JScrollPane spn_File;
    private javax.swing.JToggleButton tb_save;
    private javax.swing.JToggleButton tb_saveKey;
    private javax.swing.JToggleButton tgb_top;
    private javax.swing.JTextArea txa_File;
    // End of variables declaration//GEN-END:variables
        
    private void saveKeyStore(){
        Utils.saveKeyServer(txa_File.getText(), address);
    }
    private void savePassword(){
        Utils.saveKSPassword(pwd_password.getPassword(), address);
    }
    public void setHeader(String title){
        lbl_title.setText(title);
    } 
    public char[] getPassword(){
        if(pwd_password.getPassword().length==0) return null;
        return pwd_password.getPassword();
    }
    public String getKeyStore(){
        return keystore;
    }
    private void close(){
        this.dispose();
    }
}