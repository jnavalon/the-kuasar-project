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

import blasar.Config.CMD;
import blasar.Services.SocketTools;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class SysCommands {

    public static boolean splitter(String cmd, StringTokenizer args, SocketTools st) throws SocketException {
        String[] data = cmd.split(":");
        String result = null;
        int arg = -1;
        if (data.length != 2) {
            return false;
        }
        if (args.hasMoreTokens()) {
            try {
                arg = Integer.parseInt(args.nextToken());
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        if (data[0].equals("os")) {
            result = SOsplitter(data[1]);
        } else if (data[0].equals("cpu")) {
            result = CPUsplitter(data[1], arg);
        }else if (data[0].equals("mem")) {
            result = MEMsplitter(data[1]);
        }else if (data[0].equals("fs")) {
            result = FSsplitter(data[1], arg);
        }
        if (result == null) {
            st.sendLine(CMD.CHARS.INFO + "ERROR");
        } else {
            st.sendLine(CMD.CHARS.ANSWER + result);
        }
        return true;
    }

    public static String SOsplitter(String cmd) {
        if (cmd.equals("get_description")) {
            return SysInfo.SO.getDescription();
        } else if (cmd.equals("get_name")) {
            return SysInfo.SO.getName();
        } else if (cmd.equals("get_arch")) {
            return SysInfo.SO.getArch();
        } else if (cmd.equals("get_version")) {
            return SysInfo.SO.getVersion();
        } else if (cmd.equals("get_patch_lvl")) {
            return SysInfo.SO.getPatchLevel();
        } else if (cmd.equals("get_vendor")) {
            return SysInfo.SO.getVendor();
        } else if (cmd.equals("get_vendor_cn")) {
            return SysInfo.SO.getVendorCodename();
        } else if (cmd.equals("get_uptime")) {
            double uptime = SysInfo.SO.getUptime();
            return (uptime < 0) ? null : Double.toString(uptime);
        } else if (cmd.equals("get_fuptime")) {
            short[] uptime = SysInfo.SO.getFormattedUptime();
            return uptime == null ? null : uptime[0] + "#" + uptime[1] + "#" + uptime[2];
        }
        return null;
    }

    public static String CPUsplitter(String cmd, int arg) {
        if (cmd.equals("get_info_size")) {
            return Num2Str(SysInfo.CPU.getInfoSize());
        } else if (cmd.equals("get_vendor")) {
            return SysInfo.CPU.getVendor(arg);
        } else if (cmd.equals("get_model")) {
            return SysInfo.CPU.getModel(arg);
        } else if (cmd.equals("get_mhz")) {
            return Num2Str(SysInfo.CPU.getMhz(arg));
        }else if (cmd.equals("get_cores_size")) {
            return Num2Str(SysInfo.CPU.getTotalCores(arg));
        }else if (cmd.equals("get_cache_size")) {
            return Num2Str(SysInfo.CPU.getCacheSize(arg));
        }else if (cmd.equals("get_cpu_size")) {
            return Num2Str(SysInfo.CPU.getTotalCPUs());
        }else if (cmd.equals("get_cpu_load")) {
            return SysInfo.CPU.getCPULoad(arg);
        }else if (cmd.equals("get_total_cpu_load")) {
            return SysInfo.CPU.getTotalCPULoad(arg);
        }
        return null;
    }

    public static String FSsplitter(String cmd, int arg) {
        if (cmd.equals("get_fs_size")) {
            return Num2Str(SysInfo.FileSys.getTotalFS());
        } else if (cmd.equals("get_total")) {
            return Num2Str(SysInfo.FileSys.getTotalSize(arg));
        }else if (cmd.equals("get_used")) {
            return Num2Str(SysInfo.FileSys.getUsed(arg));
        }else if (cmd.equals("get_available")) {
            return Num2Str(SysInfo.FileSys.getAvailable(arg));
        }else if (cmd.equals("get_type")) {
            return SysInfo.FileSys.getType(arg);
        }else if (cmd.equals("get_used_percent")) {
            return Num2Str(SysInfo.FileSys.getUsePercent(arg));
        }else if (cmd.equals("get_dir_name")) {
            return SysInfo.FileSys.getDirName(arg);
        }
        return null;
    }

    public static String MEMsplitter(String cmd) {
         if (cmd.equals("get_ram")) {
            return Num2Str(SysInfo.Memory.getRAM());

         } else if (cmd.equals("get_total")) {
            return Num2Str(SysInfo.Memory.getTotal());
        }else if (cmd.equals("get_used")) {
            return Num2Str(SysInfo.Memory.getUsed());
        }else if (cmd.equals("get_free")) {
            return Num2Str(SysInfo.Memory.getFree());
        }else if (cmd.equals("get_free_swap")) {
            return Num2Str(SysInfo.Memory.getFreeSwap());
        }else if (cmd.equals("get_total_swap")) {
            return Num2Str(SysInfo.Memory.getTotalSwap());
        }else if (cmd.equals("get_used_swap")) {
            return Num2Str(SysInfo.Memory.getUsedSwap());
        }
         return null;
    }
    
    private static String Num2Str(double number) {
        if (number < 0) {
            return null;
        } else {
            return Double.toString(number);
        }
    }
    
    private static String Num2Str(int number) {
        if (number < 0) {
            return null;
        } else {
            return Integer.toString(number);
        }
    }
    private static String Num2Str(long number) {
        if (number < 0) {
            return null;
        } else {
            return Long.toString(number);
        }
    }
}
