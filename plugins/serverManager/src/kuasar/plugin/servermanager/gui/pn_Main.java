/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pn_Main.java
 *
 * Created on 01/06/2011, 15:41:22
 */
package kuasar.plugin.servermanager.gui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.servermanager.Config;
import kuasar.plugin.servermanager.gui.actions.pn_AddGroup;
import kuasar.plugin.servermanager.gui.actions.pn_AddServer;
import kuasar.plugin.servermanager.gui.actions.pn_Wizard;
import kuasar.plugin.servermanager.network.utils.Connection;
import kuasar.plugin.utils.XML;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class pn_Main extends kuasar.plugin.classMod.AbstractPanel {

    private Element root;
    private boolean checkServers = false;
    private String curDir=null;

    /** Creates new form pn_Main */
    public pn_Main() {
        initComponents();
        scp_Servers.getViewport().setOpaque(false);
        lst_Servers.setBorder(BorderFactory.createEmptyBorder());
        checkPaths();
        root = XML.getRoot(Config.path, Config.fileServers);
        fillList();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scp_Servers = new javax.swing.JScrollPane();
        lst_Servers = new kuasar.plugin.servermanager.classmod.JListView();
        lbl_Info = new javax.swing.JLabel();

        setOpaque(false);

        scp_Servers.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scp_Servers.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scp_Servers.setOpaque(false);

        lst_Servers.setOpaque(false);
        lst_Servers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_ServersMouseClicked(evt);
            }
        });
        lst_Servers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lst_ServersKeyReleased(evt);
            }
        });
        scp_Servers.setViewportView(lst_Servers);

        lbl_Info.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scp_Servers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addComponent(lbl_Info, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scp_Servers, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_Info, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lst_ServersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lst_ServersKeyReleased
        if(evt.getKeyCode()== KeyEvent.VK_DELETE){
            delSelectedNode();            
        }
    }//GEN-LAST:event_lst_ServersKeyReleased

    private void lst_ServersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_ServersMouseClicked
        if(!evt.isControlDown()){
            String  nodename = (String) ((Object[])lst_Servers.getSelectedValue())[2];
            Element e = curDir == null?root.getChild(nodename):root.getChild(curDir).getChild(nodename);
            if(e.getAttributeValue("type").equals("group"))
                fillList(nodename);
        }
    }//GEN-LAST:event_lst_ServersMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_Info;
    private javax.swing.JList lst_Servers;
    private javax.swing.JScrollPane scp_Servers;
    // End of variables declaration//GEN-END:variables

    private void checkPaths() {
        String path = ((String) kuasar.plugin.Intercom.ODR.getValue("$PLUGINDIR")) + File.separator + kuasar.plugin.servermanager.Config.path;
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.err.println("Error: Servers won't be saved! I couldn't create " + kuasar.plugin.servermanager.Config.path + " directory");
            }
        }
    }

    protected void fillList() {
        fillList(null);
    }

    protected void fillList(String group) {
        if(root==null) return;
        curDir=group;
        lbl_Info.setText("");
        List<Element> children=(group==null?root.getChildren():root.getChild(group).getChildren());
        TreeMap<String, Object[]> tm = new TreeMap<String, Object[]>();
        DefaultListModel model = new DefaultListModel();
        lst_Servers.setModel(model);
        Object[] data = new Object[5];
        for (Element child : children) {
            if (child.getAttributeValue("type").equals("group")) {
                data[Config.ServerList.ICON] = new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/folder-servers.png"));
                data[Config.ServerList.NAME] = child.getAttributeValue("name");
                data[Config.ServerList.LABEL] = lbl_Info;
                data[Config.ServerList.XMLNAME] = child.getName();
                tm.put(child.getName(), data.clone());
            }
        }
        Iterator it = tm.keySet().iterator();
        while (it.hasNext()) {
            model.addElement(tm.get((String) it.next()));
        }
        tm.clear();
        for(Element child : children){
            if (child.getAttributeValue("type").equals("server")){
                if(!checkServers)
                    data[Config.ServerList.ICON] = new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/icon"));
                else
                    data[Config.ServerList.ICON] = new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/servermanager/icons/icon"));
                data[Config.ServerList.NAME] = child.getAttributeValue("name");
                data[Config.ServerList.LABEL] = lbl_Info;
                data[Config.ServerList.XMLNAME] = child.getName();
                tm.put(child.getName(), data.clone());
            }
        }
        it = tm.keySet().iterator();
        while (it.hasNext()) {
            model.addElement(tm.get((String) it.next()));
        }
        tm.clear();
        
    }
    
    public boolean existsNode(String name){
        String realname = XML.adaptName(name);
        Element node;
        if(curDir==null){
            node=root.getChild(realname);
        }else{
            node=root.getChild(curDir);
            if(node==null) return false;
            node=node.getChild(realname);
        }
        return !(node==null);
    }
    
    public boolean addServer(String hostname, String address, int port){
        List<String[]> attributes = new ArrayList<String[]>();
        attributes.add(new String[]{"type", "server"});
        attributes.add(new String[]{"name", hostname});
        attributes.add(new String[]{"address", address});
        attributes.add(new String[]{"port", String.valueOf(port)});
        if(XML.AddElement(curDir==null?root:root.getChild(curDir), XML.adaptName(hostname), attributes))
            kuasar.plugin.Intercom.GUI.launchInfo("Server \"" + hostname + "\" added successfully!" );
        else
            return false;
        XML.Save(Config.path, Config.fileServers, root);
        fillList(curDir);
        return true;
    }

    public boolean addGroup(String groupname) {
        List<String[]> attributes = new ArrayList<String[]>();
        attributes.add(new String[]{"type", "group"});
        attributes.add(new String[]{"name", groupname});
        String adaptedName = XML.adaptName(groupname);
        if(adaptedName==null)return false;
        if(XML.AddElement(root, adaptedName, attributes))
            kuasar.plugin.Intercom.GUI.launchInfo("Group \"" + groupname + "\" added successfully!" );
        else
            return false;
        XML.Save(Config.path, Config.fileServers, root);
        fillList(curDir);
        return true;
    }
    
    public boolean childExists(String name){
        return root.getChild(name)!=null;
    }
    
    public boolean delChild(String name){
        if(curDir==null){
            return root.removeChild(name);
        }else{
            if(root.getChild(curDir)==null) return false; 
            return root.getChild(curDir).removeChild(name);
        }
    }
    protected void goToRoot(){
        fillList(null);
    }
    protected void loadConfig(){
       GUI.loadPlugin(new pn_Config(this));
       GUI.invisibleToolBar();
    }
    protected void loadAddGroup(){
        GUI.loadPlugin(new pn_AddGroup(this));
        GUI.invisibleToolBar();
    }
     protected void loadAddServer(){
        GUI.loadPlugin(new pn_AddServer(this));
        GUI.invisibleToolBar();
    }
     protected void loadWizard(){
         GUI.loadPlugin(new pn_Wizard(this));
        GUI.invisibleToolBar();
     }

    protected void delSelectedNode() {
        boolean updateList = (lst_Servers.getSelectedIndices().length>0);
            for(Object data : lst_Servers.getSelectedValues()){
                Object[] dataa = (Object[]) data;
                String nodeName = (String) dataa[2];
                String address =root.getChild(nodeName).getAttributeValue("address");
                if(delChild(nodeName)){
                    GUI.launchInfo(nodeName + " was deleted successfully!");
                    Connection.delKSPassword(address);
                    Connection.delKeyServer(address); 
                }
            }
            if(updateList){
                fillList(curDir);
                XML.Save(Config.path, Config.fileServers, root);
            }
    }
}
