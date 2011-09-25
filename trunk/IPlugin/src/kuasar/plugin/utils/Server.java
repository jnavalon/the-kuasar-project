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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kuasar.plugin.utils.Connection.CMD;
import kuasar.plugin.utils.SSocketTools.IllegalStatement;
import kuasar.plugin.utils.SSocketTools.InitSocketException;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class Server {

    public static boolean startConnection(SSocketTools st) {
        boolean ok;
        try {
            ok = st.login();
        } catch (InitSocketException ex) {
            st.closeAll();
            return false;
        }
        if (!ok) {
            return false;
        }
        return true;
    }

    public static boolean enterHVMode(SSocketTools st, String hv) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + "switchvm " + hv);
            if (st.readLine(CMD.INFO).equals("OK")) {
                return true;
            } else {
                return false;
            }

        } catch (IOException ex) {
            st.closeAll();
            return false;
        } catch (IllegalStatement ex) {
            st.closeAll();
            return false;
        }
    }

    public static boolean exitHVMode(SSocketTools st) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + "exit");
            if (!st.readLine(CMD.INFO).equals("OUTVM")) {
                st.closeAll();
                return false;
            }
        } catch (IOException ex) {
            st.closeAll();
            return false;
        } catch (IllegalStatement ex) {
            st.closeAll();
            return false;
        }
        return true;
    }

    public static boolean closeServer(SSocketTools st) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + "exit");
            if (!st.readLine(CMD.INFO).equals("BYE")) {
                st.closeAll();
                return false;
            }
        } catch (IOException ex) {
            st.closeAll();
            return false;
        } catch (IllegalStatement ex) {
            st.closeAll();
            return false;
        }
        return true;
    }

    public static ArrayList<String> getHypervisors(SSocketTools st) {
        ArrayList<String> hvs = new ArrayList<String>();
        try {
            st.sendLine(CMD.CHARS.QUESTION + "listhvs");
            int total = st.readInt(CMD.INFO);
            for (int i = 0; i < total; i++) {
                hvs.add(st.readLine(CMD.ANSWER));
            }
        } catch (Exception ex) {
            return null;
        }
        return hvs;
    }

    public static int getPercentMem(SSocketTools st) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + "$mem:get_used");
            long used = st.readLong(CMD.ANSWER);
            st.sendLine(CMD.CHARS.QUESTION + "$mem:get_total");
            long total = st.readLong(CMD.ANSWER);
            int percent = (int) (used * 100 / total);
            return percent;
        } catch (Exception ex) {
            return -1;
        }
    }

    public static int getPercentDisk(SSocketTools st) {
        try {
            HashMap<String, long[]> devices = new HashMap<String, long[]>();
            st.sendLine(CMD.CHARS.QUESTION + "$fs:get_fs_size");
            int size = st.readInt(CMD.ANSWER);
            for (int i = 0; i < size; i++) {
                st.sendLine(CMD.CHARS.QUESTION + "$fs:get_total " + i);
                long total = st.readLong(CMD.ANSWER);
                st.sendLine(CMD.CHARS.QUESTION + "$fs:get_used " + i);
                long used = st.readLong(CMD.ANSWER);
                st.sendLine(CMD.CHARS.QUESTION + "$fs:get_dir_name " + i);
                String device = st.readLine(CMD.ANSWER);
                devices.put(device, new long[]{total, used});
            }
            Iterator<String> it = devices.keySet().iterator();
            long total = 0;
            long used = 0;
            while (it.hasNext()) {
                long[] data = devices.get(it.next());
                total += data[0];
                used += data[1];
            }
            return (int) (used * 100 / total);
        } catch (Exception ex) {
            return -1;
        }
    }

    public static String getOSName(SSocketTools st) {
        return getString(st, "$os:get_name");
    }

    public static String getOSArch(SSocketTools st) {
        return getString(st, "$os:get_arch");
    }

    public static String getOSVersion(SSocketTools st) {
        return getString(st, "$os:get_version");
    }

    public static String getOSPatchLvl(SSocketTools st) {
        return getString(st, "$os:get_patch_lvl");
    }

    public static String getOSVendor(SSocketTools st) {
        return getString(st, "$os:get_vendor");
    }

    public static String getOSVendorCN(SSocketTools st) {
        return getString(st, "$os:get_vendor_cn");
    }

    public static String getOSUptime(SSocketTools st) {
        String date = getString(st, "$os:get_fuptime");
        if (date == null) {
            return null;
        }
        String[] format = date.split("#");
        return format[0] + " Days, " + format[1] + " Hour(s) " + format[2] + " Minutes.";
    }

    public static int getCPUInfoSize(SSocketTools st) {
        return getInteger(st, "$cpu:get_info_size");
    }

    public static int getCPUSize(SSocketTools st) {
        return getInteger(st, "$cpu:get_cpu_size");
    }

    public static String getCPULoad(SSocketTools st, int arg) {
        return getString(st, "$cpu:get_total_cpu_load " + arg);
    }

    public static String getCPUVendor(SSocketTools st, int arg) {
        return getString(st, "$cpu:get_vendor " + arg);
    }

    public static String getCPUModel(SSocketTools st, int arg) {
        return getString(st, "$cpu:get_model " + arg);
    }

    public static String getCPUMhz(SSocketTools st, int arg) {
        return getString(st, "$cpu:get_mhz " + arg);
    }

    public static String getCPUCores(SSocketTools st, int arg) {
        return getString(st, "$cpu:get_cores_size " + arg);
    }

    public static String getCPUCache(SSocketTools st, int arg) {
        return getString(st, "$cpu:get_cache_size " + arg);
    }

    public static String getMEMTotal(SSocketTools st) {
        return formatSize(getLong(st, "$mem:get_total"), 0);
    }

    public static String getMEMUsed(SSocketTools st) {
        return formatSize(getLong(st, "$mem:get_used"), 0);
    }

    public static String getMEMFree(SSocketTools st) {
        return formatSize(getLong(st, "$mem:get_free"), 0);
    }

    public static String getMEMTotalSwap(SSocketTools st) {
        return formatSize(getLong(st, "$mem:get_total_swap"), 0);
    }

    public static String getMEMUsedSwap(SSocketTools st) {
        return formatSize(getLong(st, "$mem:get_used_swap"), 0);
    }

    public static String getMEMFreeSwap(SSocketTools st) {
        return formatSize(getLong(st, "$mem:get_free_swap"), 0);
    }

    public static int getFSSize(SSocketTools st) {
        return getInteger(st, "$fs:get_fs_size");
    }

    public static int getFSPercentUsed(SSocketTools st, int arg) {
        String answer = getString(st, "$fs:get_used_percent " + arg);
        try {
            float percent = Float.parseFloat(answer) * 100;
            return (int) percent;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static String getFSType(SSocketTools st, int arg) {
        return getString(st, "$fs:get_type " + arg);
    }

    public static String getFSPath(SSocketTools st, int arg) {
        return getString(st, "$fs:get_dir_name " + arg);
    }

    public static String getFSTotal(SSocketTools st, int arg) {
        return formatSize(getLong(st, "$fs:get_total " + arg), 1);
    }

    public static String getFSFree(SSocketTools st, int arg) {
        return formatSize(getLong(st, "$fs:get_available " + arg), 1);
    }

    public static String getFSUsed(SSocketTools st, int arg) {
        return formatSize(getLong(st, "$fs:get_used " + arg), 1);
    }

    private static String getString(SSocketTools st, String cmd) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + cmd);
            return st.readLine(CMD.ANSWER);
        } catch (Exception ex) {
            return null;
        }
    }

    private static int getInteger(SSocketTools st, String cmd) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + cmd);
            return Integer.parseInt(st.readLine(CMD.ANSWER));
        } catch (Exception ex) {
            return -1;
        }
    }

    private static long getLong(SSocketTools st, String cmd) {
        try {
            st.sendLine(CMD.CHARS.QUESTION + cmd);
            return Long.parseLong(st.readLine(CMD.ANSWER));
        } catch (Exception ex) {
            return -1;
        }
    }

    private static String formatSize(long size, int startLvl) {
        float aux = size;
        short level = (short) startLvl;

        while (aux > 1024) {
            aux /= 1024;
            level++;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(aux) + " " + getSizeType(level);

    }

    private static String getSizeType(short level) {
        switch (level) {
            case 0:
                return "B";
            case 1:
                return "KB";
            case 2:
                return "MB";
            case 3:
                return "GB";
            case 4:
                return "TB";
            case 5:
                return "PB";
            case 6:
                return "EB";
            case 7:
                return "ZB";
            case 8:
                return "YB";
            default:
                return "WTF!";
        }
    }
}
