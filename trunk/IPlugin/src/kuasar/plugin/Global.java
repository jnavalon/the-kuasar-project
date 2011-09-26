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

package kuasar.plugin;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Global {
        public static Object mainClassInstance = null;
        public static Class mainClass = null;
        public static Class ODRClass = null;
        public static Object ODRClassInstance = null;
        public static boolean DevMode = false;
        
        /*public final class CMD {

        public final static short NULL = 0;
        public final static short ANSWER = 1;
        public final static short QUESTION = 2;
        public final static short INFO = 3;

        public final class CHARS {

            public final static char ANSWER = '<';
            public final static char QUESTION = '>';
            public final static char INFO = '#';
        }
    }*/
}
