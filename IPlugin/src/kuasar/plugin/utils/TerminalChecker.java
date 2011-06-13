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

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */

/*
 * Avoid SmartCard can be locked on some cards drivers for Unix Systems. 
 */
public class TerminalChecker extends Thread {

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
    boolean stop = false;
    private CardTerminal terminal = null;

    public TerminalChecker(CardTerminal terminal) {
        this.terminal = terminal;
    }
    
    @Override
    public void run() {
        Card card = null;
        if (terminal == null) {
            return;
        }
        try {
            while (!stop) {
                while (!terminal.waitForCardPresent(10000) && !stop) {
                }
                System.out.println("Card found on terminal " + terminal.getName());
                if (stop) return;
                if (terminal.isCardPresent()) {
                    card = terminal.connect("T=0");
                    if (isDNIe(card)) {
                        card.disconnect(true);
                    }
                }
                while (!terminal.waitForCardAbsent(10000) && !stop) {
                }
                System.out.println("Card extracted from terminal " + terminal.getName());
            }

        } catch (CardException ex) {
        }

    }

    private boolean isDNIe(Card card) {
        byte[] atr = card.getATR().getBytes();
        if (atr.length != DNIe_ATR.length) {
            return false;
        }
        for (int i = 0; i < DNIe_ATR.length; i++) {
            if ((atr[i] & DNIe_MASK[i]) != (DNIe_ATR[i] & DNIe_MASK[i])) {
                return false;
            }
        }
        return true;
    }

    public void finish(){
        stop=true;
    }
    
    public CardTerminal getTerminal(){
        return terminal;
    }

    public boolean isCardPresent(){
        try {
            return terminal.isCardPresent();
        } catch (CardException ex) {
            return false;
        }
    }

    public String getTerminalName(){
        return terminal.getName();
    }
    
}
