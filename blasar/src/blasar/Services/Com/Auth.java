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
package blasar.Services.Com;

import blasar.Services.Exceptions.UnknownAnswerException;
import blasar.Services.SocketTools;
import blasar.Info;
import blasar.Config.CMD;
import blasar.Config.CMD.CHARS;
import blasar.Services.Exceptions.IllegalStatement;
import blasar.util.DNIe;
import blasar.util.UserLogin;
import java.io.IOException;
import java.security.PublicKey;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Auth {

    private SocketTools st = null;
    private boolean logged = false;
    private static final String AUTH_FAIL = "BADLOGIN";
    private static final String AUTH_ERROR = "ERRORLOGIN";
    public Auth(SocketTools st) throws Exception {
        this.st = st;
        authentication();
    }

    private void authentication() throws Exception {
        boolean state = false;
        short attempts = 0;
        int value = -1;
        st.setUser(null);
        st.sendLine(CHARS.QUESTION + "setAUTH");

        do {
            try {
                value = st.readInt(CMD.ANSWER);
                state = true;
            } catch (IOException ex) {
                attempts++;
                Info.showMessage(st.getRemoteAdress() + " :: " + "Client TimeOUT!" + "\t" + attempts + " " + "attempt(s)");
                if (attempts == 3) {
                    Info.showError(st.getRemoteAdress() + " :: " + "Client TimeOUT at setAuth command. Rejected!");
                    throw ex;
                }
            } catch (IllegalStatement is) {
                Info.showError(st.getRemoteAdress() + " :: " + "Client sent an incorrect answer at Authentication mode question. Rejected!");
                throw is;
            }
        } while (!state);
        switch (value) {
            case 0: //CLEAN EXIT
                st.sendLine("See you!");
                st.closeAll();
                Info.showMessage(st.getRemoteAdress() + " made a clear exit");
                break;
            case 1: //PLAIN TEXT
                if (!checkUser()) {
                    throw new Exception();
                }
                break;
            case 2: //CERTIFICATION - DNIE(SPAIN)
                checkCert();
                break;
            default:
                Info.showError(st.getRemoteAdress() + " :: " + "Client answered incorrectly to an Authentication mode question.");
                throw new UnknownAnswerException("Client returned an incorrect reply at setAuth question.");
        }
    }

    private boolean checkUser() throws Exception {
        st.sendLine(CHARS.QUESTION + "Username?");
        String user = st.readLine(CMD.ANSWER).trim();
        if (user.isEmpty()) {
            st.sendLine(CHARS.INFO + Auth.AUTH_FAIL);
            return false;
        }
        st.sendLine(CHARS.QUESTION + "Passwd?");
        String passwd = st.readLine(CMD.ANSWER).trim();
        if (!UserLogin.checkUser(user, passwd)) {
            st.Send(CHARS.INFO + "User or Password don't match\n");
            return false;
        }
        st.setUser(user);
        logged = true;
        return true;
    }

    private boolean checkCert() throws Exception {
        String ticket = DNIe.getTicket();
        st.getSocket().setSoTimeout(120000);
        st.sendLine(CHARS.QUESTION + ticket);
        String nif = st.readLine(CMD.ANSWER).trim();
        int length = st.readInt(CMD.ANSWER);
        byte[] sign = st.readBytes(length);
        if (sign == null) {
            st.sendLine(CHARS.INFO+Auth.AUTH_ERROR);
            //LOG ERROR -- USER PUBLIC KEY FILE NOT FOUND
            return false;
        }
        PublicKey pk = DNIe.getPublicKey(nif);
        if (pk == null) {
            st.sendLine(CHARS.INFO+Auth.AUTH_ERROR);
            return false;
        }
        if (DNIe.validate(ticket.getBytes(), sign, pk)) {
            String user = UserLogin.checkNIF(nif);
            if (user == null) {
                st.sendLine(CHARS.INFO+Auth.AUTH_ERROR);
                return false;
            }
            st.setUser(user);
            logged = true;
        } else {
            st.sendLine(CHARS.INFO+Auth.AUTH_FAIL);
            logged = false;
            Info.showMessage("NIF: " + nif + " Bad Login");
        }
        return logged;
    }

    public boolean isLogged() {
        return logged;
    }
}
