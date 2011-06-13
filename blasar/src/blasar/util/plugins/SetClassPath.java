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
package blasar.util.plugins;

import blasar.Info;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class SetClassPath {

    private static final String URL_METHOD = "addURL";
    private static final Class[] PARAM_METHOD= new Class[]{URL.class};
    private final URLClassLoader loader;
    private final Method methodAdd;

    public SetClassPath() throws NoSuchMethodException {
        loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        methodAdd = URLClassLoader.class.getDeclaredMethod(
               URL_METHOD, PARAM_METHOD);
        methodAdd.setAccessible(true);
    }

    public URL[] getURLs() {
        return loader.getURLs();
    }

    public void add(URL url) {
        if (url != null) {
            try {
                methodAdd.invoke(loader, new Object[]{url});
            } catch (Exception ex) {
                Info.showError(ex.getLocalizedMessage());
            }
        }
    }

    public void add(URL[] urls) {
        if (urls != null) {
            for (URL url : urls) {
                add(url);
            }
        }
    }

    public void add(File file) throws MalformedURLException {
        if (file != null) {
            add(file.toURI().toURL());
        }
    }

    public void add(String name) throws MalformedURLException {
        add(new File(name));
    }
}
