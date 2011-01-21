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

package kuasar.plugin.classMod;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */

public class Panel_RoundedG extends  javax.swing.JPanel {
@Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        float x = getWidth();
        float y = getHeight();
        g2.setPaint(new GradientPaint(0.0f, 0.0f, new Color(255,255,255).darker(),
                0.0f, getHeight(), new Color(0,0,0).brighter()));
        g2.fill(new RoundRectangle2D.Double(0, 0, x, y, 25, 25));
        g2.setPaint(new GradientPaint(0.0f, 0.0f, new Color(0,0,0).brighter(),
                getWidth(), getHeight(), new Color(255,255,255)));
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.black);
    }
}
