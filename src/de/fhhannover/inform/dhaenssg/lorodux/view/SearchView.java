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
 * Sie dient der Darstellung der Suchergebnisse.
 * 
 * 22.05.2010 - Erste Implementierung
 * 02.06.2010 - Verhalten korrigiert
 * 07.06.2010 - Anpassung auf Grund SimpleInputView Anpassung
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Node;
import de.fhhannover.inform.dhaenssg.lorodux.osm.NodeManager;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Tags;
import de.fhhannover.inform.dhaenssg.lorodux.util.GeoDirection;

public class SearchView extends View {

    private final transient Command SEARCH = new Command("Eingabe", Command.ITEM, 0);
    private final transient Command BACK = new Command("Zurück", Command.BACK, 1);

    private final transient List mList;
    private transient Vector mItems;
    private transient boolean mShowInputView = true;

    public SearchView() {
	mList = new List("Suchergebnisse", List.IMPLICIT);
	mList.setFitPolicy(List.TEXT_WRAP_ON);

	mList.addCommand(SEARCH);
	mList.addCommand(BACK);
	mList.setCommandListener(this);

    }

    /**
     * Wir von SimpleInputView aufgerufen, wenn dort eine
     * Eingabe getätigt wurde.
     * Abhängig, welcher Listeneintrag vorher ausgewählt war,
     * wird der String anders behandelt.
     */
    protected void setSimpleData(final String data) {
	mList.deleteAll();
	mItems = NodeManager.getNodesContaining(data);

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
	if (mShowInputView){
	    mShowInputView = false;
	    new SimpleInputView(this, "Suchbegriff eingeben", 20, TextField.ANY);
	} else {
	    mShowInputView = true;
	    Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
	}
    }

    public void commandAction(final Command c, final Displayable d) {
	if (c == SEARCH) {
	    mShowInputView = false;
	    new SimpleInputView(this, "Suchbegriff eingeben", 20, TextField.ANY);
	} else if (c == BACK) {
	    LoroDux.showView(LoroDux.MAINVIEW);
	} else if (c == List.SELECT_COMMAND) {
	    new NodeView(this, (Node) mItems
		    .elementAt(mList.getSelectedIndex()));
	}
    }

}
