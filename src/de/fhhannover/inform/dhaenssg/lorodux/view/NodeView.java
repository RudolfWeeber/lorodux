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
 * Sie stellt die Informationen eines Node-Objektes dar.
 * 
 * @author Daniel Hänßgen
 * 
 * 09.05.2010 - Erste Implementierung
 * 09.05.2010 - NodeView um Vibration erweitert.
 * 16.05.2010 - Nodes um Description und Adress erweitert
 * 22.05.2010 - Darstellung aufgeräumt
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Node;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Tags;
import de.fhhannover.inform.dhaenssg.lorodux.util.GeoDirection;
import de.fhhannover.inform.dhaenssg.lorodux.util.GpsCalc;

public class NodeView extends View {

    private final transient Command REFRESH = new Command("aktualisieren",
	    Command.OK, 0);
    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);

    private final transient List mList;
    private final transient View mManageView;

    private final transient Node mNode;

    public NodeView(final View manageView, final Node node) {
	mManageView = manageView;
	mNode = node;

	mList = new List("Info", List.IMPLICIT);

	mList.addCommand(REFRESH);
	mList.addCommand(BACK);
	mList.setCommandListener(this);

	refresh();
	show();

    }

    /**
     * Aktualisiert Darstellung.
     * Berechnet Distanz und Kurs neu.
     */
    private void refresh() {
	mList.deleteAll();

	final Position pos = ActualPosition.getPositionCopy();
	final float lat = pos.getLat();
	final float lon = pos.getLon();
	final int nodeDist = GpsCalc.calcDistance(lat, lon, mNode.getLat(),
		mNode.getLon());
	final short nodeDir = (short) GpsCalc.calcBearing(lat, lon, mNode
		.getLat(), mNode.getLon());
	final short course = GpsCalc.calcCourse(pos.getHeading(), nodeDir);

	mNode.setDistance(nodeDist);
	mNode.setBearing(nodeDir);

	mList.append(mNode.getDistance() + " Meter", null);
	mList.append(GeoDirection.getChosenDirectionString(mNode.getBearing()),
		null);
	if (mNode.getTag() != 0)
	    mList.append(Tags.ELEMENT[mNode.getTag()], null);
	if (!mNode.getName().equalsIgnoreCase(""))
	    mList.append(mNode.getName(), null);
	if (!mNode.getAddress().equalsIgnoreCase(""))
	    mList.append(mNode.getAddress(), null);
	if (!mNode.getDescription().equalsIgnoreCase(""))
	    mList.append(mNode.getDescription(), null);
	mList.append("Breitengrad " + mNode.getLat(), null);
	mList.append("Längengrad " + mNode.getLon(), null);

	if (OptionsStore.vibrate)
	    try {
		GeoDirection.vibrate(course);
	    } catch (InterruptedException e) {
	    }

    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub
    }

    public void show() {
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    public void commandAction(final Command c, final Displayable d) {
	if (c == REFRESH) {
	    final int index = mList.getSelectedIndex();
	    refresh();
	    mList.setSelectedIndex(index, true);
	} else if (c == BACK)
	    mManageView.show();
    }

}
