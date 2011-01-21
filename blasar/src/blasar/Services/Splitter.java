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
import blasar.Services.Com.Auth;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Splitter extends Thread {

    private boolean next = true;
    private boolean cleanStop = true;
    private SocketTools st = null;

    public Splitter(Socket socket) {
        try {
            socket.setSoTimeout(10000);
        } catch (SocketException ex) {
            Info.showError(ex.getMessage());
            return;
        }
        try{
           st = new SocketTools(socket); 
        }catch(Exception ex){
            return;
        }
        
    }

    @Override
    public void run() {
        init();
        st.closeAll();
    }

    private void init() {
        try {
            startComunication();
            Auth auth = new Auth(st);
        } catch (Exception ex) {
            st.closeAll();
        }
        Info.showMessage("X_X :: Client " + st.getRemoteIP() + " (" + st.getRemotePort() + ") " + "has been disconnected." );
    }

    public Socket getSocket() {
        return st.getSocket();
    }

    public void Stop() {
        cleanStop = false;
        next = false;
    }

    public void cleanStop() {
        cleanStop = true;
        next = false;
    }

    private void startComunication() throws Exception {

        st.Send("Wellcome to Blasar (" + Config.version + ") \nThe kuasar project's daemon.\n\n");

    }

    
}
