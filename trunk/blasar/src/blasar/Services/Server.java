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

package blasar.Services;

import blasar.Config;
import blasar.Info;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;


/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */

public class Server extends Thread {

    private ServerSocket ssocket= null;
    private boolean next = true;
    private HashMap<Long,Splitter> connections = new HashMap<Long,Splitter>();

    @Override
    public void run() {

        try {
            
            SSLServerSocketFactory socket = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ssocket = (SSLServerSocket) socket.createServerSocket(Config.port);

            Info.showMessage("blasar is listening on " + ssocket.getLocalPort() + " port.");
            while (true) {
                Socket connection = waitClient();
                if (connection == null) {
                    if (!next) {
                        Iterator keys = connections.keySet().iterator();
                        while(keys.hasNext()){
                            ((Splitter) connections.get((Long)keys.next())).cleanStop();
                        }
                        ssocket.close();
                        Info.showError("Server disconnected!");
                        break;
                    }
                } else {
                    System.out.println(connection.getInetAddress().getHostAddress() + "  (" + connection.getPort() + ")" + " ==> " + connection.getLocalAddress().getHostAddress() + "  (" + connection.getLocalPort() + ")");
                    Splitter arrived = new Splitter(connection);
                    arrived.start();
                    connections.put(arrived.getId(),arrived);
                }
            }
        } catch (IOException ex) {
            Info.showError("Error on connection: " + ex.getMessage());
        }
    }

    public void Stop() {
        next = false;
    }

    private Socket waitClient() throws IOException {
        try {
            
            Socket aux = ssocket.accept();
            Config.online++;
            if(Config.online>Config.max_users){
                aux.close();
                Config.online--;
                Info.showError("Max User reached! user: " + aux.getInetAddress().getHostAddress() + " (" + aux.getPort() + ")") ;
                return null;
            }
            return aux;
       
        } catch (SocketTimeoutException ste) {
            return null;
        }

    }
}
