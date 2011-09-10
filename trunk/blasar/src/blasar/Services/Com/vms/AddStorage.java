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
package blasar.Services.Com.vms;

import blasar.Config;
import blasar.Services.Exceptions.IllegalStatement;
import blasar.Services.SocketTools;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class AddStorage {
    String uuid,type,controller;
    boolean cache;
    VMCommands intercom;
    
    AddStorage(String uuid, String type, String controller, boolean cache, VMCommands intercom){
        this.uuid=uuid;
        this.type = type;
        this.controller = controller;
        this.cache = cache;
        this.intercom = intercom;
        
    }
    
    public boolean addStorageCtl(){
        return intercom.storageCtl(uuid, type, controller, cache);
    }
    
    public void addImages(SocketTools st) throws SocketException, IOException, IllegalStatement{
        st.sendLine(Config.CMD.CHARS.QUESTION + "addimage?");
        String cmd = st.readLine(Config.CMD.ANSWER);
        if(!cmd.equals("y")){
            st.sendLine(Config.CMD.CHARS.INFO + "END");
            return;
        }
        do{
            st.sendLine(Config.CMD.CHARS.QUESTION + "data?");
            saveImage(new StringTokenizer(st.readLine(Config.CMD.ANSWER)), st);
            st.sendLine(Config.CMD.CHARS.QUESTION + "addimage?");
            cmd = st.readLine(Config.CMD.ANSWER);
        }while(cmd.equals("y"));
        st.sendLine(Config.CMD.CHARS.INFO + "END");
        
    }
    private void saveImage(StringTokenizer cmd, SocketTools st) throws SocketException, IOException {
        if (cmd.countTokens() != 7) {
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        String filename = st.getFullArguments(cmd);
        String extension = filename.substring(filename.lastIndexOf('.'));
        filename = filename.substring(0, filename.lastIndexOf('.'));
        String imgType = cmd.nextToken();
        int port = Integer.parseInt(cmd.nextToken());
        int device = Integer.parseInt(cmd.nextToken());
        boolean passthrough = Boolean.getBoolean(cmd.nextToken());
        
        String sha1 = cmd.nextToken();

        long total, size;
        total = intercom.getFreeSpace();
        if (total == -1) {
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
            return;
        }
        try {
            size = Long.parseLong(cmd.nextToken());
        } catch (NumberFormatException ex) {
            st.sendLine(Config.CMD.CHARS.INFO + "BADARGS");
            return;
        }
        if (total < size) {
            st.sendLine(Config.CMD.CHARS.INFO + "NOSPACE");
            return;
        }
        String path = intercom.getImagePath();
        File file = new File(path + File.separator + uuid);
        if (!file.exists()) {
            if (!file.mkdir()) {
                st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
                return;
            }
        }
        File outputFile = new File(file.getAbsolutePath() + File.separator + filename + extension);
        int i = 0;
        while (outputFile.exists()) {
            outputFile = new File(file.getAbsolutePath() + File.separator + filename + "_" + i++ + extension);
        }

        long tsize = 0;
        BufferedOutputStream bos=null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(outputFile));
            while (tsize < size) {
                byte[] buffer = new byte[(size - tsize) > 1024 ? 1024 : (int) (size - tsize)];
                buffer = st.readBytes(buffer.length);
                bos.write(buffer);
                tsize += buffer.length;
            }
            bos.close();
        } catch (FileNotFoundException ex) {
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
            if(bos !=null)
                bos.close();
            if(outputFile.exists())
                if(!outputFile.delete()) outputFile.deleteOnExit();
            return;
        }
        String hash = getSHA1(outputFile);
        if(hash!=null){
            if(hash.equals(sha1)){
                st.sendLine(Config.CMD.CHARS.ANSWER + outputFile.getName());
            }else{
                st.sendLine(Config.CMD.CHARS.INFO + "BADHASH");
            }
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
        if(intercom.storageattach(uuid, controller.toUpperCase() + " Module", port, device, imgType, outputFile.getAbsolutePath(), passthrough)){
            st.sendLine(Config.CMD.CHARS.INFO + "OK");
        }else{
            st.sendLine(Config.CMD.CHARS.INFO + "ERROR");
        }
        
    }
        private String getSHA1(File outputFile) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(outputFile));
            byte[] digest;
            int data;
            String hash = "";
            while ((data = bis.read()) >= 0) {
                md.update((byte) data);
            }
            digest = md.digest();
            for (byte aux : digest) {
                int b = aux & 0xff;
                if (Integer.toHexString(b).length() == 1) {
                    hash += "0";
                }
                hash += Integer.toHexString(b);
            }
            return hash;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
}
