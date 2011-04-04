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
package kuasar.plugin.netcreator.utils;

import java.net.InetAddress;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class IP {

    public static boolean checkIP(InetAddress ip, byte[] netmask) {
        if (ip == null || netmask == null) {
            return false;
        }
        isNetAdd(ip, netmask);
        return true;
    }

    public static boolean isLoopBack(InetAddress ip) {
        return ip.isLoopbackAddress();
    }

    public static boolean isMulticast(InetAddress ip) {
        return ip.isMulticastAddress();
    }

    public static boolean isNetAdd(InetAddress ip, byte[] netmask) {
        byte[] ipblocks = ip.getAddress();
        int result;
        for (int i = 0; i < ipblocks.length; i++) {
            result = ipblocks[i] & netmask[i];
            if (result != ipblocks[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkIP4Block(int value) {
        if (value > 255 || value < 0) {
            return false;
        }
        return true;
    }

    public static boolean checkIP4Block(String value) {
        if(value.trim().isEmpty()) return false;
        try {
            int oct = Integer.parseInt(value);
            return checkIP4Block(oct);
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean checkIP4Mask(int value) {
        if (value > 32 || value < 0) {
            return false;
        }
        return true;
    }

    public static boolean checkIP4Mask(String value) {
        if(value.trim().isEmpty()) return false;
        try {
            int oct = Integer.parseInt(value);
            return checkIP4Mask(oct);
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public static boolean checkIP6Mask(int value) {
        if (value > 128 || value < 0) {
            return false;
        }
        return true;
    }

    public static boolean checkIP6Mask(String value) {
        if(value.trim().isEmpty()) return false;
        try {
            int oct = Integer.parseInt(value);
            return checkIP6Mask(oct);
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public static byte[] getMask4(int n) {
        return getMask(n,4);
    }

    public static byte[] getMask6(int n) {
        return getMask(n,6);
    }

    private static byte[] getMask(int n, int type){
        byte[] mask = new byte[type == 4 ? 4 : 16];
        int i = 0;
        int e = 8;

        for (; i < ((int) (n / 8)); i++) {
            mask[i] = (byte) 255;
        }

        if (i < mask.length) {
            n -= (8 * i);
            mask[i] = 0;
            while (n > 0) {
                mask[i] += Math.pow(2, e - 1);
                e--;
                n--;
            }
            i++;
            for (; i < mask.length; i++) {
                mask[i] = 0;
            }
        }
        return mask;
    }
}
