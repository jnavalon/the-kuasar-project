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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import javax.net.ssl.SSLSocket;
import kuasar.plugin.Global.CMD;
import kuasar.plugin.utils.Connection.CMD.CHARS;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class SSocketTools {
    public class InitSocketException extends Exception{}
    public class IllegalStatement extends Exception{
        IllegalStatement (String msg){
            super(msg);
        }
    }
    private SSLSocket ssocket = null;
    private PrintStream out = null;
    private BufferedReader in = null;
    private InputStream is = null;
    
    private String address;
    private int port;
    private String ks;
    private char[] kspwd;
    private String user;
    private char[] userpwd;
    
    public SSocketTools(String address, int port, String ks, char[] kspwd, String user, char[] passwd){
        this.address = address;
        this.port = port;
        this.ks = ks;
        this.kspwd = kspwd;
        this.user = user;
        this.userpwd = passwd;
    }
    public boolean initSocket(){
        ssocket = Connection.getServerSocket(address, port, ks, kspwd, Connection.timeout);
        try {
            out = new PrintStream(ssocket.getOutputStream());
            is = ssocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            in.readLine();
            in.readLine();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    public boolean login() throws InitSocketException{
        int status;
        if(ssocket==null || in == null || out == null){
            throw new InitSocketException();
        }
        if(user.isEmpty()){
            status = Connection.checkDNIe(in, out, kspwd);
        }else{
            status = Connection.checkUser(in, out, user, userpwd);
        }
        
        return status==1;
    }
    public SSLSocket getSSocket(){
        return ssocket;
    }
    public void isAlive() throws SocketException{
        //IF SOCKET ISN'T CONNECTED TO CLIENT RETURN AN SOCKET EXCEPTION OTHERWISE RUN CLEAN
        ssocket.setKeepAlive(false);
    }
    public void closeAll(){
        try {
            in.close();
            out.close();
            ssocket.close();
        } catch (IOException ex) {}
    }
    public PrintStream getPrintStream(){
        return out;
    }
    public BufferedReader getBufferedReader(){
        return in;
    }
    public String readLine() throws IOException{
        return in.readLine();
    }
    public String readLine(short commandFilter) throws IOException, IllegalStatement  {
        String value = readLine();
        if (whatCommand(value) != commandFilter) {
            throw new IllegalStatement("Command not agree with expectation");
        }
        return value.substring(1);
    }
    public short whatCommand(String data) {
        if (data == null) {
            return 0;
        }
        char ctc;
        if (data.length() == 0) {
            return 0;
        }
        ctc = data.charAt(0);
        switch (ctc) {
            case CHARS.ANSWER:
                return CMD.ANSWER ;
            case CHARS.QUESTION:
                return CMD.QUESTION;
            case CHARS.INFO:
                return CMD.INFO;
            default:
                return CMD.NULL;
        }

    }
    public void sendLine(String msg) throws SocketException {
        isAlive();
        out.println(msg);
        out.flush();
    }
    public void Send(byte[] msg) throws SocketException, IOException{
        isAlive();
        out.write(msg);
        out.flush();
    }
    public void Send(String msg) throws SocketException, IOException  {
        Send(transcribeUTF(msg));
    }
    public void Send(char command, String[] data) throws SocketException, IOException {
        sendLine(CMD.CHARS.INFO + Integer.toString(data.length));
        for(String str : data){
            sendLine(command + str);
        }
    }
    public void Send(char command, ArrayList<String> data) throws SocketException, IOException {
        sendLine(CMD.CHARS.INFO + Integer.toString(data.size()));
        for(String str : data){
            sendLine(command + str);
        }
    }  
    private byte[] transcribeUTF(String msg) {
        try {
            return msg.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return msg.getBytes();
        }
    }
    public String getRemoteIP(){
        return ssocket.getInetAddress().getHostAddress();
    }
    public String getLocalIP() {
        return ssocket.getLocalAddress().getHostAddress();
    }
    public String getRemotePort() {
        return Integer.toString(ssocket.getPort());
    }
    public String getLocalPort() {
        return Integer.toString(ssocket.getLocalPort());
    }
    public String getRemoteAdress() {
        return (getRemoteIP() + " (" + getRemotePort() + ")");
    }
    public String getLocalAddress() {
        return (getLocalIP() + " (" + getLocalPort() + ")");
    }
    public byte readByte() throws IOException{
        return readBytes(1)[0];
    }
    public byte[] readBytes(int length) throws IOException{
        byte[] buffer = new byte[length];
        is.read(buffer);
        return buffer;
    }
    public long readLong(short filterCommand) throws Exception {
        try {
            String value = readLine();
            if (whatCommand(value)!= filterCommand) {
                throw new IllegalStatement("This isn't a correct answer");
            }
            return Long.parseLong(value.substring(1));
        } catch (NumberFormatException ex) {
            return (long) -1;
        }
    }
    public Integer readInt(short filterCommand) throws Exception {

        try {
            String value = readLine();
            if (whatCommand(value)!= filterCommand) {
                throw new IllegalStatement("This isn't a correct answer");
            }
            return Integer.parseInt(value.substring(1));
        } catch (NumberFormatException ex) {
            return -1;
        }

    }
    public boolean setSocketTime(int timeout){
        try {
            ssocket.setSoTimeout(timeout);
        } catch (SocketException ex) {
            return false;
        }
        return true;
    }
}
