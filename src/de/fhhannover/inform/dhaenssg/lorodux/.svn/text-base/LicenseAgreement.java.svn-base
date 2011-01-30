/*
 * Copyright 2010 Daniel H‰nﬂgen (daniel.haenssgen@stud.fh-hannover.de)
 * All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version. 
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details:
 * http://www.gnu.org/licenses/gpl.txt
 */

/**
 * Zeigt die Lizenzvereinbarung an.
 * Diese muss akzeptiert werden, sonst wird das Programm beendet.
 * 
 * @author Daniel H‰nﬂgen
 * 
 * Version: 0.1
 * 
 * 26.03.2010 Implementierung
 */

package de.fhhannover.inform.dhaenssg.lorodux;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

public class LicenseAgreement implements CommandListener {

    private TextBox mLicenseAgreement;
    private final Command EXIT = new Command("Ablehnen", Command.STOP, 1);
    private final Command OK = new Command("Akzeptieren", Command.OK, 2);
    private CommandListener CL = this;

    /**
     * @uml.property name="mLoroDux"
     * @uml.associationEnd
     */
    public LicenseAgreement() {
	mLicenseAgreement = new TextBox("LoroDux Lizenzabkommen",
		"Bla bla bla", 120, TextField.ANY);
	mLicenseAgreement.addCommand(EXIT);
	mLicenseAgreement.addCommand(OK);
	mLicenseAgreement.setCommandListener(CL);

	Display.getDisplay(LoroDux.getInstance()).setCurrent(mLicenseAgreement);
    }

    public synchronized void commandAction(Command c, Displayable d) {
	switch (c.getCommandType()) {
	case Command.STOP:
	    LoroDux.getInstance().exit();
	    break;
	case Command.OK:
	    LoroDux.showView(LoroDux.MAINVIEW);
	    break;
	}

    }

}
