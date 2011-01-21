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

package kuasar.gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import kuasar.plugin.PluginInterface;
import kuasar.util.config.Configuration;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */

public class fun_frm_Main extends frm_Main {
    

    public fun_frm_Main(){
        setListeners();
        System.gc();
    }
    public void launchInfo(String message){
        ImageIcon downbar = (ImageIcon) lbl_Animation.getIcon();
        lbl_Animation.setIcon(null);
        downbar.getImage().flush();
        lbl_Animation.setIcon(downbar);
        lbl_Info.setText(message);
    }

    public void clearInfo(){
        lbl_Info.setText("");
    }
    
    public String getInfo(){
        return lbl_Animation.getText();
    }

    public void unLoadPlugin(){
       if(unLoadFrame())
           unLoadTB();
        unCheckList();
    }

    public void updateUI(){
        pn_ShareContainerFrame.updateUI();
        pn_ToolBar.updateUI();
        pn_ToolBarShare.updateUI();
        pn_ToolBar.setVisible(!pn_ToolBar.isVisible());
        pn_ToolBar.setVisible(!pn_ToolBar.isVisible());
    }
    
    private void setListeners(){
    mni_Preferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mni_PreferencesActionPerformed(evt);
            }
        });
        lst_Plugins.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_PluginsMouseClicked(evt);
            }
        });
    }

    private void lst_PluginsMouseClicked(java.awt.event.MouseEvent evt) {
        if(lst_Plugins.getSelectedIndex() == actLoaded)
            return;
        actLoaded=lst_Plugins.getSelectedIndex();
        PluginInterface[] plugins = (PluginInterface[]) Configuration.getODR(Configuration.Plugins.pluginsKey);
        plugins[lst_Plugins.getSelectedIndex()].Load(this, this.getClass());

    }
    private void mni_PreferencesActionPerformed(java.awt.event.ActionEvent evt){
        kuasar.plugin.classMod.AbstractPanel cfg = new kuasar.util.config.pn_Config(this);
        loadFrame(cfg);
        hideMenu();
        unCheckList();
        actLoaded = -1;
    }
    
    public void loadPlugin(JPanel panel){
        loadFrame(panel);
    }

    public void visibleToolBar(){
        pn_ToolBar.setVisible(true);
    }

    public void invisibleToolBar(){
        pn_ToolBar.setVisible(false);
    }

    public void loadToolBar(JPanel panel){
        loadTB(panel);

    }
    public void unLoadToolBar(){
        unLoadTB();
    }
}
