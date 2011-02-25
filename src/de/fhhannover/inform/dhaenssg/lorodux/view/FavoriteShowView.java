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
 * This class is part of the LoroDux midlet suite
 * Shows details of a favorite, distance to current location and absolute
 * direction
 * @author Daniel Hänßgen
 * 
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

    // Declaration of commands
    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);
    private final transient Command REFRESH = new Command("aktualisieren",
	    Command.OK, 0);


    private final transient FavoriteManageView mManageView;

    private final transient List mList;

    private final transient Favorite mFav;

    /**
     * Constructor
     * @param manageView View to return to, when done
     * @param fav Favorite to show
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
     * Recalc distance, bearing and course and display them
     * Vibrate the phone, if this is activated in the config
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
