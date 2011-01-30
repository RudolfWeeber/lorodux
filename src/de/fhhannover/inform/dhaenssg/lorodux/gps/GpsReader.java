/* LocateMe
 * Copyright � 2009 Silent Software (Benjamin Brown)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/cpl1.0.php
 */

/**
 * Diese Klasse geh�rt zur LoroDux MIDlet Suite.
 * @author Daniel H�n�gen
 * �bernommen von Benjamin Brown.
 * Package-Struktur am 14.04.2010 angepasst.
 */
package de.fhhannover.inform.dhaenssg.lorodux.gps;

/**
 * Simple interface to allow different
 * GPS data readers to hook into LocateMe
 */
public interface GpsReader {
	public void discoverGPS();
	public void shutdown();
}
