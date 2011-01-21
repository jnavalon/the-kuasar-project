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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Files {

    public static Stack<String> getDirs(String path) {
        Stack<String> dirs = new Stack<String>();
        try {
            for (File file : new File(path).listFiles()) {
                if (file.isDirectory()) {
                    dirs.add(file.getName());
                }
            }
            return dirs;
        } catch (Exception ex) {
            return null;
        }

    }

    public static HashMap loadMap(String path) {
        HashMap<String, String> info = new HashMap<String, String>();
        String line;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            return info;
        }
        try {

            line = reader.readLine();
            while (line != null) {
                info.put(line.split("=")[0], line.substring(line.indexOf("=") + 1, line.length()));
                line = reader.readLine();
            }

        } catch (IOException ex) {
            return info;
        }
        return info;
    }

    public static Stack<String> getFiles(String path) {
        Stack<String> files = new Stack<String>();
        try {
            for (File file : new File(path).listFiles()) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }

    }
}
