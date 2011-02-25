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
 * Sie überprüft periodisch, ob ein GPS Signal vorhanden ist, und gibt
 * ggf. Alerts aus.
 * 
 * @author Daniel Hänßgen
 * Datum: 10.05.2010
 * 
 * Datum: 16.05.2010
 * Bugfix: unnötige Mehrfachnennung von "GPS bereit" entfernt
 */

package de.fhhannover.inform.dhaenssg.lorodux.gps;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;
import de.fhhannover.inform.dhaenssg.lorodux.view.AlertManager;

public class CheckGps extends Thread {

    private long mTimeSinceSignalLost;
    private boolean mSignalWasLostBefore = true;
    private boolean mLostSignalAlertAlreadyShown = true;

    public CheckGps() {
	this.start();
    }

    public void run() {
	Position pos;
	long timePassed;
	try {
	    while (true) {
		pos = ActualPosition.getPositionCopy();
		if (pos.getStatus() == false && mSignalWasLostBefore == false) {
		    mTimeSinceSignalLost = System.currentTimeMillis();
		    mSignalWasLostBefore = true;
		} else {
		    timePassed = System.currentTimeMillis()
			    - mTimeSinceSignalLost;
		    if (pos.getStatus() == false
			    && mSignalWasLostBefore == true
			    && timePassed >= OptionsStore.timeGpsLostAllowed
			    && mLostSignalAlertAlreadyShown == false) {
			AlertManager.displayError("Problem",
				"GPS Signal nicht verfügbar");
			mLostSignalAlertAlreadyShown = true;
		    } else {
			if (pos.getStatus() == true
				&& mSignalWasLostBefore == true
				&& mLostSignalAlertAlreadyShown == true) {
			    mSignalWasLostBefore = false;
			    mLostSignalAlertAlreadyShown = false;
			    AlertManager.displayInfo("GPS bereit");
			}
		    }
		}
		Thread.sleep(2000);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    LoroDux.displayStringOnMainView("CheckGPS: " + e.toString());
	}
    }

}
