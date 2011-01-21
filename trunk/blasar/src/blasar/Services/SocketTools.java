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
import blasar.Config.CMD;
import blasar.Config.CMD.CHARS;
import blasar.Info;
import blasar.Services.Exceptions.IllegalStatement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class SocketTools {

    private Socket socket;
    private PrintStream out;
    private BufferedReader in;
    private SSLSession session = null;
    private Certificate[] certificate = null;

    public SocketTools(Socket socket) throws Exception {
        this.socket = socket;
        getStreams();
    }

    public void isAlive() throws SocketException {
        //IF SOCKET ISN'T CONNECTED TO CLIENT RETURN AN SOCKET EXCEPTION OTHERWISE RUN CLEAN
        socket.setKeepAlive(false);
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeAll() {
        try {
            out.close();
        } catch (Exception ex) {
        }
        try {
            in.close();
        } catch (IOException ex) {
        }
        try {
            socket.close();
        } catch (IOException ex) {
        }
        Config.online--;
    }

    private boolean getStreams() throws Exception {
        try {
            session = ((SSLSocket) socket).getSession();
            certificate = session.getLocalCertificates();

            if (Config.verbose) {
                System.out.println("=============  INFO  =============");
                for (int i = 0; i < certificate.length; i++) {

                    System.out.println(((X509Certificate) certificate[i]).getSubjectDN());
                }
                System.out.println("Peer host is " + session.getPeerHost());
                System.out.println("Cipher is " + session.getCipherSuite());
                System.out.println("Protocol is " + session.getProtocol());
                System.out.println("ID is " + new BigInteger(session.getId()));
                System.out.println("Session created in " + session.getCreationTime());
                System.out.println("Session accessed in " + session.getLastAccessedTime());

                System.out.println("==================================");
            }
            out = new PrintStream(socket.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException ex) {
            if (Config.verbose) {
                if (ex.getMessage() == null) {
                    Info.showError(socket.getInetAddress().toString().substring(1) + ": Impossible get socket streams.");
                } else {
                    Info.showError(socket.getInetAddress().toString().substring(1) + " : Impossible get socket streams: \n" + ex.getMessage());
                }
            }
            throw ex;
        } catch (Exception ex) {
            Info.showError("Unknown Error");
            throw ex;
        }
    }

    public String readLine() throws Exception {
        return in.readLine();
    }

    public String readLine(short commandFilter) throws Exception {
        String value = readLine();
        if (whatCommand(value) != commandFilter) {
            throw new IllegalStatement("Command not agree with expectation");
        }
        return value.substring(1);
    }

    public void sendLine(String msg) throws SocketException {
        isAlive();
        out.println(msg);
        out.flush();

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

    public void Send(byte[] msg) throws Exception {

        isAlive();
        out.write(msg);
        out.flush();

    }

    public void Send(String msg) throws Exception {
        Send(transcribeUTF(msg));
    }

    private byte[] transcribeUTF(String msg) {
        try {
            return msg.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return msg.getBytes();
        }
    }

    public String getRemoteIP() {
        return socket.getInetAddress().getHostAddress();
    }

    public String getLocalIP() {
        return socket.getLocalAddress().getHostAddress();
    }

    public String getRemotePort() {
        return Integer.toString(socket.getPort());
    }

    public String getLocalPort() {
        return Integer.toString(socket.getLocalPort());
    }

    public String getRemoteAdress() {
        return (getRemoteIP() + " (" + getRemotePort() + ")");
    }

    public String getLocalAddress() {
        return (getLocalIP() + " (" + getLocalPort() + ")");
    }

    public String getSessionID() {
        return new BigInteger(session.getId()).toString();
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
}
