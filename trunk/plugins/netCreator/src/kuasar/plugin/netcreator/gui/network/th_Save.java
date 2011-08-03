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
package kuasar.plugin.netcreator.gui.network;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import kuasar.plugin.netcreator.Config;
import kuasar.plugin.utils.XML;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class th_Save extends Thread {

    private boolean prosecute = true;
    private Element node = null;
    private InetAddress ipv4 = null;
    private InetAddress ipv6 = null;
    private InetAddress mask = null;
    private InetAddress prefix = null;
    private InetAddress gw = null;
    private ArrayList<InetAddress> dns = null;
    private JProgressBar progress = null;
    private JLabel info = null;
    private boolean dhcp4 = false;
    private boolean dhcp6 = false;
    private Exception Exception;
    private String netpath;
    private JEditorPane summary = null;
    private short tabs = 0;

    private SimpleAttributeSet folderStyle = getFolderStyle();
    private SimpleAttributeSet nodeStyle = getNodeStyle();
    private SimpleAttributeSet attributeStyle = getAttributeStyle();

    public th_Save(ArrayList<Object[]> ips, InetAddress gw, ArrayList<InetAddress> dns, String netpath) {
        th_Save(ips, gw, dns, netpath, null, null, null);
    }

    public th_Save(ArrayList<Object[]> ips, InetAddress gw, ArrayList<InetAddress> dns, String netpath, JProgressBar progress, JLabel info, JEditorPane summary) {
        th_Save(ips, gw, dns, netpath, progress, info, summary);
    }

    private void th_Save(ArrayList<Object[]> ips, InetAddress gw, ArrayList<InetAddress> dns, String netpath, JProgressBar progress, JLabel info, JEditorPane summary) {
        this.node = XML.getElementOnPath(netpath, Config.rootNetwork);
        this.netpath = netpath;
        this.gw = gw;
        this.dns = dns;
        this.summary = summary;
        ipv4 = (InetAddress) ips.get(0)[0];
        ipv6 = (InetAddress) ips.get(1)[0];
        mask = (InetAddress) ips.get(0)[1];
        prefix = (InetAddress) ips.get(1)[1];
        if (!checkIP()) {
            System.err.println("Bad ip(s) was received");
            return;
        }
        this.progress = progress;
        this.info = info;
    }

    @Override
    public void run() {
        save();
    }

    public void cleanStop() {
        prosecute = false;
    }

    private void save() {
        try {
            node = setIPs(node);
        } catch (Exception ex) {
            System.err.println("CleanClosed exception received, old data is untouched, that's good.");
            return;
        }
        if (!netpath.equals("/")) {
            String path = netpath.substring(0, netpath.lastIndexOf("/"));
            if (path.isEmpty()) {
                path = "/";
            }
            String nameNode = netpath.substring(netpath.lastIndexOf("/") + 1);
            XML.RemoveElement(path, Config.rootNetwork, nameNode);
            XML.AddElement(path, Config.rootNetwork, node);
        }
        if (info != null) {
            info.setText("Saving...");
        }
        XML.Save(Config.VMdir, Config.VMdata, Config.rootNetwork);
        if (info != null) {
            info.setText("Finished!");
        }
    }

    private boolean checkIP() {
        try {
            if (ipv4 != null) {
                if (ipv4.equals(InetAddress.getByName("0.0.0.0"))) {
                    dhcp4 = true;
                }
            }
            if (ipv6 != null) {
                if (ipv6.equals(InetAddress.getByName("0::0"))) {
                    dhcp6 = true;
                }
            }
        } catch (UnknownHostException ex) {
            return false;
        }
        return true;
    }

    private String getIPv4() {
        if (dhcp4) {
            return "0.0.0.0";
        }
        if (ipv4 == null) {
            return "";
        }
        ipv4 = getNextIP(ipv4);
        return ipv4.getHostAddress();
    }

    private InetAddress getNextIP(InetAddress ip) {
        int[] blocks = new int[ip.getAddress().length];
        int i = 0;
        for (byte block : ip.getAddress()) {
            blocks[i++] = block < 0 ? block + 256 : block;
        }
        blocks[blocks.length - 1]++;
        for (i = blocks.length - 1; i >= 0; i--) {
            if (blocks[i] > 255) {
                blocks[i] = 0;
                if (i - 1 < 0) {
                    return null;
                } else {
                    blocks[i - 1]++;
                }
            }
        }
        byte newIP[] = new byte[blocks.length];
        i = 0;
        for (int block : blocks) {
            newIP[i++] = (byte) (block);
        }
        try {
            return InetAddress.getByAddress(newIP);
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    private String getMask() {
        if (dhcp4) {
            return "0.0.0.0";
        }
        if (mask == null) {
            return "";
        }
        return mask.getHostAddress();
    }

    private String getPrefix() {
        if (dhcp6) {
            return "0::0";
        }
        if (prefix == null) {
            return "";
        }
        return prefix.getHostAddress();
    }

    private String getIPv6() {
        if (dhcp6) {
            return "0::0";
        }
        if (ipv6 == null) {
            return "";
        }
        ipv6 = getNextIP(ipv6);
        return ipv6.getHostAddress();
    }

    private String getGw() {
        if (gw == null) {
            return "";
        }
        return gw.getHostAddress();
    }

    private String getDNS() {
        if (dns == null) {
            return "";
        }
        String alldns = "";
        for (InetAddress cdns : dns) {
            alldns += cdns.getHostAddress() + " ";
        }
        return alldns.trim();
    }

    private Element setIPs(Element cnode) throws Exception {

        List<Element> children = cnode.getChildren();
        for (Element child : children) {
            if (!prosecute) {
                throw Exception;
            }
            if (child.getAttributeValue("type").equals("vm")) {
                if (info != null) {
                    info.setText("Setting: " + child.getAttributeValue("name")
                            + " (" + (progress.getValue()) + "/" + progress.getMaximum() + ")");
                }
                child.setAttribute("ipv4", getIPv4());
                child.setAttribute("mask", getMask());
                child.setAttribute("ipv6", getIPv6());
                child.setAttribute("prefix", getPrefix());
                child.setAttribute("gw", getGw());
                child.setAttribute("dns", getDNS());
                if (summary != null) {
                    summary.getDocument().insertString(summary.getDocument().getLength(), printTabs() + child.getAttributeValue("name") + "\n", nodeStyle);
                    tabs++;
                    summary.getDocument().insertString(summary.getDocument().getLength(), printTabs() + "IPv4: " + child.getAttributeValue("ipv4") + "\n", attributeStyle);
                    summary.getDocument().insertString(summary.getDocument().getLength(), printTabs() + "IPv6: " + child.getAttributeValue("ipv6") + "\n", attributeStyle);
                    tabs--;
                }
                if (progress != null) {
                    progress.setValue(progress.getValue() + 1);
                }
            } else if (child.getAttributeValue("type").isEmpty()) {
                if (summary != null) {
                    summary.getDocument().insertString(summary.getDocument().getLength(), printTabs() +"<" + child.getAttributeValue("name") + ">\n", folderStyle);
                    tabs++;
                }
                child = setIPs(child);
                if (summary != null) {
                    tabs--;
                    summary.getDocument().insertString(summary.getDocument().getLength(), printTabs() +"</" + child.getAttributeValue("name") + ">\n", folderStyle);
                }
            }
        }
        return cnode;
    }

    private String printTabs() {
        String stabs = "";
        for (int i = 0; i < tabs; i++) {
            stabs += "    ";
        }
        return stabs;
    }

    private SimpleAttributeSet getFolderStyle(){
        SimpleAttributeSet saset = new SimpleAttributeSet();
        StyleConstants.setForeground(saset, Color.RED);
        StyleConstants.setFontSize(saset, 14);
        StyleConstants.setBold(saset, true);
        return saset;
    }
    private SimpleAttributeSet getNodeStyle(){
        SimpleAttributeSet saset = new SimpleAttributeSet();
        StyleConstants.setForeground(saset, Color.BLACK);
        StyleConstants.setFontSize(saset, 12);
        StyleConstants.setBold(saset, true);
        return saset;
    }
    private SimpleAttributeSet getAttributeStyle(){
        SimpleAttributeSet saset = new SimpleAttributeSet();
        StyleConstants.setForeground(saset, Color.BLACK);
        StyleConstants.setFontSize(saset, 12);
        StyleConstants.setBold(saset, false);
        return saset;
    }
}
