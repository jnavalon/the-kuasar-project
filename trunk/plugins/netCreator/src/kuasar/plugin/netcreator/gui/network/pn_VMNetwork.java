/*
 *  Copyright (C) 2011 jnavalon
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
 * pn_NetworkSettings.java
 *
 * Created on 02/03/2011, 19:25:34
 */

package kuasar.plugin.netcreator.gui.network;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JPanel;
import kuasar.plugin.netcreator.Config;
import kuasar.plugin.netcreator.utils.IP;
import kuasar.plugin.utils.XML;
import kuasar.plugin.utils.pn_Info;
import org.jdom.Element;

/**
 *
 * @author jnavalon
 */
public class pn_VMNetwork extends kuasar.plugin.classMod.AbstractPanel {

    private Element node;
    private String nodepath;

    /** Creates new panel to setup network
     * @param nodepath 
     */

    public pn_VMNetwork(String nodepath) {
        this.nodepath = nodepath;
        this.node = XML.getElementOnPath(nodepath, Config.rootNetwork);
        initComponents();
        lbl_Title.setText(" VM Network Editor < " + node.getAttributeValue("name") + " >");
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

        pn_Container = new javax.swing.JPanel();
        lbl_Title = new javax.swing.JLabel();
        tbt_Manual = new javax.swing.JToggleButton();
        tbt_dhcp = new javax.swing.JToggleButton();
        pn_IP = new javax.swing.JPanel();
        lbl_IP = new javax.swing.JLabel();
        txt_IP = new javax.swing.JTextField();
        lbl_Mask = new javax.swing.JLabel();
        txt_Mask = new javax.swing.JTextField();
        lbl_Gateway = new javax.swing.JLabel();
        txt_Gateway = new javax.swing.JTextField();
        lbl_DNS = new javax.swing.JLabel();
        txt_DNS = new javax.swing.JTextField();
        btn_Accept = new javax.swing.JButton();

        setOpaque(false);

        pn_Container.setOpaque(false);

        lbl_Title.setFont(new java.awt.Font("Dialog", 1, 18));
        lbl_Title.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/IPVM.png"))); // NOI18N
        lbl_Title.setText("<Node Name>");

        tbt_Manual.setSelected(true);
        tbt_Manual.setText("Manual");
        tbt_Manual.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tbt_ManualStateChanged(evt);
            }
        });

        tbt_dhcp.setText("DHCP");
        tbt_dhcp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_dhcpActionPerformed(evt);
            }
        });

        pn_IP.setOpaque(false);

        lbl_IP.setForeground(new java.awt.Color(204, 204, 204));
        lbl_IP.setText("IP:");

        txt_IP.setBackground(new Color(0,0,0,0));
        txt_IP.setFont(new java.awt.Font("Dialog", 1, 14));
        txt_IP.setForeground(new java.awt.Color(204, 204, 204));
        txt_IP.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_IP.setOpaque(false);

        lbl_Mask.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Mask.setText("Mask:");

        txt_Mask.setBackground(new Color(0,0,0,0));
        txt_Mask.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_Mask.setForeground(new java.awt.Color(204, 204, 204));
        txt_Mask.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_Mask.setOpaque(false);

        lbl_Gateway.setForeground(new java.awt.Color(204, 204, 204));
        lbl_Gateway.setText("Gateway:");

        txt_Gateway.setBackground(new Color(0,0,0,0));
        txt_Gateway.setFont(new java.awt.Font("Dialog", 1, 14));
        txt_Gateway.setForeground(new java.awt.Color(204, 204, 204));
        txt_Gateway.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_Gateway.setOpaque(false);

        lbl_DNS.setForeground(new java.awt.Color(204, 204, 204));
        lbl_DNS.setText("DNS:");

        txt_DNS.setBackground(new Color(0,0,0,0));
        txt_DNS.setFont(new java.awt.Font("Dialog", 1, 14));
        txt_DNS.setForeground(new java.awt.Color(204, 204, 204));
        txt_DNS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txt_DNS.setOpaque(false);

        javax.swing.GroupLayout pn_IPLayout = new javax.swing.GroupLayout(pn_IP);
        pn_IP.setLayout(pn_IPLayout);
        pn_IPLayout.setHorizontalGroup(
            pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
            .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_IPLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_Gateway)
                        .addComponent(lbl_Mask)
                        .addComponent(lbl_IP)
                        .addComponent(lbl_DNS))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_Mask, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_Gateway, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addComponent(txt_DNS, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addComponent(txt_IP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        pn_IPLayout.setVerticalGroup(
            pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
            .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_IPLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_IP)
                        .addComponent(txt_IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_Mask)
                        .addComponent(txt_Mask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_Gateway)
                        .addComponent(txt_Gateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(pn_IPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_DNS)
                        .addComponent(txt_DNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(53, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pn_ContainerLayout = new javax.swing.GroupLayout(pn_Container);
        pn_Container.setLayout(pn_ContainerLayout);
        pn_ContainerLayout.setHorizontalGroup(
            pn_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_Title, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_ContainerLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pn_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pn_IP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pn_ContainerLayout.createSequentialGroup()
                                .addComponent(tbt_Manual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tbt_dhcp)))))
                .addContainerGap())
        );
        pn_ContainerLayout.setVerticalGroup(
            pn_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbt_Manual)
                    .addComponent(tbt_dhcp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_Accept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/save.png"))); // NOI18N
        btn_Accept.setText("Save");
        btn_Accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AcceptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_Accept)
                        .addContainerGap())
                    .addComponent(pn_Container, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pn_Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Accept)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbt_ManualStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tbt_ManualStateChanged
        tbt_dhcp.setSelected(!tbt_Manual.isSelected());
        pn_IP.setVisible(tbt_Manual.isSelected());
    }//GEN-LAST:event_tbt_ManualStateChanged

    private void tbt_dhcpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_dhcpActionPerformed
        tbt_Manual.setSelected(!tbt_dhcp.isSelected());
    }//GEN-LAST:event_tbt_dhcpActionPerformed

    private void btn_AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AcceptActionPerformed
        save();
    }//GEN-LAST:event_btn_AcceptActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Accept;
    private javax.swing.JLabel lbl_DNS;
    private javax.swing.JLabel lbl_Gateway;
    private javax.swing.JLabel lbl_IP;
    private javax.swing.JLabel lbl_Mask;
    private javax.swing.JLabel lbl_Title;
    private javax.swing.JPanel pn_Container;
    private javax.swing.JPanel pn_IP;
    private javax.swing.JToggleButton tbt_Manual;
    private javax.swing.JToggleButton tbt_dhcp;
    private javax.swing.JTextField txt_DNS;
    private javax.swing.JTextField txt_Gateway;
    private javax.swing.JTextField txt_IP;
    private javax.swing.JTextField txt_Mask;
    // End of variables declaration//GEN-END:variables

    private void loadData(){
        String ipv4 = node.getAttributeValue("ipv4");
        String ipv6 = node.getAttributeValue("ipv6");

        if(ipv4==null && ipv6 == null) {
            tbt_Manual.setSelected(false);
            return;
        }
        short value4 = rateIP(ipv4);
        short value6 = rateIP(ipv6);
        if(value4 < 0 || value6 < 0){
            pn_Info.Load((JPanel) this.getParent(), this, "Error", "We Sorry but Network file is corrupted!<p> Please open configuration file "
                    + "and check if VM IP was correctly set up, otherwise delete IP configuration and try again.", pn_Info.ICON_ERROR);
            return;
        }
        if(value4>value6){
            if(value4==2){
                txt_IP.setText(ipv4);
                if(node.getAttributeValue("mask") != null)
                    txt_Mask.setText(Integer.toString(IP.mask2Digit(node.getAttributeValue("mask"))));
            }else{
                tbt_Manual.setSelected(false);
                return;
            }
        }else{
            if(value6==2){
                txt_IP.setText(ipv6);
                if(node.getAttributeValue("prefix") != null)
                    txt_Mask.setText(Integer.toString(IP.mask2Digit(node.getAttributeValue("prefix"))));
            }else{
                tbt_Manual.setSelected(false);
                return;
            }
        }
        if(node.getAttributeValue("gw") != null)
                    txt_Gateway.setText(node.getAttributeValue("gw"));
        if(node.getAttributeValue("dns") != null)
                    txt_DNS.setText(node.getAttributeValue("dns"));
    }

    private short rateIP(String ip){
        if(ip == null) return 0;
        if(ip.trim().isEmpty()){
            return 0;
        }
        try {
            InetAddress ipcheck = InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            return -1;
        }
        if(ip.trim().equals("0.0.0.0") || ip.trim().equals("0::0")){
            return 1;
        }
        return 2;
    }

    private void save(){
        if(!checkNet()) return;
        setAttributes();
        String path = nodepath.substring(0, nodepath.lastIndexOf("."));
        if (path.isEmpty()) {
             path = ".";
        }
        String nameNode = nodepath.substring(nodepath.lastIndexOf(".") + 1);
        XML.RemoveElement(path, Config.rootNetwork, nameNode);
        XML.AddElement(path, Config.rootNetwork, node);
        XML.Save(Config.VMdir, Config.VMdata, Config.rootNetwork);
        kuasar.plugin.Intercom.GUI.launchInfo(node.getAttributeValue("name") + " was updated successfully!");
    }

    private void setAttributes(){
        InetAddress ip;
        try {
            ip=InetAddress.getByName(txt_IP.getText().trim());
        } catch (UnknownHostException ex) {
            System.err.println("Uncatched error! IP pass all checks but continues.");
            return;
        }
        if(!tbt_dhcp.isSelected()){
            node.setAttribute("ipv4", ip.getAddress().length==4 ? ip.getHostAddress() : "");
            node.setAttribute("ipv6", ip.getAddress().length>4 ? ip.getHostAddress() : "");
            node.setAttribute("mask", ip.getAddress().length==4 ? IP.digit2Mask(txt_Mask.getText().trim(),4) : "");
            node.setAttribute("prefix", ip.getAddress().length>4 ?  IP.digit2Mask(txt_Mask.getText().trim(),6): "");
            node.setAttribute("gw", txt_Gateway.getText().trim().isEmpty() ? "" : txt_Gateway.getText().trim());
            node.setAttribute("dns", txt_DNS.getText().trim().isEmpty() ? "" : txt_DNS.getText().trim());
        }else{
            node.setAttribute("ipv4", "0.0.0.0");
            node.setAttribute("ipv6", "0::0");
            node.setAttribute("mask", "0");
            node.setAttribute("prefix", "0");
            node.setAttribute("gw", "");
            node.setAttribute("dns", "");
        }
    }

    private boolean checkNet(){
        if(this.tbt_dhcp.isSelected()) return true;
        InetAddress ip;
        try {
            ip = InetAddress.getByName(txt_IP.getText().trim());
        } catch (UnknownHostException ex) {
            pn_Info.Load((JPanel) this.getParent(), this, "Error", "Incorrect IP.", pn_Info.ICON_ERROR);
            return false;
        }
       
        if(ip.getAddress().length>4 ? !IP.checkIP6Mask(txt_Mask.getText()) : !IP.checkIP4Mask(txt_Mask.getText())){
            pn_Info.Load((JPanel) this.getParent(), this, "Error", "Incorrect Mask.", pn_Info.ICON_ERROR);
            return false;
        }

        if(!txt_Gateway.getText().trim().isEmpty()){
            try {
                InetAddress.getByName(txt_Gateway.getText().trim());
            } catch (UnknownHostException ex) {
                pn_Info.Load((JPanel) this.getParent(), this, "Error", "Incorrect Gateway.", pn_Info.ICON_ERROR);
                return false;
            }
        }

        if(!txt_DNS.getText().trim().isEmpty()){
            for(String dns : txt_DNS.getText().split(" ")){
                try {
                    InetAddress.getByName(dns.trim());
                } catch (UnknownHostException ex) {
                    pn_Info.Load((JPanel) this.getParent(), this, "Error", "Incorrect Gateway.", pn_Info.ICON_ERROR);
                    return false;
                }
            }
        }
        
        return true;
    }
}
