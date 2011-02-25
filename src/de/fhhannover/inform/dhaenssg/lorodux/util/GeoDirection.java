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
 * Diese Klasse ist Teil der LoroDux MIDlet Suite.
 *
 * Dient der Umwandlung von Bearing in verschiedene Darstellungs-Optionen.
 * 
 * @author Daniel Hänßgen
 * 22.05.2010 möglichkeit der Internationalisierung eingebaut.
 */

package de.fhhannover.inform.dhaenssg.lorodux.util;

import javax.microedition.lcdui.Display;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;

public class GeoDirection {

    /**
     * Deklaration der Richtungsangaben.
     * Kann später durch andere Sprachen dynamisch ersetzt werden.
     */
    private static final String N = "Nord ";
    private static final String O = "Ost ";
    private static final String S = "Süd ";
    private static final String W = "West ";

    /**
     * Wandelt Bearing in Himmelsrichtung um.
     * @param bearing
     * @return Himmelsrichtung als String
     */
    public static String bearing2Cardinal(final int bearing) {
	final int bear = MathSupport.round(bearing / 11.25f);

	if (bear >= 0 && bear < 1) {
	    return N;
	} else if (bear >= 1 && bear < 3) {
	    return N + N + O;
	} else if (bear >= 3 && bear < 5) {
	    return N + O;
	} else if (bear >= 5 && bear < 7) {
	    return O + N + O;
	} else if (bear >= 7 && bear < 9) {
	    return O;
	} else if (bear >= 9 && bear < 11) {
	    return O + S + O;
	} else if (bear >= 11 && bear < 13) {
	    return S + O;
	} else if (bear >= 13 && bear < 15) {
	    return S + S + O;
	} else if (bear >= 15 && bear < 17) {
	    return S;
	} else if (bear >= 17 && bear < 19) {
	    return S + S + W;
	} else if (bear >= 19 && bear < 21) {
	    return S + W;
	} else if (bear >= 21 && bear < 23) {
	    return W + S + W;
	} else if (bear >= 23 && bear < 25) {
	    return W;
	} else if (bear >= 25 && bear < 27) {
	    return W + N + W;
	} else if (bear >= 27 && bear < 29) {
	    return N + W;
	} else if (bear >= 29 && bear < 31) {
	    return N + N + W;
	} else if (bear >= 31 && bear < 32) {
	    return N;
	} else {
	    return null;
	}

    }

    /**
     * Wandelt Bearing in Angabe auf der Uhr um
     * @param bearing
     * @return Relative Richtung auf der Uhr
     */
    public static int bearing2clockface(final int bearing) {
	int clock;
	if (bearing >= 0 && bearing < 15) {
	    clock = 12;
	} else if (bearing >= 15 && bearing < 45) {
	    clock = 1;
	} else if (bearing >= 45 && bearing < 75) {
	    clock = 2;
	} else if (bearing >= 75 && bearing < 105) {
	    clock = 3;
	} else if (bearing >= 105 && bearing < 135) {
	    clock = 4;
	} else if (bearing >= 135 && bearing < 165) {
	    clock = 5;
	} else if (bearing >= 165 && bearing < 195) {
	    clock = 6;
	} else if (bearing >= 195 && bearing < 225) {
	    clock = 7;
	} else if (bearing >= 225 && bearing < 255) {
	    clock = 8;
	} else if (bearing >= 255 && bearing < 285) {
	    clock = 9;
	} else if (bearing >= 285 && bearing < 315) {
	    clock = 10;
	} else if (bearing >= 315 && bearing < 345) {
	    clock = 11;
	} else if (bearing >= 345 && bearing < 360) {
	    clock = 12;
	} else {
	    clock = 0;
	}
	return clock;
    }

    /**
     * Abhängig von den eingestellten Optionen gibt diese Methode
     * die passende Richtungsangabe zurück.
     * @param bearing
     * @return Richtungs-String
     */
    public static String getChosenDirectionString(short bearing) {
	short heading = ActualPosition.getPosition().getHeading();
	String ret;
	switch (OptionsStore.chosenDirectionString) {
	case 0:
	    ret = bearing2clockface(GpsCalc.calcCourse(heading, bearing))
		    + " Uhr";
	    break;

	case 1:
	    ret = bearing2Cardinal(bearing);
	    break;

	case 2:
	    ret = GpsCalc.calcCourse(heading, bearing) + " Grad";
	    break;

	case 3:
	    ret = bearing + " Grad";
	    break;

	default:
	    ret = null;
	    break;
	}
	return ret;
    }

    /**
     * Abhängig vom Kurs lässt diese Methode das Handy vibrieren.
     * @param course
     * @throws InterruptedException
     */
    public static void vibrate(int course) throws InterruptedException {
	Display display = Display.getDisplay(LoroDux.getInstance());
	Thread.sleep(400);
	if (course <= 10 || course >= 350) {
	    display.vibrate(30);
	    Thread.sleep(230);
	    display.vibrate(30);
	} else {
	    if (course <= 190 && course >= 170) {
		display.vibrate(30);
		Thread.sleep(230);
		display.vibrate(30);
		Thread.sleep(230);
		display.vibrate(30);
	    } else {
		if (course >= 11 && course <= 169) {
		    display.vibrate(30);
		    Thread.sleep(230);
		    display.vibrate(course * 10);
		} else {
		    display.vibrate((360 - course) * 10);
		    Thread.sleep(((360 - course) * 10) + 200);
		    display.vibrate(30);
		}
	    }
	}
    }

}
