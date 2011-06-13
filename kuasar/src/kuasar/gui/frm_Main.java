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
 * frm_Main.java
 *
 * Created on 22/07/2010, 15:21:58
 */
package kuasar.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.IllegalComponentStateException;
import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;
import kuasar.plugin.Intercom.ODR;
import kuasar.plugin.PluginInterface;
import kuasar.util.Image;
import kuasar.plugin.classMod.Panel_Opaque;
import kuasar.util.Files;
import kuasar.util.config.Configuration;
import kuasar.plugin.classMod.AbstractPanel;
import kuasar.util.plugins.PluginLoader;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class frm_Main extends javax.swing.JFrame {

    private boolean isOnlyAlt = true;
    protected int actLoaded = -1;

    /** Creates new form frm_Main */
    public frm_Main() {
        initComponents();
        initConfiguration();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_MainFrame = new javax.swing.JPanel();
        lyp_Upper = new javax.swing.JLayeredPane();
        pn_divider = new javax.swing.JPanel();
        lbl_divider = new javax.swing.JLabel();
        lyp_Medium = new javax.swing.JLayeredPane();
        spp_Base = new javax.swing.JSplitPane();
        scp_Plugins = new javax.swing.JScrollPane();
        lst_Plugins = new javax.swing.JList();
        lyp_PluginFrame = new javax.swing.JLayeredPane();
        pn_PluginFrame = new Panel_Opaque();
        pn_ShareContainerFrame = new javax.swing.JPanel();
        pn_ToolBar = new Panel_Opaque();
        lbl_ToolBarShow = new javax.swing.JLabel();
        pn_ToolBarShare = new javax.swing.JPanel();
        lbl_Header = new javax.swing.JLabel();
        pn_Down = new javax.swing.JPanel();
        lyp_Info = new javax.swing.JLayeredPane();
        lbl_Info = new javax.swing.JLabel();
        lbl_Animation = new javax.swing.JLabel();
        mnb_Menu = new javax.swing.JMenuBar();
        mn_File = new javax.swing.JMenu();
        mi_Close = new javax.swing.JMenuItem();
        mn_Edit = new javax.swing.JMenu();
        mni_Preferences = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kuasar (VM Manager) 0.1");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pn_MainFrame.setBackground(new java.awt.Color(0, 0, 0));

        lyp_Upper.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lyp_UpperComponentResized(evt);
            }
        });

        pn_divider.setCursor(new java.awt.Cursor(java.awt.Cursor.E_RESIZE_CURSOR));
        pn_divider.setOpaque(false);

        lbl_divider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_dividerMouseExited(evt);
            }
        });
        lbl_divider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbl_dividerMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout pn_dividerLayout = new javax.swing.GroupLayout(pn_divider);
        pn_divider.setLayout(pn_dividerLayout);
        pn_dividerLayout.setHorizontalGroup(
            pn_dividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dividerLayout.createSequentialGroup()
                .addComponent(lbl_divider)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pn_dividerLayout.setVerticalGroup(
            pn_dividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dividerLayout.createSequentialGroup()
                .addComponent(lbl_divider)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pn_divider.setBounds(120, 100, 16, 16);
        lyp_Upper.add(pn_divider, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lyp_Medium.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lyp_MediumComponentResized(evt);
            }
        });

        spp_Base.setBorder(null);
        spp_Base.setDividerLocation(150);
        spp_Base.setDividerSize(0);
        spp_Base.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                spp_BaseComponentResized(evt);
            }
        });

        scp_Plugins.setBorder(null);
        scp_Plugins.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scp_Plugins.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scp_Plugins.setOpaque(false);
        scp_Plugins.getViewport().setOpaque(false);
        scp_Plugins.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                scp_PluginsComponentResized(evt);
            }
        });

        lst_Plugins.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lst_Plugins.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lst_Plugins.setOpaque(false);
        lst_Plugins.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lst_PluginsMouseWheelMoved(evt);
            }
        });
        lst_Plugins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lst_PluginsMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lst_PluginsMouseEntered(evt);
            }
        });
        lst_Plugins.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lst_PluginsKeyReleased(evt);
            }
        });
        scp_Plugins.setViewportView(lst_Plugins);

        spp_Base.setLeftComponent(scp_Plugins);

        lyp_PluginFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lyp_PluginFrameComponentResized(evt);
            }
        });
        lyp_PluginFrame.setOpaque(false);

        pn_PluginFrame.setOpaque(false);
        pn_PluginFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pn_PluginFrameComponentResized(evt);
            }
        });

        pn_ShareContainerFrame.setOpaque(false);
        pn_ShareContainerFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pn_ShareContainerFrameComponentResized(evt);
            }
        });

        javax.swing.GroupLayout pn_ShareContainerFrameLayout = new javax.swing.GroupLayout(pn_ShareContainerFrame);
        pn_ShareContainerFrame.setLayout(pn_ShareContainerFrameLayout);
        pn_ShareContainerFrameLayout.setHorizontalGroup(
            pn_ShareContainerFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );
        pn_ShareContainerFrameLayout.setVerticalGroup(
            pn_ShareContainerFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 146, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pn_PluginFrameLayout = new javax.swing.GroupLayout(pn_PluginFrame);
        pn_PluginFrame.setLayout(pn_PluginFrameLayout);
        pn_PluginFrameLayout.setHorizontalGroup(
            pn_PluginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_PluginFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_ShareContainerFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_PluginFrameLayout.setVerticalGroup(
            pn_PluginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_PluginFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_ShareContainerFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pn_PluginFrame.setBounds(10, 0, 470, 170);
        lyp_PluginFrame.add(pn_PluginFrame, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spp_Base.setRightComponent(lyp_PluginFrame);

        spp_Base.setBounds(20, 0, 640, 170);
        lyp_Medium.add(spp_Base, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lyp_Medium.setBounds(0, 110, 670, 230);
        lyp_Upper.add(lyp_Medium, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pn_ToolBar.setBackground(new java.awt.Color(153, 0, 0));
        pn_ToolBar.setName("show"); // NOI18N
        pn_ToolBar.setOpaque(false);
        pn_ToolBar.setPreferredSize(new java.awt.Dimension(130, 60));

        lbl_ToolBarShow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_ToolBarShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_ToolBarShowMousePressed(evt);
            }
        });

        pn_ToolBarShare.setOpaque(false);
        pn_ToolBarShare.setPreferredSize(new java.awt.Dimension(300, 60));

        javax.swing.GroupLayout pn_ToolBarShareLayout = new javax.swing.GroupLayout(pn_ToolBarShare);
        pn_ToolBarShare.setLayout(pn_ToolBarShareLayout);
        pn_ToolBarShareLayout.setHorizontalGroup(
            pn_ToolBarShareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );
        pn_ToolBarShareLayout.setVerticalGroup(
            pn_ToolBarShareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 57, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pn_ToolBarLayout = new javax.swing.GroupLayout(pn_ToolBar);
        pn_ToolBar.setLayout(pn_ToolBarLayout);
        pn_ToolBarLayout.setHorizontalGroup(
            pn_ToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_ToolBarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(pn_ToolBarShare, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_ToolBarShow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_ToolBarLayout.setVerticalGroup(
            pn_ToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_ToolBarLayout.createSequentialGroup()
                .addGroup(pn_ToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pn_ToolBarShare, 0, 57, Short.MAX_VALUE)
                    .addComponent(lbl_ToolBarShow, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );

        pn_ToolBar.setBounds(-40, 30, 140, 60);
        lyp_Upper.add(pn_ToolBar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lbl_Header.setBounds(0, 0, 670, 140);
        lyp_Upper.add(lbl_Header, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pn_Down.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pn_Down.setOpaque(false);

        lyp_Info.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lyp_InfoComponentResized(evt);
            }
        });

        lbl_Info.setForeground(new java.awt.Color(153, 153, 153));
        lbl_Info.setBounds(20, 10, 400, 20);
        lyp_Info.add(lbl_Info, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lbl_Animation.setBounds(0, 0, 670, 30);
        lyp_Info.add(lbl_Animation, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pn_DownLayout = new javax.swing.GroupLayout(pn_Down);
        pn_Down.setLayout(pn_DownLayout);
        pn_DownLayout.setHorizontalGroup(
            pn_DownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lyp_Info, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
        );
        pn_DownLayout.setVerticalGroup(
            pn_DownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lyp_Info, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pn_MainFrameLayout = new javax.swing.GroupLayout(pn_MainFrame);
        pn_MainFrame.setLayout(pn_MainFrameLayout);
        pn_MainFrameLayout.setHorizontalGroup(
            pn_MainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_Down, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lyp_Upper, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
        );
        pn_MainFrameLayout.setVerticalGroup(
            pn_MainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_MainFrameLayout.createSequentialGroup()
                .addComponent(lyp_Upper, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_Down, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mn_File.setText("File");

        mi_Close.setText("Close");
        mi_Close.setActionCommand("mi_Close");
        mn_File.add(mi_Close);

        mnb_Menu.add(mn_File);

        mn_Edit.setText("Edit");

        mni_Preferences.setText("Preferences");
        mn_Edit.add(mni_Preferences);

        mnb_Menu.add(mn_Edit);

        setJMenuBar(mnb_Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_MainFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_MainFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // <editor-fold defaultstate="collapsed" desc="Object Events">
    private void lyp_UpperComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lyp_UpperComponentResized
        lbl_Header.setLocation(lyp_Upper.getSize().width - Image.getsize(lbl_Header.getIcon()).width, 0);
        lyp_Medium.setSize(lyp_Upper.getWidth(), lyp_Upper.getHeight() - lyp_Medium.getLocation().y);
    }//GEN-LAST:event_lyp_UpperComponentResized

    private void lyp_MediumComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lyp_MediumComponentResized
        spp_Base.setSize(lyp_Medium.getSize().width - spp_Base.getLocation().x - 10, lyp_Medium.getSize().height);
    }//GEN-LAST:event_lyp_MediumComponentResized

    private void spp_BaseComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_spp_BaseComponentResized
        scp_Plugins.setSize(scp_Plugins.getWidth(), spp_Base.getHeight());
        lyp_PluginFrame.setSize(spp_Base.getWidth() - lst_Plugins.getWidth(), spp_Base.getHeight());
    }//GEN-LAST:event_spp_BaseComponentResized

    private void scp_PluginsComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_scp_PluginsComponentResized
        lst_Plugins.setSize(scp_Plugins.getSize());
        pn_divider.setLocation(spp_Base.getDividerLocation() + spp_Base.getLocation().x - (pn_divider.getWidth() / 2), lyp_Medium.getLocation().y - (pn_divider.getHeight() / 2));
    }//GEN-LAST:event_scp_PluginsComponentResized

    private void lbl_dividerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_dividerMouseDragged
        int x = evt.getXOnScreen();
        if (x < spp_Base.getLocationOnScreen().x || x > spp_Base.getLocationOnScreen().x + spp_Base.getWidth()) {
            return;
        }
        pn_divider.setLocation(x - pn_divider.getWidth() / 2 - this.getLocationOnScreen().x, pn_divider.getLocation().y);
        spp_Base.setDividerLocation(x - lbl_divider.getWidth() - this.getLocationOnScreen().x);
    }//GEN-LAST:event_lbl_dividerMouseDragged

    private void lst_PluginsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_PluginsMouseEntered
        lbl_divider.setVisible(true);
    }//GEN-LAST:event_lst_PluginsMouseEntered

    private void lst_PluginsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_PluginsMouseExited
        isMouseOver(evt);
    }//GEN-LAST:event_lst_PluginsMouseExited

    private void lbl_dividerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_dividerMouseExited
        isMouseOver(evt);
    }//GEN-LAST:event_lbl_dividerMouseExited

    private void lst_PluginsMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lst_PluginsMouseWheelMoved
        scp_Plugins.getVerticalScrollBar().setValue(scp_Plugins.getVerticalScrollBar().getValue() + (evt.getWheelRotation()) * 20);
    }//GEN-LAST:event_lst_PluginsMouseWheelMoved

    private void lyp_PluginFrameComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lyp_PluginFrameComponentResized
        pn_PluginFrame.setSize(lyp_PluginFrame.getWidth() - pn_PluginFrame.getLocation().x, lyp_PluginFrame.getHeight());
    }//GEN-LAST:event_lyp_PluginFrameComponentResized

    private void pn_PluginFrameComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pn_PluginFrameComponentResized
        pn_ShareContainerFrame.setSize(pn_PluginFrame.getWidth() - (pn_ShareContainerFrame.getLocation().x * 2), pn_PluginFrame.getHeight() - (pn_ShareContainerFrame.getLocation().y * 2));
    }//GEN-LAST:event_pn_PluginFrameComponentResized

    private void pn_ShareContainerFrameComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pn_ShareContainerFrameComponentResized
        if (pn_ShareContainerFrame.getComponentCount() > 0) {
            pn_ShareContainerFrame.getComponent(0).setBounds(0, 0, pn_ShareContainerFrame.getWidth(), pn_ShareContainerFrame.getHeight());
            pn_ShareContainerFrame.revalidate();
        }
    }//GEN-LAST:event_pn_ShareContainerFrameComponentResized

    private void lst_PluginsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lst_PluginsKeyReleased
        hideShowMenu(evt);
    }//GEN-LAST:event_lst_PluginsKeyReleased

    private void lyp_InfoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lyp_InfoComponentResized
        lbl_Info.setSize(lyp_Info.getWidth() - lyp_Info.getLocation().x, lbl_Info.getHeight());
        lbl_Animation.setSize(lyp_Info.getSize());
    }//GEN-LAST:event_lyp_InfoComponentResized

    private void lbl_ToolBarShowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ToolBarShowMousePressed
        hideShowToolBar();
}//GEN-LAST:event_lbl_ToolBarShowMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       PluginInterface[] plugins = (PluginInterface[]) ODR.getValue("$PLUGINS");
       for(PluginInterface plugin : plugins){
           System.out.println("Stopping " + plugin.getName() + " plugin...\t" + (plugin.Stop()? "OK :-)":"Error :-S"));
       }
    }//GEN-LAST:event_formWindowClosing
