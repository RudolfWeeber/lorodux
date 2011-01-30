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
 * Sie dient der Darstellung der Umliegenden Punkte.
 * 
 * @author Daniel Hänßgen
 * Version: 0.1
 * Datum 05.05.2010 -> Implementierung
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Node;
import de.fhhannover.inform.dhaenssg.lorodux.osm.NodeManager;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Tags;
import de.fhhannover.inform.dhaenssg.lorodux.util.GeoDirection;

public class NearByView extends View {

    private final transient Command REFRESH = new Command("aktualisieren",
	    Command.OK, 0);
    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);
    private final transient Command SETRADIUS = new Command(
	    "Suchradius ändern", Command.ITEM, 1);
    private final transient Command ADDFAV = new Command(
	    "Punkt als Favorit speichern", Command.ITEM, 1);
    private transient int mDirection = -1;
    private transient Vector mItems;

    private final transient List mList;

    public NearByView() {
	mList = new List("Umgebungssuche", List.IMPLICIT);
	mList.setFitPolicy(List.TEXT_WRAP_ON);

	mList.addCommand(REFRESH);
	mList.addCommand(BACK);
	mList.addCommand(SETRADIUS);
	mList.addCommand(ADDFAV);
	mList.setCommandListener(this);
    }

    public void setSimpleData(final String radius) {
	OptionsStore.radius = Integer.parseInt(radius);
    }

    /**
     * Aktualisiert komplette Darstellung. Berechnet Distanz und Richtung neu.
     */
    public void refresh() {
	mList.deleteAll();
	mItems = NodeManager.getNodesNearActualPosition(mDirection);

	String temp;
	for (int i = 0; i < mItems.size() && i < 100; i++) {
	    String item = "";
	    final Node node = (Node) mItems.elementAt(i);
	    temp = Tags.ELEMENT[node.getTag()];
	    if (!temp.equalsIgnoreCase("")) {
		item = temp;
	    }

	    temp = node.getName();
	    if (!temp.equalsIgnoreCase("")) {
		item = item + " " + temp;
	    }

	    temp = node.getAddress();
	    if (!temp.equalsIgnoreCase("")) {
		item = item + " " + temp;
	    }

	    temp = node.getDescription();
	    if (!temp.equalsIgnoreCase("")) {
		item = item + " " + temp;
	    }

	    item = item
		    + " "
		    + node.getDistance()
		    + "Meter "
		    + GeoDirection.getChosenDirectionString(((Node) mItems
			    .elementAt(i)).getBearing());

	    mList.append(item, null);
	}
    }

    public void show() {
	refresh();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    /**
     * Holt sich die aktuellen Positionsdaten vom GPS-Empfänger schreibt diese
     * auf den InputView und zeigt diesen dann an.
     */
    private void addAsFavorite(final int i) {
	final Node node = (Node) mItems.elementAt(i);
	final FavoriteInputView inputView = new FavoriteInputView(this);
	inputView.setCoordinates(node.getLat(), node.getLon());
	inputView.setName(node.getName());
	inputView.show();
    }

    public void commandAction(final Command c, final Displayable arg1) {
	if (c == BACK) {
	    LoroDux.showView(LoroDux.MAINVIEW);
	} else if (c == REFRESH) {
	    mDirection = -1;
	    refresh();
	} else if (c == SETRADIUS) {
	    new SimpleInputView(this, "Umkreis in Metern", 4, TextField.NUMERIC);
	} else if (c == ADDFAV) {
	    addAsFavorite(mList.getSelectedIndex());
	} else if (c == List.SELECT_COMMAND) {
	    new NodeView(this,
		    (Node) mItems.elementAt(mList.getSelectedIndex()));
	}

    }
}
