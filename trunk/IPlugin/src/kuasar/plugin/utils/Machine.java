/*
 * Copyright (C) 2011 Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kuasar.plugin.utils;

import java.io.IOException;
import java.util.ArrayList;
import kuasar.plugin.utils.Connection.CMD;
import kuasar.plugin.utils.SSocketTools.IllegalStatement;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Machine {

    private final static short PW_START = 0;
    private final static short PW_RESET = 1;
    private final static short PW_POWEROFF = 2;
    private final static short PW_ACPIOFF = 3;

    private static boolean sendPower(SSocketTools st, String uuid, short type) {
        String cmd = "startvm ";
        switch (type) {
            case PW_RESET:
                cmd = "resetvm ";
                break;
            case PW_POWEROFF:
                cmd = "poweroffvm ";
                break;
            case PW_ACPIOFF:
                cmd = "acpioffvm ";
                break;
        }
        try {
            st.sendLine(CMD.CHARS.QUESTION + cmd + uuid);
            String answer = st.readLine(CMD.INFO);
            if (answer.equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            return false;
        } catch (IllegalStatement ex) {
            return false;
        }
    }

    public static boolean startMachine(SSocketTools st, String uuid) {
        return sendPower(st, uuid, PW_START);
    }

    public static boolean resetMachine(SSocketTools st, String uuid) {
        return sendPower(st, uuid, PW_RESET);
    }

    public static boolean shutDownMachine(SSocketTools st, String uuid) {
        return sendPower(st, uuid, PW_ACPIOFF);
    }

    public static boolean powerOffMachine(SSocketTools st, String uuid) {
        return sendPower(st, uuid, PW_POWEROFF);
    }

    public static boolean deleteMachine(SSocketTools st, String uuid) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + "deletevm " + uuid);
            String answer = st.readLine(CMD.INFO);
            if (answer.equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            return false;
        } catch (IllegalStatement ex) {
            return false;
        }
    }
    
    public static String[] getMachines(SSocketTools st){
        ArrayList<String> vms = new ArrayList<String>();
         try {
            st.sendLine(CMD.CHARS.QUESTION + "getmachines");
            int total = st.readInt(CMD.INFO);
            for(int i = 0 ; i < total; i++){
                vms.add(st.readLine(CMD.ANSWER));
            }
            String[] avms = new String [vms.size()];
            vms.toArray(avms);
            return avms;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static String getMachineUUID(SSocketTools st, String name){
        try {
            st.sendLine(CMD.CHARS.QUESTION + "getmachineuuid \"" + name + "\"");
            return st.readLine(CMD.ANSWER);
        } catch (IOException ex) {
            return null;
        } catch (IllegalStatement ex) {
            return null;
        }
    }
    public static boolean isRunning(SSocketTools st, String uuid){
        try {
            st.sendLine(CMD.CHARS.QUESTION + "isrunning " + uuid);
            return st.readLine(CMD.ANSWER).equals("true");
        } catch (IOException ex) {
            return false;
        } catch (IllegalStatement ex) {
            return false;
        }
    }
}
