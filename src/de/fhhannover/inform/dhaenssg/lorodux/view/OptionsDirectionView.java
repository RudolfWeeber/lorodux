/* 
 * Copyright 2010 Daniel Hänßgen (daniel.haenssgen@stud.fh-hannover.de)
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
 * This class belongs to the LoroDux Midlet Suite
 * Allows for setting the way, directions are spoken
 * 
 * @author Daniel Hänßgen
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;

public class OptionsDirectionView extends View {

    // Definition of commands
    private final transient Command OK = new Command("OK", Command.OK, 0);
    private final transient Command BACK = new Command("Zurück", Command.BACK, 1);

    private final transient List mList;

    private final transient OptionsView mOptionsView;

    /**
     * Constructor
     * @param Options view, to which to return, when done
     */
    public OptionsDirectionView(final OptionsView optionsView) {
	mOptionsView = optionsView;

	mList = new List("Angabe der Richtung", List.IMPLICIT);
	mList.append("auf der Uhr (relativ zur Bewegungsrichtung)", null);
	mList.append("in Himmelsrichtung", null);
	mList.append("in Grad (relativ zur Bewegungsrichtung)", null);
	mList.append("in Grad (absolut)", null);

	mList.addCommand(BACK);
	mList.addCommand(OK);
	mList.setCommandListener(this);

	show();

    }

    void setSimpleData(String data) {
    }

    public void show() {
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    // Handle commands
    public void commandAction(final Command c, final Displayable d) {
	if (c == OK || c == List.SELECT_COMMAND) {
	    OptionsStore.chosenDirectionString = mList.getSelectedIndex();
	    mOptionsView.show();
	} else if (c == BACK) {
	    mOptionsView.show();
	}
    }

}
