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
 * Diese Klasse gehört zur LodoDux MIDlet Suite.
 * Sie dient der Darstellung der aktuellen Daten, die vom GPS-Empfänger kommen.
 * 
 * @author Daniel Hänßgen
 * Version: 0.1
 * März 2010 - Erste Implementierung mit StringItems
 * 11.04.2010 - Aus EXIT - BACK to MainView gemacht.
 * 14.04.2010 - Implementierung der StringItems in TextBox gewandelt
 * 		-> Nur so Screenreader tauglich
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;

public class GpsView extends View {

    private final transient Command REFRESH = new Command("Aktualisieren",
	    Command.OK, 0);
    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);

    private final transient List mList;

    public GpsView() {
	mList = new List("GPS Status", List.IMPLICIT);
	mList.addCommand(REFRESH);
	mList.addCommand(BACK);
	mList.setCommandListener(this);
    }

    /**
     * Parst das übergebene Position-Objekt und schreibt die Daten auf die
     * Liste. Vorher wird dieser alle Einträge entfernt.
     * 
     * @param Position
     *            , die auf dem Display angezeigt werden soll.
     */
    public void set(final Position pos) {
	try {
	    mList.deleteAll();
	    mList.append("Anzahl Satelliten " + pos.getSatellites(), null);
	    mList.append("HDOP " + pos.getHDOP(), null);
	    mList.append(pos.getDateAsString(), null);
	    mList.append("Breitengrad " + pos.getLat(), null);
	    mList.append("Längengrad " + pos.getLon(), null);
	    mList.append("Richtung " + pos.getHeading() + " Grad", null);
	    mList.append(pos.getSpeed() + " kilometer pro stunde", null);

	} catch (NullPointerException e) {
	    LoroDux.displayStringOnMainView("GpsView: " + e.toString());
	}
    }

    public void show() {
	set(ActualPosition.getPositionCopy());
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    public void commandAction(final Command c, final Displayable arg1) {
	if (c == REFRESH) {
	    final int item = mList.getSelectedIndex();
	    set(ActualPosition.getPositionCopy());
	    mList.setSelectedIndex(item, true);
	} else if (c == BACK) {
	    LoroDux.showView(LoroDux.MAINVIEW);
	}
    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub
	
    }

}
