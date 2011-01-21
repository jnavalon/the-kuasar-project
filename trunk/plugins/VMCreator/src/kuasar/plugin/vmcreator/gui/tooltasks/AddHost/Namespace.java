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

package kuasar.plugin.vmcreator.gui.tooltasks.AddHost;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public final class Namespace {

    public static final class HiperVisors{
        public static final String UNDEFINED = "UND";
        public static final String VIRTUALBOX = "VBX";
        public static final String VMWARE = "VMW";
        public static final String QEMU = "QEM";
        public static final String VIRTUALPC = "VPC";
        public static final String XEN = "XEN";
    }

    public static final class keyMaps{
        public static final String HYPERVISOR_SAFE = "hvs";
        public static final String HYPERVISOR = "hv";
        public static final String OS = "os";
        public static final String OS_VERSION = "osv";
        public static final String DEVICES = "dev";
        public static final String MEMORY = "mem";
        public static final String NETWORK = "net";
    }
    
    public static final class OSystems{
        public static final String[] SYSTEMS = {"MS Windows", "Linux", "Solaris", "BSD", "IBM OS/2", "Mac OS X", "OTHER"};
        public static final String[] WINDOWS ={"Windows 3.1", "Windows 95", "Windows 98", "Windows NT 4", "Windows 2000", "Windows XP", "Windows 2003", "Windows Vista", "Windows 2008", "Windows 7"};
        public static final String[] LINUX={"Linux 2.2", "Linux 2.4", "Linux 2.6", "Arch Linux", "Debian", "OpenSuse", "Fedora","Gentoo","Mandriva", "Red Hat", "Turbolinux", "Ubuntu", "Xandros", "Oracle", "Other Linux"};
        public static final String[] SOLARIS ={"Solaris", "OpenSolaris"};
        public static final String[] BSD = {"FreeBSD", "OpenBSD", "NetBSD"};
        public static final String[] IBMOS2 ={"OS/2 Warp 3", "OS/2 Warp 4", "OS/2 Warp 4.5", "eComStation", "Other OS/2"};
        public static final String[] MACOSX = {"Mac OS X Server"};
        public static final String[] OTHER = {"DOS","Netware", "L4", "QNX", "Other"};
    }

}
