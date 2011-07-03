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
package kuasar.plugin.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class DNIe {

    private final static String SIGN_ALGORTIHM = "SHA256withRSA";
    private final static String LINUX_LIB = "name = DNIe\nlibrary = /usr/lib/opensc-pkcs11.so\n";
    private final static String MAC_LIB = "name = DNIe\nlibrary = /Library/OpenSC/lib/opensc-pkcs11.so\n";
    private final static String WIN_LIB = "name = DNIe\nlibrary = c:/WINDOWS/system32/UsrPkcs11.dll\n";
    private static KeyStore keyStore = null;
    private static String lib = null;
    private static char[] pin = null;
    private static List<TerminalChecker> checkersThreads = new ArrayList<TerminalChecker>();

    public static void setLib(String lib) {
        DNIe.lib = lib;

    }

    public static void setPIN(char[] pin) {
        DNIe.pin = pin;

    }

    public static boolean loadAutoChecker() {
        try {
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            for (CardTerminal terminal : terminals) {
                TerminalChecker dniec = new TerminalChecker(terminal);
                checkersThreads.add(dniec);
                dniec.start();
            }
        } catch (CardException ex) {
            return false;
        }
        return true;
    }

    public static void unloadAutoChecker() {
        for (TerminalChecker checker : checkersThreads) {
            checker.finish();
        }
    }
    
    private static String getLibrary() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("linux")) {
            return LINUX_LIB;
        } else if (osName.contains("mac")) {
            return MAC_LIB;
        } else if (osName.contains("windows")) {
            return WIN_LIB;
        }
        return null;
    }
    
    public static String getNIF(X509Certificate cert) {
        if (cert == null) {
            return null;
        }
        String data = cert.getSubjectDN().toString();
        String[] values = data.split(",");
        for (String value : values) {
            value = value.trim();
            if (value.startsWith("SERIALNUMBER")) {
                return value.split("=")[1];
            }
        }
        return null;
    }
    //************************************************************UNTESTED*********************************************************************
    
    public static boolean loadKeyStore() throws CardException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, ProviderException {
        keyStore = getKeyStore();
        keyStore.load(null, pin);
        return true;
    }

    public static X509Certificate getCertificate() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, CardException, ProviderException {
        if (keyStore == null) {
            if (!loadKeyStore()) {
                return null;
            }
        }
        return (X509Certificate) keyStore.getCertificate(getAlias());
    }

    private static String getAlias() throws KeyStoreException, CardException, IOException, NoSuchAlgorithmException, CertificateException, ProviderException {
        if (keyStore == null) {
            loadKeyStore();
        }
        Enumeration e = keyStore.aliases();
        String alias = null;
        boolean found = false;
        while (e.hasMoreElements() && !found) {
            alias = e.nextElement().toString();
            if (alias.compareTo("CertAutenticacion") == 0) {
                found = true;
            } else {
                found = false;
            }
        }
        if (!found) {
            return null;
        }
        return alias;
    }

    private static KeyStore getKeyStore() throws KeyStoreException, ProviderException {
        String cfg = lib == null ? getLibrary() : lib;
        if (cfg == null) {
            return null;
        }
        sun.security.pkcs11.SunPKCS11 sunpkcs11 = new sun.security.pkcs11.SunPKCS11(new ByteArrayInputStream(cfg.getBytes()));
        Security.addProvider(sunpkcs11);
        keyStore = KeyStore.getInstance("PKCS11", sunpkcs11);
        return keyStore;
    }

    

    private static PrivateKey getPrivateKey() throws CardException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        if (keyStore == null) {
            if (!loadKeyStore()) {
                return null;
            }
        }
        PrivateKey key = (PrivateKey) keyStore.getKey(getAlias(), pin);
        return key;
    }

    public static byte[] sign(String message) throws NoSuchAlgorithmException, CardException, InvalidKeyException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, SignatureException {
        Signature sig = Signature.getInstance(SIGN_ALGORTIHM);
        sig.initSign(getPrivateKey());
        sig.update(message.getBytes());
        return (sig.sign());
    }
}
