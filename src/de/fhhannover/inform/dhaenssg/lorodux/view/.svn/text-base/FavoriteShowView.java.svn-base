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
 * Sie dient der Darstellung eines ausgewählten Favoriten,
 * die Distanz vom aktuellen Standort und die absolute Richtung.
 * @author Daniel Hänßgen
 * 
 * Version: 0.1
 * 11.04.2010	Implementierung
 * Version: 0.2
 * 12.04.2010	Aktuallisierungs-Command hinzugefügt
 * 30.06.2010 Refactored
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Favorite;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;
import de.fhhannover.inform.dhaenssg.lorodux.util.GeoDirection;
import de.fhhannover.inform.dhaenssg.lorodux.util.GpsCalc;

public class FavoriteShowView extends View {

    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);
    private final transient Command REFRESH = new Command("aktualisieren",
	    Command.OK, 0);

    private final transient FavoriteManageView mManageView;
    private final transient List mList;
    private final transient Favorite mFav;

    /**
     * Konstruktor, dem Favoriten-Objekt und View übergeben wird,
     * zu dem zurückgekehrt werden soll.
     * @param manageView
     * @param fav
     */
    public FavoriteShowView(final FavoriteManageView manageView,
	    final Favorite fav) {
	mManageView = manageView;
	mFav = fav;

	mList = new List("Favorit", List.IMPLICIT);
	mList.addCommand(BACK);
	mList.addCommand(REFRESH);
	mList.setCommandListener(this);

	refresh();
	show();
    }

    /**
     * Methode berechnet Distanz, Bearing und Kurs neu,
     * stellt diese auf dem Display dar und lässt
     * das Handy vibrieren, falls diese Funktion im Optionen-Menü eingestellt wurde.
     */
    public void refresh() {
	final Position actualPosition = ActualPosition.getPositionCopy();

	final int distance = GpsCalc.calcDistance(actualPosition.getLat(),
		actualPosition.getLon(), mFav.getLat(), mFav.getLon());
	final short bearing = (short) GpsCalc.calcBearing(
		actualPosition.getLat(), actualPosition.getLon(),
		mFav.getLat(), mFav.getLon());
	final short course = GpsCalc.calcCourse(actualPosition.getHeading(),
		bearing);

	mList.deleteAll();
	mList.append(distance + " Meter", null);
	mList.append(GeoDirection.getChosenDirectionString(bearing), null);
	mList.append(mFav.getName(), null);
	mList.append("Breitengrad " + mFav.getLat(), null);
	mList.append("Längengrad " + mFav.getLon(), null);

	if (OptionsStore.vibrate) {
	    try {
		GeoDirection.vibrate(course);
	    } catch (InterruptedException e) {
		/* Was soll man schon machen, wenn das vibrieren fehlschlägt?! */
	    }
	}

    }

    public void show() {
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    public void commandAction(final Command c, final Displayable d) {
	if (c == BACK) {
	    mManageView.show();
	} else if (c == REFRESH) {
	    refresh();
	}

    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub
	
    }

}
