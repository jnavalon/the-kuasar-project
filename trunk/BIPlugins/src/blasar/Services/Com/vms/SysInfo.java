/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blasar.Services.Com.vms;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;
import org.hyperic.sigar.Swap;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class SysInfo {

    private static Sigar sigar=new Sigar();
    

    static class OS {

        private static OperatingSystem system = OperatingSystem.getInstance();

        public static String getDescription() {

            return system.getDescription();
        }

        public static String getName() {
            return system.getName();
        }

        public static String getArch() {
            return system.getArch();
        }

        public static String getVersion() {
            return system.getVersion();
        }

        public static String getPatchLevel() {
            return system.getPatchLevel();
        }

        public static String getVendor() {
            return system.getVendor();
        }

        public static String getVendorCodename() {
            return system.getVendorCodeName();
        }

        public static String getVendorVersion() {
            return system.getVersion();
        }

        public static double getUptime() {
            try {
                return sigar.getUptime().getUptime();
            } catch (SigarException ex) {
                return -1;
            }
        }

        /*
         * Return short[3] where:
         *          [0] :   days
         *          [1] :   hours
         *          [2] :   seconds
         */
        public static short[] getFormattedUptime() {
            double uptime = getUptime();
            if (uptime < 0) {
                return null;
            }
            short[] time = new short[3];
            time[0] = (short) (uptime / (86400)); //86400 = 24H*60M*60S
            time[1] = (short) ((uptime / 3600) % 24); //3600 = 60M*60S
            time[2] = (short) ((uptime / 60) % 60);
            return time;
        }
    }

    static class CPU {

        private static CpuInfo[] cpuinfo = null;
        private static CpuPerc[] cpus = null;

        private static boolean loadCPUInfo(boolean reload) {
            if (cpuinfo == null || reload) {
                try {
                    cpuinfo = sigar.getCpuInfoList();
                    return true;
                } catch (SigarException ex) {
                    return false;
                }
            }
            return true;
        }

        private static boolean loadCPUPerc(boolean reload) {
            if (cpus == null || reload) {
                try {
                    cpus = sigar.getCpuPercList();
                    return true;
                } catch (SigarException ex) {
                    return false;
                }
            }
            return true;
        }

        public static int getInfoSize() {
            if (!loadCPUInfo(false)) {
                return -1;
            }
            return cpuinfo.length;
        }

        public static String getVendor(int cell) {
            if (!loadCPUInfo(false)) {
                return null;
            }
            if (cell >= getInfoSize()) {
                return null;
            }
            return cpuinfo[cell].getVendor();
        }

        public static String getModel(int cell) {
            if (!loadCPUInfo(false)) {
                return null;
            }
            if (cell >= getInfoSize()) {
                return null;
            }
            return cpuinfo[cell].getModel();
        }

        public static int getMhz(int cell) {
            if (!loadCPUInfo(false)) {
                return -1;
            }
            if (cell >= getInfoSize()) {
                return -1;
            }
            return cpuinfo[cell].getMhz();
        }

        public static int getTotalCores(int cell) {
            if (!loadCPUInfo(false)) {
                return -1;
            }
            if (cell >= getInfoSize()) {
                return -1;
            }
            return cpuinfo[cell].getTotalCores();
        }

        public static long getCacheSize(int cell) {
            if (!loadCPUInfo(false)) {
                return -1;
            }
            if (cell >= getInfoSize()) {
                return -1;
            }
            if (cpuinfo[cell].getCacheSize() != Sigar.FIELD_NOTIMPL) {
                return cpuinfo[cell].getCacheSize();
            } else {
                return -1;
            }
        }

        public static int getTotalCPUs() {
            if (!loadCPUInfo(false)) {
                return -1;
            }
            return cpuinfo.length;
        }

        public static String getCPULoad(int cpu) {
            if (!loadCPUPerc(false)) {
                return null;
            }
            if (cpu >= getInfoSize()) {
                return null;
            }
            return CpuPerc.format(cpus[cpu].getUser());
        }

        public static String getTotalCPULoad(int cpu) {
            if (!loadCPUPerc(false)) {
                return null;
            }
            if (cpu >= getInfoSize()) {
                return null;
            }
            try {
                return CpuPerc.format(sigar.getCpuPerc().getUser());
            } catch (SigarException ex) {
                return null;
            }
        }
    }

    static class Memory {

        private static Mem memory = null;
        private static Swap swap = null;

        private static boolean loadMemory(boolean reload) {
            if (memory == null || reload) {
                try {
                    memory = sigar.getMem();
                } catch (SigarException ex) {
                    return false;
                }
            }
            return true;
        }

        private static boolean loadSwap(boolean reload) {
            if (swap == null || reload) {
                try {
                    swap = sigar.getSwap();
                } catch (SigarException ex) {
                    return false;
                }
            }
            return true;
        }

        /*
         * Return Total RAM in MB
         */
        public static long getRAM() {
            if (!loadMemory(false)) {
                return -1;
            }
            return memory.getRam();
        }
        /*
         * Return Total MEM in bits
         */

        public static long getTotal() {
            if (!loadMemory(false)) {
                return -1;
            }
            return memory.getTotal();
        }
        /*
         * Return Used MEM in bits
         */

        public static long getUsed() {
            if (!loadMemory(false)) {
                return -1;
            }
            return memory.getActualUsed();
        }
        /*
         * Return Free MEM in bits
         */

        public static long getFree() {
            if (!loadMemory(false)) {
                return -1;
            }
            return memory.getActualFree();
        }
        /*
         * Return Free Swap MEM in bits
         */

        public static long getFreeSwap() {
            if (!loadSwap(false)) {
                return -1;
            }
            return swap.getFree();
        }
        /*
         * Return Free Swap MEM in bits
         */

        public static long getTotalSwap() {
            if (!loadSwap(false)) {
                return -1;
            }
            return swap.getTotal();
        }
        /*
         * Return Free Swap MEM in bits
         */

        public static long getUsedSwap() {
            if (!loadSwap(false)) {
                return -1;
            }
            return swap.getUsed();
        }
    }

    static class FileSys {

        private static SigarProxy proxy = null;

        private static boolean loadProxy(boolean reload) {
            if (proxy == null || reload) {
                proxy = SigarProxyCache.newInstance(sigar);
            }
            return true;
        }

        private static FileSystem getFileSystem(int id) {
            if (!loadProxy(false)) {
                return null;
            }
            try {
                FileSystem[] fss = proxy.getFileSystemList();
                if (fss.length <= id) {
                    return null;
                }
                return fss[id];
            } catch (SigarException ex) {
                return null;
            }
        }

        public static int getTotalFS() {
            if (!loadProxy(false)) {
                return -1;
            }
            try {
                return proxy.getFileSystemList().length;
            } catch (SigarException ex) {
                return -1;
            }
        }

        private static FileSystemUsage getFileSysUsage(int id) {
            FileSystem fs = getFileSystem(id);
            FileSystemUsage usage = null;
            if (fs instanceof NfsFileSystem) {
                NfsFileSystem nfs = (NfsFileSystem) fs;
                if (!nfs.ping()) {
                    return null;
                }
            }
            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
            } catch (SigarException ex) {
                return null;
            }
            return usage;
        }

        public static long getTotalSize(int fs) {
            FileSystemUsage fsu = getFileSysUsage(fs);
            if (fsu == null) {
                return -1;
            }
            return fsu.getTotal();
        }

        public static long getUsed(int fs) {
            FileSystemUsage fsu = getFileSysUsage(fs);
            if (fsu == null) {
                return -1;
            }
            return fsu.getUsed();
        }

        public static long getAvailable(int fs) {
            FileSystemUsage fsu = getFileSysUsage(fs);
            if (fsu == null) {
                return -1;
            }
            return fsu.getAvail();
        }

        public static String getType(int fs) {
            FileSystem fsys = getFileSystem(fs);
            if (fsys == null) {
                return null;
            }
            return fsys.getSysTypeName();
        }

        public static double getUsePercent(int fs) {
            FileSystemUsage fsu = getFileSysUsage(fs);
            if (fsu == null) {
                return -1;
            }
            return fsu.getUsePercent();
        }

        public static String getDirName(int fs) {
            FileSystem fsys = getFileSystem(fs);
            if (fsys == null) {
                return null;
            }
            return fsys.getDirName();
        }
    }
}
