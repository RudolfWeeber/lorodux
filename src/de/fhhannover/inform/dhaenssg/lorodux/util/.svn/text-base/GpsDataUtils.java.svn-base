/* LocateMe
 * Copyright © 2009 Silent Software (Benjamin Brown)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/cpl1.0.php
 */

/**
 * Diese Klasse gehört zur LoroDux MIDlet Suite.
 * Adaptiert von LocateMe (siehe nächster Kommentar).
 * 
 * @author Daniel Hänßgen
 */
package de.fhhannover.inform.dhaenssg.lorodux.util;

/**
 * Helper class specific to LocateMe data structures
 * to process and format GPS data into other types
 */
public class GpsDataUtils {
	
	/**
	 * Utility method to convert the NMEA formatted coords 
	 * into pure degree coords (no minutes/seconds)
	 *
	 * The format is suitable for mapping websites such
	 * as streetmap.co.uk
	 * 
	 * @param position
	 * @param direction
	 * @return
	 */
	public static float convertToDegree(String position, String direction) throws IllegalArgumentException
    {
        int ptIndex = position.indexOf(".");
        if (ptIndex == -1) {
        	throw new IllegalArgumentException("Decimal point not found in String");
        }
        String degrees = position.substring(0, ptIndex - 2);
        String minutes = position.substring(ptIndex - 2);
        
        // This gives us the minutes as a fraction of a degree
        float decMinutes = Float.parseFloat(minutes) / 60;
        
        // Append the value to the full degrees
        float result = Float.parseFloat(degrees) + decMinutes;
        direction = direction.toUpperCase();
        
        // For correct positioning we need to specify orientation (i.e.
        // add a negative if West or South
        if (direction.startsWith("W") || direction.startsWith("S"))
            result *= -1;
        return result;
    }
}
