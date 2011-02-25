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
 * Diese Klasse geghört zur LoroDux MIDlet Suite.
 * Klasse zum Parsen von NMEA-Datenströmen
 * 
 * @author Daniel Hänßgen
 * Version: 0.1
 * 01.04.2010 - Implementierung
 * Version: 0.2
 * 12.04.2010 - Coordinates durch zwei double ersetzt
 * Version: 0.3
 * 13.03.2010 - Einmalige Ausgabe des GPS Fix auf MainView implementiert
 */

package de.fhhannover.inform.dhaenssg.lorodux.gps;

import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;
import de.fhhannover.inform.dhaenssg.lorodux.util.GpsDataUtils;
import de.fhhannover.inform.dhaenssg.lorodux.util.MathSupport;
import de.fhhannover.inform.dhaenssg.lorodux.util.StringUtils;

public class NMEAParser {

    private static final float KPH_PER_KNOT = 1.852f;
    private static boolean isConnected = false;

    public static void process(String data) {
	try {
	    if (data.startsWith("$GPRMC")) {
		final String[] s = StringUtils.split(data, ',');
		final Position pos = ActualPosition.getPosition();
		/*
		 * Setze den Vergleich von NMEA an der Stelle 2 mit A auf
		 * Status. A=Active, V=Void (wird ignoriert)
		 */
		if (s[2].equalsIgnoreCase("A")) {
		    isConnected = true;
		    pos.setStatus(isConnected);
		    // throw new Exception("Keine gültigen GPS Daten");
		} else {
		    isConnected = false;
		    pos.setStatus(isConnected);
		}

		if (s[8].trim().length() > 0) {
		    pos.setHeading((short) MathSupport.round(Float
			    .parseFloat(s[8])));
		} else {
		    pos.setHeading((short) 0);
		}

		if (s[7].trim().length() > 0) {
		    pos.setSpeed(Float.parseFloat(s[7]) * KPH_PER_KNOT);
		} else {
		    pos.setSpeed(0.0f);
		}

		/*
		 * Setze Uhrzeit und Datum
		 */
		pos.setDate(s[1], s[9]);

		pos.setCoordinates(GpsDataUtils.convertToDegree(s[3], s[4]),
			GpsDataUtils.convertToDegree(s[5], s[6]));

		/*
		 * Wenn zum ersten Mal gültige GPS Daten vorhanden sind wird
		 * diese Information auf dem MainView ausgegeben, aber nur ein
		 * einziges Mal. Dafür sorgt das Attribut isConnected.
		 */
//		if (!isConnected) {
//		    LoroDux.displayStringOnMainView("GPS bereit");
//		    isConnected = true;
//		}
	    }

	    if (data.startsWith("$GPGGA")) {
		final String[] s = StringUtils.split(data, ',');
		final Position pos = ActualPosition.getPosition();

		if (s[7].trim().length() > 0) {
		    pos.setSatellites(Byte.parseByte(s[7]));
		}

		if (s[8].trim().length() > 0) {
		    pos.setHDOP(Float.parseFloat(s[8]));
		}

	    }
	} catch (Exception e) {
	    e.printStackTrace();
//	    LoroDux.displayStringOnMainView("NMEAParser: " + e.toString());
	}
    }

}
