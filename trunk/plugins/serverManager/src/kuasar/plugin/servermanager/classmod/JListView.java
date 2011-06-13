package kuasar.plugin.servermanager.classmod;

import java.awt.*;
import javax.swing.*;
import kuasar.plugin.servermanager.Config;

/**
 *
 *  @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 *
 *  @thanksto arittner http://www.smurfi.de/memberlist.php?mode=viewprofile&u=1195&sid=bafc4ba939fe476cc2c210eae67a45d8
 *
 */
public class JListView extends JList {

    private int defaultIconSize;
    private EmptyIcon EMPTY;

    public JListView() {
        this(48);
    }

    public JListView(int defaultIconSize) {
        super();
        this.defaultIconSize = defaultIconSize;
        setVisibleRowCount(-1);
        setLayoutOrientation(JList.HORIZONTAL_WRAP);
        setCellRenderer(new IconRenderer());
        EMPTY = new EmptyIcon();
    }

    private class IconRenderer implements ListCellRenderer {

        pn_Icon panel = new pn_Icon();
        JLabel label = new JLabel(" ", EMPTY, JLabel.CENTER);

        {

            panel.lbl_Icon.setVerticalTextPosition(JLabel.BOTTOM);
            panel.lbl_Icon.setHorizontalTextPosition(JLabel.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            Object[] info = (Object[]) value;
            ImageIcon icon = (ImageIcon) info[Config.ServerList.ICON];
            panel.lbl_Text.setText((String) info[Config.ServerList.NAME]);
             if(cellHasFocus){
                if(info[Config.ServerList.LABEL]!=null)((JLabel) info[Config.ServerList.LABEL]).setText((String) info[Config.ServerList.NAME]);
            }
            panel.lbl_Icon.setBackground(isSelected ? UIManager.getColor("List.selectionBackground") : Color.WHITE);
            panel.lbl_Text.setForeground(isSelected ? Color.BLACK : Color.WHITE);
            panel.lbl_Icon.setBackground(Color.red);
            panel.setOpaque(isSelected);
            panel.selected = isSelected;
            panel.lbl_Icon.setIcon(icon==null ? EMPTY: icon);
            panel.lbl_Icon.setText("");
            return panel;

        }
    }

    private class EmptyIcon implements Icon {

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return defaultIconSize;
        }

        @Override
        public int getIconHeight() {
            return defaultIconSize;
        }
    }
}
