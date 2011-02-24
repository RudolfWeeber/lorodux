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
 * Sie dient der Konfiguration verschiedener Funktionen.
 * 
 * @author Daniel Hänßgen
 * Datum:
 * 09.05.2010
 * 07.06.2010 - Anpassung auf Grund SimpleInputView Anpassung
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;

public class OptionsView extends View {

    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);

    private final transient List mList;
    private transient int mSelectedIndex;

    public OptionsView() {
	mList = new List("Einstellungen", List.IMPLICIT);

	mList.append("Vibration Ein Aus", null);
	mList.append("Umkreisradius ändern", null);
	mList.append("Aktualisierungsintervall ändern", null);
	mList.append("Zeit bis GPS Ausfall benachrichtigt wird", null);
	mList.append("Angabe der Richtungen", null);
	mList.append("Internes / externes Gps umschalten", null);

	mList.addCommand(BACK);
	mList.setCommandListener(this);
    }

    /**
     * Wird von SimpleInputView aufgerufen, wenn dort eine
     * Eingabe getätigt wurde.
     * Abhängig, welcher Listeneintrag vorher ausgewählt war,
     * wird der String anders behandelt.
     */
    protected void setSimpleData(final String data) {
	switch (mSelectedIndex) {
	case 1:
	    OptionsStore.radius = Integer.parseInt(data);
	    break;

	case 2:
	    OptionsStore.timeAutoRefresh = Integer.parseInt(data);
	    break;

	case 3:
	    OptionsStore.timeGpsLostAllowed = Integer.parseInt(data) * 1000;
	    break;

	default:
	    break;

	}

    }

    public void show() {
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);

    }

    public void commandAction(final Command c, final Displayable d) {

	if (c == List.SELECT_COMMAND) {
	    mSelectedIndex = mList.getSelectedIndex();
	    switch (mSelectedIndex) {
	    case 0:
		if (OptionsStore.vibrate) {
		    OptionsStore.vibrate = false;
		    AlertManager.displayInfo("Vibration ist aus");
		} else {
		    OptionsStore.vibrate = true;
		    AlertManager.displayInfo("Vibration ist ein");
		    Display.getDisplay(LoroDux.getInstance()).vibrate(50);
		}
		break;

	    case 1:
		new SimpleInputView(this, "Umkreis in Metern", 4,
			TextField.NUMERIC);
		break;

	    case 2:
		new SimpleInputView(this, "Intervall in Millisekunden", 4,
			TextField.NUMERIC);
		break;

	    case 3:
		new SimpleInputView(this, "Verzögerung in Sekunden", 2,
			TextField.NUMERIC);
		break;

	    case 4:
		new OptionsDirectionView(this);
		break;
	    case 5:
		if (OptionsStore.internalGps) {
		    OptionsStore.internalGps = false;
		    AlertManager.displayInfo("Bluetooth Gps. Starten Sie LoroDux erneut, damit dies wirksam wird.");
		} else {
		    OptionsStore.internalGps = true;
		    AlertManager.displayInfo("Internes Gps aktiviert. Starten Sie LoroDux erneut, damit dies wirksam wird. Durch die Nutzung des Internen Gps koennen auf manchen Telefonen Internetkosten entstehen.");
		    Display.getDisplay(LoroDux.getInstance()).vibrate(50);
		}
		break;

	    default:
		break;

	    }
	} else if (c == BACK) {
	    LoroDux.showView(LoroDux.MAINVIEW);
	}

    }

}
