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
 *  This class is part of the LoroDux Midlet Suite
 * Shows main window and main menu
 * 
 * @author Daniel H‰nﬂgen
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;

public class MainView extends View {

    // Definition of commands
    private final transient Command GPSVIEW = new Command("GPS Status anzeigen",
	    Command.SCREEN, 0);
    private final transient Command FAVORITEVIEW = new Command("Favoriten verwalten",
	    Command.SCREEN, 0);
    private final transient Command WHEREAMIVIEW = new Command("Wo bin ich?",
	    Command.SCREEN, 0);
    private final transient Command NEARBYVIEW = new Command("Umgebungssuche",
	    Command.SCREEN, 0);
    private final transient Command SEARCHVIEW = new Command("Stichwortsuche",
	    Command.SCREEN, 0);
    private final transient Command OPTIONSVIEW = new Command("Einstellungen",
	    Command.SCREEN, 0);
    private final transient Command EXIT = new Command("Beenden", Command.EXIT, 1);

    private final transient Command YES = new Command("Ja", Command.OK, 0);
    private final transient Command NO = new Command("Nein", Command.EXIT, 1);

    private final transient Form mForm;
    private final transient StringItem mBody = new StringItem("Willkommen", null);

    /**
     * Constructor
     * @param String, which should be displayed on the main window.
     */
    public MainView(final String body) {
	if (body != null) {
	    mBody.setLabel(body);
	}
	mForm = new Form("LoroDux");
	mForm.append(mBody);

	mForm.addCommand(GPSVIEW);
	mForm.addCommand(FAVORITEVIEW);
	mForm.addCommand(WHEREAMIVIEW);
	mForm.addCommand(NEARBYVIEW);
	mForm.addCommand(SEARCHVIEW);
	mForm.addCommand(OPTIONSVIEW);
	mForm.addCommand(EXIT);
	mForm.setCommandListener(this);

	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }

    public synchronized void setTitle(final String string) {
	mForm.setTitle(string);
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }

    public synchronized void setBody(final String string) {
	mForm.setTitle("LoroDux");
	mBody.setLabel(string);
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }

    public synchronized void setText(final String title, final String body) {
	mForm.setTitle(title);
	mBody.setLabel(body);
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }

    public synchronized void show() {
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }

    // Handle commands
    public void commandAction(final Command c, final Displayable d) {
	if (c == GPSVIEW) {
	    LoroDux.showView(LoroDux.GPSVIEW);
	} else if (c == FAVORITEVIEW) {
	    LoroDux.showView(LoroDux.FAVORITEVIEW);
	} else if (c == WHEREAMIVIEW) {
	    LoroDux.showView(LoroDux.WHEREAMIVIEW);
	} else if (c == NEARBYVIEW) {
	    LoroDux.showView(LoroDux.NEARBYVIEW);
	} else if (c == SEARCHVIEW) {
	    LoroDux.showView(LoroDux.SEARCHVIEW);
	} else if (c == OPTIONSVIEW) {
	    LoroDux.showView(LoroDux.OPTIONSVIEW);
	} else if (c == EXIT) {
	    final Alert areYouSure = new Alert("Frage",
		    "Wollen Sie LoroDux beenden?", null, AlertType.WARNING);
	    areYouSure.addCommand(YES);
	    areYouSure.addCommand(NO);
	    areYouSure.setCommandListener(this);
	    areYouSure.setTimeout(Alert.FOREVER);
	    Display.getDisplay(LoroDux.getInstance()).setCurrent(areYouSure);
	} else if (c == YES) {
	    LoroDux.getInstance().exit();
	} else if (c == NO) {
	    show();
	}

    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub
	
    }
}
