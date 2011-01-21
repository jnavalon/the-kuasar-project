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
import java.io.IOException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Auth {
    private SocketTools st = null;
    public Auth(SocketTools st) throws Exception{
        this.st = st;
        authentication();
    }
    private void authentication() throws Exception {
        boolean state = false;
        short attempts = 0;
        int value = -1;
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
            }catch(IllegalStatement is){
                Info.showError(st.getRemoteAdress() + " :: " + "Client sent an incorrect answer at Authentication mode question. Rejected!");
                throw is;
            }
        } while (!state);

        switch (value) {
            case 0: //PLAIN TEXT
                checkUser();
                break;
            case 1: //CERTIFICATION - DNIE(SPAIN)
                checkCert();
                break;
            default:
                Info.showError(st.getRemoteAdress() + " :: " + "Client answered incorrectly to an Authentication mode question.");
                throw new UnknownAnswerException("Client returned an incorrect reply at setAuth question.");
        }
    }

    private boolean checkUser() throws Exception{
        st.sendLine(CHARS.QUESTION + "Login?");
        String value = st.readLine(CMD.ANSWER);
        System.out.println(extractUAP(value)[0]+ " --- " + extractUAP(value)[1]); //TEST... THIS LINE WILL BE REMOVE
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private String[] extractUAP(String value) throws Exception{
        String[] aux = {"",""};
        short i =0;
        for(char Char : value.toCharArray()){
            if(Char == 43){ //Change to 0
                i++;
            }else{
             aux[i] += (String.valueOf(Char));
            }
        }
        return aux;
    }
    private boolean checkCert() throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
