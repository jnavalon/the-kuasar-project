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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Server extends Thread {

    private ServerSocket ssocket = null;
    private boolean next = true;
    private static HashMap<Long, Splitter> connections = new HashMap<Long, Splitter>();

    @Override
    public void run() {

        try {

            SSLServerSocketFactory socket = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ssocket = (SSLServerSocket) socket.createServerSocket(Config.BLASAR.port);
            Info.showMessage("blasar is listening on " + ssocket.getLocalPort() + " port.");

            while (true) {
                Socket connection = waitClient();
                if (connection == null) {
                    if (!next) {
                        Iterator keys = connections.keySet().iterator();
                        while (keys.hasNext()) {
                            ((Splitter) connections.get((Long) keys.next())).cleanStop();
                        }
                        ssocket.close();
                        Info.showError("Server disconnected!");
                        break;
                    }
                } else {
                    Info.showMessage(connection.getInetAddress().getHostAddress() + "  (" + connection.getPort() + ")" + " ==> " + connection.getLocalAddress().getHostAddress() + "  (" + connection.getLocalPort() + ")");
                    Splitter arrived = new Splitter(connection);
                    arrived.start();
                    connections.put(arrived.getId(), arrived);
                }
                clearConnections();
            }
        } catch (IOException ex) {
            Info.showError("Error on connection: " + ex.getMessage());
        }
    }

    public String getLocalPort(){
        return String.valueOf(ssocket.getLocalPort());
    }
    public ArrayList<String[]> getInterfaces(){
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            ArrayList<String[]> interfaces = new ArrayList<String[]>();
            while(e.hasMoreElements()){
                NetworkInterface ni= (NetworkInterface) e.nextElement();
                String[] locint = new String[2];
                locint[0] = ni.getName();
                Enumeration en = ni.getInetAddresses();
                locint[1] = "";
                while(en.hasMoreElements()){
                    InetAddress ip = (InetAddress) en.nextElement();
                    locint[1] += locint[1].isEmpty() ? ip.getHostAddress() : " " + ip.getHostAddress();
                    if(locint[1].contains("%"))locint[1] = locint[1].substring(0, locint[1].lastIndexOf("%"));
                }
                interfaces.add(locint);
                return interfaces;
            }
        } catch (SocketException ex) {
            System.out.println("Impossible to get local interfaces.");
        }
        return null;
    }

    private void clearConnections() {
        Iterator keys = connections.keySet().iterator();
        Long id;
        while (keys.hasNext()) {
            id = (Long) keys.next();
            if(((Splitter) connections.get(id)).isDead()){
                connections.remove(id);
                keys = connections.keySet().iterator();
            }
        }
    }

    public void Stop() {
        next = false;
    }

    private Socket waitClient() throws IOException {
        try {

            Socket aux = ssocket.accept();
            Config.BLASAR.online++;
            if (Config.BLASAR.online > Config.BLASAR.max_users) {
                aux.close();
                Config.BLASAR.online--;
                Info.showError("Max User reached! user: " + aux.getInetAddress().getHostAddress() + " (" + aux.getPort() + ")");
                return null;
            }
            return aux;

        } catch (SocketTimeoutException ste) {
            return null;
        }

    }

    public boolean killClient(String connection){
        boolean status = false;
        ArrayList<String> list = new ArrayList<String>();
        Iterator keys = connections.keySet().iterator();
        while (keys.hasNext()) {
            Splitter curSplitter = (Splitter) connections.get((Long) keys.next());
            SocketTools st = curSplitter.getSocketTools();
            try {
                st.isAlive();
                if(connection!=null){
                    if(connection.equals(st.getRemoteIP() + ":" + st.getRemotePort())){
                        try {
                            curSplitter.getSocket().close();
                        } catch (IOException ex) {
                            return false;
                        }
                        return true;
                    }
                }else{
                    try {
                        curSplitter.getSocket().close();
                    } catch (IOException ex) {
                        return false;
                    }
                    status = true;
                }
            } catch (SocketException ex) {}
        }
        return status;
    }

    public ArrayList<String> getConnections() {
        ArrayList<String> list = new ArrayList<String>();
        Iterator keys = connections.keySet().iterator();
        while (keys.hasNext()) {
            Splitter curSplitter = (Splitter) connections.get((Long) keys.next());
            SocketTools st = curSplitter.getSocketTools();
            try {
                st.isAlive();
                list.add(st.getRemoteIP() + ":" + st.getRemotePort());
            } catch (SocketException ex) {}
            
        }
        return list;
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> list = new ArrayList<String>();
        Iterator keys = connections.keySet().iterator();
        while (keys.hasNext()) {
            Splitter curSplitter = (Splitter) connections.get((Long) keys.next());
            SocketTools st = curSplitter.getSocketTools();
            try {
                st.isAlive();
                if(st.getUser()!=null)
                    list.add(st.getUser() + " on " +st.getRemoteIP() + ":" + st.getRemotePort());
            } catch (SocketException ex) {}

        }
        return list;
    }
}
