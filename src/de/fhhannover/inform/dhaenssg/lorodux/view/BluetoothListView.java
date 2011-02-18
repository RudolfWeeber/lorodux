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
 * This class belongs to the ?Lorodux midlet suite
 * 
 * Shows the discovererd bluetooth devices
 * 
 * This view is not derived from the view class as it should only be called
 * from the bluetooth gps reader class
 * 
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.gps.BluetoothGpsReader;

public class BluetoothListView implements CommandListener {

    private BluetoothGpsReader mReader;

    private List mList;

    public BluetoothListView(BluetoothGpsReader reader) {
	mReader = reader;
    }

    /**
     * Shows the list of discoverd devices
     * @param Vector containing the found devices
     */
    public void displayList(Vector items) {
	mList = null;
	mList = new List("Ger‰te gefunden", List.IMPLICIT);
	mList.setTitle("Bluetooth Ger‰te");

	for (int i = 0; i < items.size(); i++) {
	    mList.append((String) items.elementAt(i), null);
	}
	mList.setCommandListener(this);
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    /**
     * Is called when the user selects a device from the list
     * Will call selectDevice() to establish connection
     */
    public void commandAction(Command c, Displayable d) {
	if (c == List.SELECT_COMMAND) {
	    int index = mList.getSelectedIndex();
	    mReader.selectDevice(index, mList.getString(index));
	    LoroDux.displayStringOnMainView("Bluetooth-GPS-Ger‰t ausgew‰hlt");
	}
    }
}
