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

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class ShowCommands {
    protected static void Redirect(StringTokenizer st){
        if(!st.hasMoreTokens()){
            printHelp();
            return;
        }
        String token = st.nextToken().toLowerCase();
        if(token.equals("connections"))
            printConnections();
        if(token.equals("interfaces"))
            printInterfaces();
        else if(token.equals("?"))
                printHelp();
        else
            printHelp();
    }

    private static void printHelp(){
        System.out.println("show <CMD>\t");
        System.out.println("\t\tconnections\tShow Hosts connected to Server");
        System.out.println("\t\tinterfaces \tShow IP Addresses assigned to all network interfaces");
    }

    private static void printInterfaces(){
        ArrayList<String[]> interfaces = InitConsole.server.getInterfaces();
        System.out.println();
        for(String[] locint : interfaces){
            System.out.println(locint[0] + ":");
            String[] adresses = locint[1].split(" ");
            for(String address : adresses ){
                if(!address.isEmpty())
                    System.out.println("\t" + address.trim());
            }
        }
        System.out.println();
    }
    private static void printConnections(){
        ArrayList<String> connections = InitConsole.server.getConnections();
        for(String connection : connections){
            System.out.println(connection);
        }
        if(connections.isEmpty())
            System.out.println(">> Not connections <<");
        System.out.println("Total Connections: " + connections.size());
    }

}
