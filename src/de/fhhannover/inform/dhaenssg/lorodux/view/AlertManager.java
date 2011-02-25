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
 * AlertManager
 * This class handles all alerts and dialogs implemented using  alert.
 * This is necessary to avoid the undocumented
 * "Alert can not revert to Alert"-Exception.
 * 
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;

public class AlertManager {

    /**
     * Shows an alert using the J2Me Alert class
     * 
     * @param head
     *            Title of the alert
     * @param body
     *           Body of the alert
     */
    public static void displayAlarm(final String head, final String body) {
	final Alert alarm = new Alert(head, body, null, AlertType.ALARM);
	alarm.setTimeout(3000);
	waitForOtherAlertToBeFinished();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(alarm);
    }

    public static void displayInfo(final String body) {
	final Alert info = new Alert("Info", body, null, AlertType.INFO);
	info.setTimeout(3000);
	waitForOtherAlertToBeFinished();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(info);
    }

    public static void displayError(final String head, final String body) {
	final Alert error = new Alert(head, body, null, AlertType.ERROR);
	error.setTimeout(5000);
	waitForOtherAlertToBeFinished();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(error);
    }

    public static void displayWarning(final String head, final String body) {
	final Alert warning = new Alert(head, body, null, AlertType.WARNING);
	warning.setTimeout(3000);
	waitForOtherAlertToBeFinished();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(warning);
    }

    /**
     * Wait for other alerts to finish
     * If currently an alert is shown, wait 500 ms and try again.
     *
     */
    private static void waitForOtherAlertToBeFinished() {
	final Display display = Display.getDisplay(LoroDux.getInstance());
	
	// Probably, we should give up after a while?
	while (display.getCurrent().getClass() == Alert.class) {
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
	    }
	}
    }

}
