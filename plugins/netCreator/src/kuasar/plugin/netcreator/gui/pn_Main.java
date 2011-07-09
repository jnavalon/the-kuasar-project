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
 * pn_Main.java
 *
 * Created on 07/02/2011, 17:24:45
 */
package kuasar.plugin.netcreator.gui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.netcreator.Config;
import kuasar.plugin.netcreator.gui.dirlist.Lst_Cell_Renderer;
import kuasar.plugin.netcreator.gui.network.pn_GroupNetwork;
import kuasar.plugin.netcreator.gui.network.pn_VMNetwork;
import kuasar.plugin.utils.XML;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class pn_Main extends kuasar.plugin.classMod.AbstractPanel {

    
    DefaultListModel model = new DefaultListModel();
    ArrayList<JButton> paths = new ArrayList<JButton>();
    String actdir = ".";
    String namePath = ".";

    /** Creates new form pn_Main */
    public pn_Main() {
        Config.NetworkFile  = (String) ODR.getValue("$PLUGINDIR") + File.separator + Config.VMdir + File.separator + Config.VMdata;
        Config.rootNetwork= XML.getRoot(new File(Config.NetworkFile));
        initComponents();
        scp_Dir.getViewport().setOpaque(false);
        lst_Dir.setCellRenderer(new Lst_Cell_Renderer());
        lst_Dir.setModel(model);
        loadData(actdir);
        spn_Panel.getViewport().setOpaque(false);
        lbl_Divider.setIcon(new ImageIcon(Config.skinpath + File.separator + "adjust"));
        lbl_Divider.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_Dir = new javax.swing.JPanel();
        btn_Next = new javax.swing.JButton();
        btn_Previous = new javax.swing.JButton();
        spn_Panel = new javax.swing.JScrollPane();
        pn_Bt_Dir = new javax.swing.JPanel();
        pn_Container = new javax.swing.JPanel();
        pn_Divider = new javax.swing.JPanel();
        lbl_Divider = new javax.swing.JLabel();
        spp_Panel = new javax.swing.JSplitPane();
        scp_Dir = new javax.swing.JScrollPane();
        lst_Dir = new javax.swing.JList();
        pn_Config = new javax.swing.JPanel();
        pn_Network = new javax.swing.JPanel();

        setOpaque(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        pn_Dir.setOpaque(false);

        btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/next.png"))); // NOI18N
        btn_Next.setContentAreaFilled(false);
        btn_Next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_NextMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_NextMouseEntered(evt);
            }
        });

        btn_Previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/previous.png"))); // NOI18N
        btn_Previous.setContentAreaFilled(false);
        btn_Previous.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Previous.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_PreviousMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_PreviousMouseEntered(evt);
            }
        });

        spn_Panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        spn_Panel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spn_Panel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spn_Panel.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        spn_Panel.setDoubleBuffered(true);
        spn_Panel.setOpaque(false);
        spn_Panel.setWheelScrollingEnabled(false);

        pn_Bt_Dir.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pn_Bt_Dir.setOpaque(false);

        javax.swing.GroupLayout pn_Bt_DirLayout = new javax.swing.GroupLayout(pn_Bt_Dir);
        pn_Bt_Dir.setLayout(pn_Bt_DirLayout);
        pn_Bt_DirLayout.setHorizontalGroup(
            pn_Bt_DirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        pn_Bt_DirLayout.setVerticalGroup(
            pn_Bt_DirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );

        spn_Panel.setViewportView(pn_Bt_Dir);

        javax.swing.GroupLayout pn_DirLayout = new javax.swing.GroupLayout(pn_Dir);
        pn_Dir.setLayout(pn_DirLayout);
        pn_DirLayout.setHorizontalGroup(
            pn_DirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DirLayout.createSequentialGroup()
                .addComponent(btn_Previous, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spn_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_DirLayout.setVerticalGroup(
            pn_DirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Previous, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addComponent(spn_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addComponent(btn_Next, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        pn_Container.setOpaque(false);
        pn_Container.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pn_ContainerComponentResized(evt);
            }
        });
        pn_Container.setLayout(null);

        pn_Divider.setOpaque(false);
        pn_Divider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pn_DividerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pn_DividerMouseExited(evt);
            }
        });

        lbl_Divider.setCursor(new java.awt.Cursor(java.awt.Cursor.E_RESIZE_CURSOR));
        lbl_Divider.setDoubleBuffered(true);
        lbl_Divider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_DividerMouseExited(evt);
            }
        });
        lbl_Divider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbl_DividerMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lbl_DividerMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout pn_DividerLayout = new javax.swing.GroupLayout(pn_Divider);
        pn_Divider.setLayout(pn_DividerLayout);
        pn_DividerLayout.setHorizontalGroup(
            pn_DividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Divider, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pn_DividerLayout.setVerticalGroup(
            pn_DividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Divider, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pn_Container.add(pn_Divider);
        pn_Divider.setBounds(151, 24, 20, 20);

        spp_Panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        spp_Panel.setDividerLocation(150);
        spp_Panel.setDividerSize(0);
        spp_Panel.setOpaque(false);
        spp_Panel.setPreferredSize(new java.awt.Dimension(856, 150));

        scp_Dir.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scp_Dir.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scp_Dir.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scp_Dir.setMinimumSize(new java.awt.Dimension(1, 1));
        scp_Dir.setOpaque(false);

        lst_Dir.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lst_Dir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lst_Dir.setOpaque(false);
        lst_Dir.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lst_DirMouseWheelMoved(evt);
            }
        });
        lst_Dir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lst_DirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lst_DirMouseExited(evt);
            }
        });
        lst_Dir.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lst_DirValueChanged(evt);
            }
        });
        scp_Dir.setViewportView(lst_Dir);

        spp_Panel.setLeftComponent(scp_Dir);

        pn_Config.setOpaque(false);

        pn_Network.setOpaque(false);
        pn_Network.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pn_NetworkComponentResized(evt);
            }
        });

        javax.swing.GroupLayout pn_NetworkLayout = new javax.swing.GroupLayout(pn_Network);
        pn_Network.setLayout(pn_NetworkLayout);
        pn_NetworkLayout.setHorizontalGroup(
            pn_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 465, Short.MAX_VALUE)
        );
        pn_NetworkLayout.setVerticalGroup(
            pn_NetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pn_ConfigLayout = new javax.swing.GroupLayout(pn_Config);
        pn_Config.setLayout(pn_ConfigLayout);
        pn_ConfigLayout.setHorizontalGroup(
            pn_ConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_Network, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_ConfigLayout.setVerticalGroup(
            pn_ConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_Network, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        spp_Panel.setRightComponent(pn_Config);

        pn_Container.add(spp_Panel);
        spp_Panel.setBounds(11, 21, 640, 260);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_Dir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_Dir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lst_DirValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lst_DirValueChanged
        if (lst_Dir.getSelectedIndices().length == 0) {
            return;
        }
        Element e = (Element) lst_Dir.getSelectedValue();
        if (!e.getAttributeValue("type").toString().isEmpty()) {
            if(evt.getValueIsAdjusting())
                showVMNet(actdir.endsWith(".") ? actdir+ e.getName() : actdir + "." + e.getName());
            return;
        }
        namePath = namePath.concat(namePath.endsWith(".") ? e.getAttributeValue("name") : "." + e.getAttributeValue("name"));
        loadData(actdir.endsWith(".") ? actdir + e.getName() : actdir + "." + e.getName());
    }//GEN-LAST:event_lst_DirValueChanged

    private void btn_PreviousMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PreviousMouseEntered
        move2Right.start();
    }//GEN-LAST:event_btn_PreviousMouseEntered

    private void btn_PreviousMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PreviousMouseExited
        move2Right.stop();
    }//GEN-LAST:event_btn_PreviousMouseExited

    private void btn_NextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_NextMouseEntered
        move2Left.start();
    }//GEN-LAST:event_btn_NextMouseEntered

    private void btn_NextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_NextMouseExited
        move2Left.stop();
    }//GEN-LAST:event_btn_NextMouseExited

    private void lst_DirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_DirMouseEntered
        lbl_Divider.setVisible(true);
    }//GEN-LAST:event_lst_DirMouseEntered

    private void lst_DirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_DirMouseExited
        lbl_Divider.setVisible(false);
    }//GEN-LAST:event_lst_DirMouseExited

    private void pn_DividerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_DividerMouseEntered
        lbl_Divider.setVisible(true);
    }//GEN-LAST:event_pn_DividerMouseEntered

    private void pn_DividerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_DividerMouseExited
        lbl_Divider.setVisible(false);
    }//GEN-LAST:event_pn_DividerMouseExited

    private void lbl_DividerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DividerMouseDragged
        lbl_Divider.setVisible(true);
        int x = evt.getXOnScreen();
        if (x < spp_Panel.getLocationOnScreen().x || x > spp_Panel.getLocationOnScreen().x + spp_Panel.getWidth()) {
            return;
        }

        spp_Panel.setDividerLocation(x - this.getLocationOnScreen().x);
        pn_Divider.setLocation(x - pn_Divider.getWidth() / 2 - this.getLocationOnScreen().x, spp_Panel.getLocation().y);
    }//GEN-LAST:event_lbl_DividerMouseDragged

    private void lbl_DividerMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DividerMouseMoved
        lbl_Divider.setVisible(true);
    }//GEN-LAST:event_lbl_DividerMouseMoved

    private void lbl_DividerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DividerMouseExited
        lbl_Divider.setVisible(false);
    }//GEN-LAST:event_lbl_DividerMouseExited

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.updateUI();
    }//GEN-LAST:event_formComponentResized

    private void pn_ContainerComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pn_ContainerComponentResized
        spp_Panel.setBounds(0, 0, pn_Container.getWidth(), pn_Container.getHeight());
        pn_Divider.setLocation(spp_Panel.getDividerLocation() - pn_Divider.getWidth() / 2, spp_Panel.getLocation().y);
    }//GEN-LAST:event_pn_ContainerComponentResized

    private void lst_DirMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lst_DirMouseWheelMoved
        if (evt.getWheelRotation() < 0) {
            if (scp_Dir.getViewport().getViewPosition().y <= 0) {
                return;
            }
            if (scp_Dir.getViewport().getViewPosition().y >= 30) {
                scp_Dir.getViewport().setViewPosition(new Point(scp_Dir.getViewport().getViewPosition().x, scp_Dir.getViewport().getViewPosition().y - 30));
            } else {
                scp_Dir.getViewport().setViewPosition(new Point(scp_Dir.getViewport().getViewPosition().x, 0));
            }
        } else {
            if (scp_Dir.getViewport().getViewPosition().y >= scp_Dir.getViewport().getViewSize().height - scp_Dir.getHeight()) {
                return;
            }
            if (scp_Dir.getViewport().getViewPosition().y + 30 <= scp_Dir.getViewport().getViewSize().height - scp_Dir.getHeight()) {
                scp_Dir.getViewport().setViewPosition(new Point(scp_Dir.getViewport().getViewPosition().x, scp_Dir.getViewport().getViewPosition().y + 30));
            } else {
                scp_Dir.getViewport().setViewPosition(new Point(scp_Dir.getViewport().getViewPosition().x, scp_Dir.getViewport().getViewSize().height - scp_Dir.getHeight()));
            }
        }
        scp_Dir.updateUI();
    }//GEN-LAST:event_lst_DirMouseWheelMoved

    private void pn_NetworkComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pn_NetworkComponentResized
        if(pn_Network.getComponentCount()==1){
            pn_Network.getComponent(0).setBounds(0, 0, pn_Network.getWidth(), pn_Network.getHeight());
            pn_Network.updateUI();
        }
    }//GEN-LAST:event_pn_NetworkComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Next;
    private javax.swing.JButton btn_Previous;
    private javax.swing.JLabel lbl_Divider;
    private javax.swing.JList lst_Dir;
    private javax.swing.JPanel pn_Bt_Dir;
    private javax.swing.JPanel pn_Config;
    private javax.swing.JPanel pn_Container;
    private javax.swing.JPanel pn_Dir;
    private javax.swing.JPanel pn_Divider;
    private javax.swing.JPanel pn_Network;
    private javax.swing.JScrollPane scp_Dir;
    private javax.swing.JScrollPane spn_Panel;
    private javax.swing.JSplitPane spp_Panel;
    // End of variables declaration//GEN-END:variables
    Timer move2Right = new Timer(50, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (spn_Panel.getViewport().getViewPosition().x <= 0) {
                return;
            } else if (spn_Panel.getViewport().getViewPosition().x > 30) {
                spn_Panel.getViewport().setViewPosition(new Point(spn_Panel.getViewport().getViewPosition().x - 30, spn_Panel.getViewport().getViewPosition().y));
            } else {
                spn_Panel.getViewport().setViewPosition(new Point(0, spn_Panel.getViewport().getViewPosition().y));
            }
        }
    });
    Timer move2Left = new Timer(50, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (spn_Panel.getViewport().getViewPosition().x + spn_Panel.getWidth() >= pn_Bt_Dir.getWidth()) {
                return;
            }
            spn_Panel.getViewport().setViewPosition(new Point(spn_Panel.getViewport().getViewPosition().x + 30, spn_Panel.getViewport().getViewPosition().y));
        }
    });

    private void loadData(String netpath) {
        Element aux = XML.getElementOnPath(netpath, Config.rootNetwork);
        TreeMap<String, Element> tm = new TreeMap<String, Element>();
        if (aux == null) {
            System.err.println("netCreator : XML path not found!");
            return;
        }
        model.removeAllElements();
        Iterator it = aux.getChildren().iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            if (e.getAttributeValue("type").isEmpty()) {
                tm.put(e.getAttributeValue("name"), e);
            }else{
                tm.put("~" + e.getAttributeValue("name"), e);
            }
        }
        it = tm.keySet().iterator();
        while(it.hasNext()){
            model.addElement(tm.get((String) it.next()));
        }
        
        actdir = netpath;
        lst_Dir.updateUI();
        reloadButtons();
        showGroupNet();
    }

    private void reloadButtons() {

        String[] dirs = namePath.split("[.]");
        int i = 0;
        paths.clear();
        pn_Bt_Dir.removeAll();
        if (dirs.length == 0) {
            dirs = new String[]{""};
        }
        for (String dir : dirs) {
            JButton aux;
            if (dir.isEmpty()) {
                aux = new JButton(new ImageIcon(getClass().getResource("/kuasar/plugin/netcreator/icons/home.png")));
            } else {
                aux = new JButton(dir);
            }
            aux.setCursor(new Cursor(Cursor.HAND_CURSOR));
            aux.setFont(new java.awt.Font("Verdana", 0, 15));
            aux.setName(Integer.toString(i++));
            aux.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JButton bt = (JButton) e.getSource();
                    try {
                        returnToPath(Integer.parseInt(bt.getName()));
                    } catch (NumberFormatException ex) {
                        System.err.println("Button id isn't a correct number.");
                    }
                }
            });
            aux.updateUI();
            paths.add(aux);
        }

        javax.swing.GroupLayout pn_Bt_DirLayout = new javax.swing.GroupLayout(pn_Bt_Dir);
        pn_Bt_Dir.setLayout(pn_Bt_DirLayout);
        pn_Bt_DirLayout.setAutoCreateGaps(true);
        pn_Bt_DirLayout.setAutoCreateContainerGaps(true);

        SequentialGroup hsg = pn_Bt_DirLayout.createSequentialGroup();

        Iterator<JButton> ibuttons = paths.iterator();
        while (ibuttons.hasNext()) {
            hsg.addComponent(ibuttons.next());
        }

        pn_Bt_DirLayout.setHorizontalGroup(hsg);

        SequentialGroup vsg = pn_Bt_DirLayout.createSequentialGroup();
        ParallelGroup vgrp = pn_Bt_DirLayout.createParallelGroup(Alignment.LEADING);

        ibuttons = paths.iterator();
        while (ibuttons.hasNext()) {
            vgrp.addComponent(ibuttons.next(), 34, 34, 34);
        }

        vsg.addGroup(vgrp);

        pn_Bt_DirLayout.setVerticalGroup(vsg);
        spn_Panel.getViewport().setViewPosition(new Point(spn_Panel.getViewport().getViewSize().getSize().width, spn_Panel.getViewport().getViewSize().height));
        pn_Bt_Dir.updateUI();
    }

    private void returnToPath(int position) {
        String path = "";
        String[] dirs = actdir.split("[.]");
        String[] names = namePath.split("[.]");
        namePath = "";
        if (dirs.length != names.length) {
            System.err.println("Directories and Names Vector haven't same length");
            return;
        }
        if (position > dirs.length) {
            System.err.println("Position is bigger than directories lenght");
            return;
        }
        if (dirs.length > 0) {
            for (int i = 0; i <= position; i++) {
                if (!dirs[i].isEmpty()) {
                    path = path.concat("." + dirs[i]);
                    namePath = namePath.concat("." + names[i]);
                }
            }
            if (path.isEmpty()) {
                path = path.concat(".");
                namePath = namePath.concat(".");
            }
        } else {
            namePath = ".";
            path = ".";
        }

        loadData(path);
    }
    private void showGroupNet(){
        pn_GroupNetwork gn = new pn_GroupNetwork(actdir);
        gn.setBounds(0, 0, pn_Network.getWidth(), pn_Network.getHeight());
        pn_Network.removeAll();
        pn_Network.add(gn);
        pn_Network.updateUI();
    }
    private void showVMNet(String nodePath){
        pn_VMNetwork vmn = new pn_VMNetwork(nodePath);
        vmn.setBounds(0, 0, pn_Network.getWidth(), pn_Network.getHeight());
        pn_Network.removeAll();
        pn_Network.add(vmn);
        pn_Network.updateUI();
    }
}