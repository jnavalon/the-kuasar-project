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
package kuasar.util;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.Icon;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Image {

    public static ImageIcon resize (Icon icon, int width, int height){
     
        if(icon==null){
            return null;

        }
        try{
          return resize((ImageIcon)icon, width, height);
        }catch(Exception ex){
            Error.console(Image.class.getCanonicalName() + ".resize(Icon icon, int width, int height)",ex.getMessage());
        }
       
        return null;
    }
    public static ImageIcon resize (String file, int width, int height){
        try{
             ImageIcon icon = new ImageIcon(file);
             return resize(icon,width,height);
        }catch(Exception ex){
           Error.console(Image.class.getCanonicalName() + ".resize(String file, int width, int height)",ex.getMessage());
        }
         return null;
    }
    public static ImageIcon resize (ImageIcon icon, int width , int height){
        try{
                 java.awt.Image image = icon.getImage();
                 image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                 return new ImageIcon(image);
        }catch(Exception ex){
                Error.console(Image.class.getCanonicalName() + ".resize (ImageIcon icon, int width , int height)",ex.getMessage());
        }
        return null;
    }
    public static Dimension getsize(String file){
        try{
            ImageIcon icon = new ImageIcon(file);
            return new Dimension(icon.getIconWidth(), icon.getIconHeight());
        }catch(Exception ex){
            Error.console(Image.class.getCanonicalName() + ".getsize(String file)",ex.getMessage());
            return new Dimension(0,0);
        }
       

    }
    public static Dimension getsize(Icon icon){
        try{
            return  new Dimension(icon.getIconWidth(), icon.getIconHeight());
        }catch(Exception ex){
            return new Dimension(0,0);
        }
        
    }
}
