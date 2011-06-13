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
package blasar.util;

import blasar.Config;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class DNIe {

    private static final byte[] DNIe_ATR = {
        (byte) 0x3B, (byte) 0x7F, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6A, (byte) 0x44,
        (byte) 0x4E, (byte) 0x49, (byte) 0x65, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x00
    };

    private static final byte[] DNIe_MASK = {
        (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF
    };

    private final static String SIGN_ALGORTIHM = "SHA256withRSA";
    private final static String PKEY_ALGORTIHM = "RSA";
    private final static String LINUX_LIB = "name = DNIe\nlibrary = /usr/lib/opensc-pkcs11.so\n";
    private final static String MAC_LIB = "name = DNIe\nlibrary = /Library/OpenSC/lib/opensc-pkcs11.so\n";
    private final static String WIN_LIB = "name = DNIe\nlibrary = c:/WINDOWS/system32/UsrPkcs11.dll\n";

    public static PublicKey getPublicKey(String NIF) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException  {
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.PublicKeyDIR + NIF + ".pk");
        if(!file.exists()) return null;
        FileInputStream fis = new FileInputStream(file);
        byte[] ePublicKey = new byte[(int) file.length()];
        fis.read(ePublicKey);
        fis.close();
        KeyFactory kf = KeyFactory.getInstance(PKEY_ALGORTIHM);
        X509EncodedKeySpec X509Key = new X509EncodedKeySpec(ePublicKey);
        PublicKey pk = kf.generatePublic(X509Key);
        return pk;
    }

    public static boolean savePublicKey(PublicKey pk, String name) {
        FileOutputStream fos = null;
        if(!checkPKDir()) return false;
        try {
            X509EncodedKeySpec X509Key = new X509EncodedKeySpec(pk.getEncoded());
            fos = new FileOutputStream(Config.BLASAR.startDir + Config.BLASAR.PublicKeyDIR + name + Config.BLASAR.PublicKeyEXT);
            fos.write(X509Key.getEncoded());
            return true;
        } catch (IOException ex) {
            //LOG
        } finally {
            try {
                fos.close();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    public static boolean removePublicKey(String name){
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.PublicKeyDIR + name + Config.BLASAR.PublicKeyEXT);
        return file.delete();
    }
    private static  boolean checkPKDir(){
        File file = new File(Config.BLASAR.startDir + Config.BLASAR.PublicKeyDIR);
        if(!file.exists()){
            try{
                return file.mkdir();
            }catch(Exception ex){}
        }
        return true;
    }
    
    public static String getTicket() {
        long timestamp = new Date().getTime();
        SecureRandom rand = new SecureRandom();
        String token =new BigInteger(256, rand).toString(32);
        return timestamp + token;
    }

    public static  boolean validate(Certificate cert, byte[] ticket, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return validate(ticket, sign, cert.getPublicKey());
    }

    public static boolean validate(byte[] ticket, byte[] sign, PublicKey pk) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature vsign = Signature.getInstance(SIGN_ALGORTIHM);
        vsign.initVerify(pk);
        vsign.update(ticket);
        return vsign.verify(sign);
    }

    public static String getNIF(X509Certificate cert){
        String data = cert.getSubjectDN().toString();
        String[] values = data.split(",");
        for(String value : values){
            value = value.trim();
            if(value.startsWith("SERIALNUMBER")){
                return value.split("=")[1];
            }
        }
        return null;
    }

    public static  X509Certificate extractCert(String lib, char[] pin) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, CardException {
        if(!connect()) return null;
        String cfg = lib==null ? getLibrary() : lib;
        if(cfg==null) return null;
        sun.security.pkcs11.SunPKCS11 sunpkcs11 = new sun.security.pkcs11.SunPKCS11(new ByteArrayInputStream(cfg.getBytes()));
        Security.addProvider(sunpkcs11);
        KeyStore keyStore = KeyStore.getInstance("PKCS11", sunpkcs11);
        keyStore.load(null,pin);
        Enumeration e = keyStore.aliases();
        String name = null;
        boolean found = false;
        while(e.hasMoreElements() && !found){
            name = e.nextElement().toString();
            if(name.compareTo("CertAutenticacion")==0)
                found = true;
            else
                found = false;
        }
        if(!found) return null;
        return (X509Certificate) keyStore.getCertificate(name);
    }

    private static String getLibrary(){
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("linux")) return LINUX_LIB;
        else if(osName.contains("mac")) return MAC_LIB;
        else if(osName.contains("windows")) return WIN_LIB;
        return null;
    }
    
    private static boolean connect() throws CardException {
        Card card = null;
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        for (CardTerminal terminal : terminals) {
            if (terminal.isCardPresent()) {
                card = terminal.connect("T=0");
                if (isDNIe(card)) {
                    card.disconnect(true);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isDNIe(Card card) {
        byte[] atr =card.getATR().getBytes();
        if(atr.length != DNIe_ATR.length) return false;
        for(int i=0; i<DNIe_ATR.length;i++){
            if((atr[i] & DNIe_MASK[i]) != (DNIe_ATR[i] & DNIe_MASK[i])){
                return false;
            }
        }
        return true;
    }

}
