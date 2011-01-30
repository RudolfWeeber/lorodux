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
 * Diese Klasse gehört zur LoroDux MIDlet Suite.
 * 
 * Dient zur Darstellung die gefundenen Bluetooth-Geräte.
 * 
 * Einziger View, der nicht von View erbt, da dieser View
 * nur vom BluetoothGPSReader aufgerufen werden soll.
 * 
 * Datum 14.04.2010
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
     * Wird aufgerufen um gefundene Bluetooth-Geräte als Liste anzuzeigen
     * und den View dann auf dem Display darzustellen.
     * @param Vector mit gefundenen Bluetooth-Geräten
     */
    public void displayList(Vector items) {
	mList = null;
	mList = new List("Geräte gefunden", List.IMPLICIT);
	mList.setTitle("Bluetooth Geräte");

	for (int i = 0; i < items.size(); i++) {
	    mList.append((String) items.elementAt(i), null);
	}
	mList.setCommandListener(this);
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    /**
     * Wird ausgeführt, wenn Anwender einen Empfänger aus der Liste wählt.
     * Veranlasst mit selectDevice() sich mit dem Gewählten zu verbinden.
     */
    public void commandAction(Command c, Displayable d) {
	if (c == List.SELECT_COMMAND) {
	    int index = mList.getSelectedIndex();
	    mReader.selectDevice(index, mList.getString(index));
	    LoroDux.displayStringOnMainView("Bluetooth-GPS-Gerät ausgewählt");
	}
    }
}
