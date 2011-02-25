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
 * Sie stellt die Adress- und Place-Node in der Nähe dar. 
 * 
 * @author Daniel Hänßgen
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Node;
import de.fhhannover.inform.dhaenssg.lorodux.osm.NodeManager;
import de.fhhannover.inform.dhaenssg.lorodux.util.GeoDirection;

public class WhereAmIView extends View {
    
    private final transient Command REFRESH = new Command("aktualisieren", Command.OK, 0);
    private final transient Command BACK = new Command("zurück", Command.BACK, 1);
    
    private final transient List mList;
    
    public WhereAmIView() {
	mList = new List("Wo bin ich?", List.IMPLICIT);

	mList.addCommand(REFRESH);
	mList.addCommand(BACK);
	mList.setCommandListener(this);
    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub
    }
    
    private void refresh(){
	mList.deleteAll();
	
	Vector elements = NodeManager.getWhereAmI();
	
	mList.append(((Node) elements.elementAt(0)).getAddress(), null);
	mList.append(((Node) elements.elementAt(0)).getDistance() + " Meter", null);
	mList.append(GeoDirection.getChosenDirectionString(((Node) elements.elementAt(0)).getBearing()), null);
	mList.append(((Node) elements.elementAt(1)).getName(), null);
    }

    public void show() {
	refresh();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    public void commandAction(final Command c, final Displayable d) {
	if (c == REFRESH){
	    refresh();
	} else if (c == BACK)
	    LoroDux.showView(LoroDux.MAINVIEW);
    }

}
