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

package blasar.console;

import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class KillCommands {
    
    protected static void kill(StringTokenizer st){
        if(!st.hasMoreElements()){
            System.out.println("Connection ID missing.\n\n kill <id>\t Exemple: kill 127.0.0.1:1234");
            return;
        }
        String token = st.nextToken().toLowerCase();
        if(InitConsole.server.killClient(token)){
            System.out.println(token + " was killed successfully!");
        }else{
            System.out.println("There was an ERROR!. Impossible to kill " + token);
        }
    }
    
    protected static void killAll(){
        System.out.println(InitConsole.server.killClient(null) ? "Successfully!": "There wasn't any connection!");
    }

}
