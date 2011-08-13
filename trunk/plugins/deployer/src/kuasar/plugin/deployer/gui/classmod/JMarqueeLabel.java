/*
 * Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kuasar.plugin.deployer.gui.classmod;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JLabel;



/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class JMarqueeLabel extends JLabel {
    public static final int MARQUEE_SPEED_DIV = 10;
    public static final int REPAINT_WITHIN_MS = 5;
    private boolean mouseIsOver = false;
    
    public JMarqueeLabel(){
        super();
        addListener();
    }
    
    public JMarqueeLabel(Icon image, int horizontalAlignment){
        super(image, horizontalAlignment);
        addListener();
    }
    
    public JMarqueeLabel(Icon image){
        super(image);
        addListener();
    }
    public JMarqueeLabel(String text, int horizontalAlignment){
        super(text, horizontalAlignment);
        addListener();
    }
    public JMarqueeLabel(String text){
        super(text);
        addListener();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if(getWidth()>getParent().getWidth())
            g.translate(getWidth() - (int)((System.currentTimeMillis() / MARQUEE_SPEED_DIV) % (getWidth() * 2)), 0);
        
        super.paintComponent(g); 
        if(!mouseIsOver)
            repaint(REPAINT_WITHIN_MS);

    }

    private void addListener() {
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent evt){
                mouseIsOver = true;
            }
            @Override
            public void mouseExited(MouseEvent evt){
                mouseIsOver = false;
                repaint(REPAINT_WITHIN_MS);
            }
        });
    }
}