// </editor-fold>
   // <editor-fold defaultstate="collapsed" desc="Functions">
    private void hideShowToolBar() {
        if (pn_ToolBar.getName().equals("hiden")) {
            pn_ToolBarShare.setVisible(true);
            pn_ToolBar.setName("shown");
            if (pn_ToolBarShare.getComponentCount() > 0) {
                pn_ToolBar.setSize(pn_ToolBarShare.getComponent(0).getPreferredSize().width + lbl_ToolBarShow.getWidth() + pn_ToolBar.getLocation().x * -1 + 5, pn_ToolBar.getHeight());
                pn_ToolBarShare.setSize(pn_ToolBarShare.getComponent(0).getPreferredSize().width, pn_ToolBar.getHeight());
            } else {
                pn_ToolBar.setSize(50 + lbl_ToolBarShow.getWidth() + pn_ToolBar.getLocation().x * -1 + 15, pn_ToolBar.getHeight());
                pn_ToolBarShare.setSize(50, pn_ToolBar.getHeight());
            }

            if ((new File(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.Images.hide).exists())) {
                lbl_ToolBarShow.setIcon(new ImageIcon(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.Images.hide));
            }

        } else {
            pn_ToolBarShare.setVisible(false);
            pn_ToolBar.setName("hiden");
            pn_ToolBarShare.setSize(0, pn_ToolBar.getHeight());
            pn_ToolBar.setSize(pn_ToolBar.getLocation().x * -1 + lbl_ToolBarShow.getWidth(), pn_ToolBar.getHeight());
            if ((new File(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.Images.show).exists())) {
                lbl_ToolBarShow.setIcon(new ImageIcon(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.Images.show));
            }

        }

        lbl_ToolBarShow.setLocation(pn_ToolBarShare.getWidth() + pn_ToolBar.getLocation().x * -1, lbl_ToolBarShow.getLocation().y);
        pn_ToolBarShare.updateUI();
        pn_ToolBar.updateUI();
        lyp_Upper.updateUI();
    }

    private void hideShowMenu(java.awt.event.KeyEvent evt) {
        if (isOnlyAlt == false) {
            isOnlyAlt = true;
            return;
        }
        if (evt.getKeyCode() == 18) {
            if (!evt.isAltDown()) {
                mnb_Menu.setVisible(!mnb_Menu.isVisible());
            }
        }
        if (evt.isAltDown()) {
            if (evt.getKeyCode() != 18) {
                isOnlyAlt = false;
            }
        }
    }

    protected void unCheckList() {
        lst_Plugins.clearSelection();

        actLoaded = -1;
    }

    private void setSkin() {
        String skinpath = Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator;
        if ((new File(skinpath + Configuration.Interfice.Images.header).exists())) {
            lbl_Header.setIcon(new ImageIcon(skinpath + Configuration.Interfice.Images.header));
        }
        if ((new File(skinpath + Configuration.Interfice.Images.downbar).exists())) {
            lbl_Animation.setIcon(new ImageIcon(skinpath + Configuration.Interfice.Images.downbar));
        }
        if ((new File(skinpath + Configuration.Interfice.Images.adjust).exists())) {
            lbl_divider.setIcon(new ImageIcon(skinpath + Configuration.Interfice.Images.adjust));
        }
        if ((new File(skinpath + Configuration.Interfice.Images.show).exists())) {
            lbl_ToolBarShow.setIcon(new ImageIcon(skinpath + Configuration.Interfice.Images.show));
        }

        HashMap data = Files.loadMap(Configuration.startDir + Configuration.Interfice.pathskins + File.separator + Configuration.Interfice.skin + File.separator + Configuration.Interfice.cfgskin);
        try {
            pn_MainFrame.setBackground(new Color(Integer.parseInt(data.get("background").toString().substring(2).trim(), 16)));
        } catch (NumberFormatException ex) {
            kuasar.util.Error.console(this.getClass().getCanonicalName(), "Background color undefined or hex number isn't a color");
        }

    }

    private void initConfiguration() {
        setSkin();
        setHeader();
        hideShowToolBar();
        pn_ToolBar.setVisible(false);
        mnb_Menu.setVisible(false);
        scp_Plugins.getViewport().setOpaque(false);
        lst_Plugins.setOpaque(false);
        lbl_divider.setVisible(false);
        lst_Plugins.setCellRenderer(new kuasar.classMod.Lst_Plugins_CellRender()); //IT MAKES CELLS TRANSPARENTS AND ROUNDED
    }

    private void isMouseOver(java.awt.event.MouseEvent evt) {
        try {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            if ((x > lst_Plugins.getLocationOnScreen().x && x < lst_Plugins.getLocationOnScreen().x + lst_Plugins.getWidth() && y > lst_Plugins.getLocationOnScreen().y && y < lst_Plugins.getLocationOnScreen().y + lst_Plugins.getHeight()) || (x > lbl_divider.getLocationOnScreen().x && x < lbl_divider.getLocationOnScreen().x + lbl_divider.getWidth() && y > lbl_divider.getLocationOnScreen().y && y < lbl_divider.getLocationOnScreen().y + lbl_divider.getHeight())) {
                lbl_divider.setVisible(true);
            } else {
                lbl_divider.setVisible(false);
            }
        } catch (IllegalComponentStateException ex) {
            return;
        }

    }

    private void setHeader() {
        lbl_Header.setSize(Image.getsize(lbl_Header.getIcon()));
        this.repaint();
    }

    protected void loadFrame(Component panel) {
        if (!unLoadFrame()) {
            return;
        }
        panel.setBounds(0, 0, pn_ShareContainerFrame.getWidth(), pn_ShareContainerFrame.getHeight());
        pn_ShareContainerFrame.add(panel, null);
        pn_ShareContainerFrame.updateUI();
    }

    protected boolean unLoadFrame() {
        if (pn_ShareContainerFrame.getComponentCount() > 0) {
            if (((AbstractPanel) pn_ShareContainerFrame.getComponent(0)).unLoad(pn_ShareContainerFrame)) {
                pn_ShareContainerFrame.removeAll();
                pn_ShareContainerFrame.updateUI();
                return true;
            }
            unCheckList();
            return false;
        }
        return true;
    }

    protected void loadTB(Component panel) {
        unLoadTB();
        panel.setBounds(0, 0, panel.getPreferredSize().width, panel.getPreferredSize().height);
        pn_ToolBarShare.add(panel, null);
        this.hideShowToolBar();
        this.hideShowToolBar();
    }

    protected void unLoadTB() {
        pn_ToolBarShare.removeAll();
        pn_ToolBarShare.updateUI();
    }

    protected void hideMenu() {
        mnb_Menu.setVisible(false);
    }
    // </editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel lbl_Animation;
    private javax.swing.JLabel lbl_Header;
    protected javax.swing.JLabel lbl_Info;
    private javax.swing.JLabel lbl_ToolBarShow;
    private javax.swing.JLabel lbl_divider;
    protected javax.swing.JList lst_Plugins;
    private javax.swing.JLayeredPane lyp_Info;
    private javax.swing.JLayeredPane lyp_Medium;
    private javax.swing.JLayeredPane lyp_PluginFrame;
    private javax.swing.JLayeredPane lyp_Upper;
    private javax.swing.JMenuItem mi_Close;
    private javax.swing.JMenu mn_Edit;
    private javax.swing.JMenu mn_File;
    private javax.swing.JMenuBar mnb_Menu;
    protected javax.swing.JMenuItem mni_Preferences;
    private javax.swing.JPanel pn_Down;
    private javax.swing.JPanel pn_MainFrame;
    private javax.swing.JPanel pn_PluginFrame;
    protected javax.swing.JPanel pn_ShareContainerFrame;
    protected javax.swing.JPanel pn_ToolBar;
    protected javax.swing.JPanel pn_ToolBarShare;
    private javax.swing.JPanel pn_divider;
    private javax.swing.JScrollPane scp_Plugins;
    private javax.swing.JSplitPane spp_Base;
    // End of variables declaration//GEN-END:variables
}
