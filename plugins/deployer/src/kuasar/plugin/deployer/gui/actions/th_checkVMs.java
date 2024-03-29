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
package kuasar.plugin.deployer.gui.actions;

import kuasar.plugin.Intercom.GUI;
import kuasar.plugin.deployer.gui.actions.dialogs.pn_BinErr;
import kuasar.plugin.deployer.gui.actions.dialogs.pn_SvErr;
import kuasar.plugin.deployer.gui.pn_Checker;
import org.jdom.Element;

/**
 *
 * @author Jesus Navalon i Pastor <jnavalon at redhermes dot net>
 */
public class th_checkVMs extends Thread {

    private static class AbortException extends Exception {

        public AbortException() {
        }
    }
    private pn_Checker parent;
    /*
     * HashMap<String,Short>
     * String = Node path (XML);
     * Object[3]:
     *   [0] String = Visible path;
     *   [1] String = Cause;
     *   [2] Short =
     *          1: OK
     *          0: Error BIN
     *         -1: Error IMAGE FILE;
     */
    private Element dataProject = null;

    public th_checkVMs(pn_Checker parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        try {
            parent.setVisibleNext(false);
            parent.setInfoText("Extracting data");
            if (!extractData()) {
                abort();
            }
            parent.setInfoText("Checking disk images");
            if (!checkImage()) {
                abort();
            }
            parent.setInfoText("Checking servers");
            if (!checkServers()) {
                abort();
            }
            parent.setVisibleNext(true);
            parent.showFinished();
            parent.project = dataProject;
        } catch (AbortException ex) {
        }
    }

    private void abort() throws AbortException {
        parent.goPrevious();
        throw new AbortException();
    }

    private boolean extractData() {
        boolean recheck;
        boolean abort = false;
        do {
            recheck = false;
            DataExtractor extractor = new DataExtractor();
            if (!extractor.extract(parent.project)) {
                pn_BinErr binerr = new pn_BinErr(extractor.getErrors());
                GUI.loadPlugin(binerr);
                binerr.WaitAnswer();
                recheck = binerr.getAnswer();
                abort = binerr.getAbort();
                GUI.loadPlugin(parent);
            }
            dataProject = extractor.getData();
        } while (recheck == true);
        return !abort;
    }

    private boolean checkImage() {
        boolean recheck;
        boolean abort = false;
        do {
            recheck = false;
            ImageChecker checker = new ImageChecker();
            if (!checker.check(dataProject)) {
                pn_BinErr binerr = new pn_BinErr(checker.getErrors());
                GUI.loadPlugin(binerr);
                binerr.WaitAnswer();
                recheck = binerr.getAnswer();
                abort = binerr.getAbort();
                GUI.loadPlugin(parent);
            }
        } while (recheck == true);
        return !abort;
    }

    private boolean checkServers() {
        boolean recheck;
        boolean abort = false;
        do {
            recheck = false;
            ServerChecker checker = new ServerChecker();
            if (!checker.check(parent)) {
                pn_SvErr binerr = new pn_SvErr(checker.getErrors(), parent.target);
                GUI.loadPlugin(binerr);
                binerr.WaitAnswer();
                recheck = binerr.getAnswer();
                abort = binerr.getAbort();
                GUI.loadPlugin(parent);
            }
        } while (recheck == true);
        return !abort;
    }
}
