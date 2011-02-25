/* LocateMe
 * Copyright © 2009 Silent Software (Benjamin Brown)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/cpl1.0.php
 */

/**
 * Diese Klasse ist Teil der LoroDux MIDlet Suite.
 * 
 * Im Original von Benjamin Brown.
 * Adaptiert am 14.04.2010 von Daniel Hänßgen
 * Package-Struktur geändert.
 */
package de.fhhannover.inform.dhaenssg.lorodux.datastore;

import javax.microedition.rms.RecordStore;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;

/**
 * Wrapper class over a RecordStore for holding LocateMe's config
 */
public class Config {

	/**
	 * The index into the record store for the GPS device name
	 */
	public final static int GPS_DEVICE_NAME_INDEX = 1;
	
	/**
	 * The index into the record store for the last target
	 */
	public final static int LAST_LOCATION_INDEX = 2;
	
	/**
	 * The locateme record store name
	 */
	private final static String LOCATEME_DB = "locateMePrefs";
	
	/**
	 * The instance of the record store
	 */
	private static RecordStore db;
	
	/**
	 * A flag to indicate whether the record store is currently open
	 * @uml.property  name="isOpen"
	 */
	private static boolean isOpen = false;
	
	
	/**
	 * Opens the record store for access
	 * 
	 * @throws Exception
	 */
	public static synchronized void open() throws Exception {
		db = RecordStore.openRecordStore(LOCATEME_DB, true);
		isOpen = true;
	}
	
	/**
	 * Returns the record store's open state
	 * @return
	 * @uml.property  name="isOpen"
	 */
	public static boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * Closes the record store
	 */
	public static synchronized void close() {
		if (!isOpen)
			return;
		try {
			db.closeRecordStore();
			isOpen = false;
		} catch(Exception e){
			LoroDux.displayStringOnMainView(e.toString());
			}
	}
	
	/**
	 * Retrieves a property from the record store, e.g. last connected
	 * GPS name. Uses the final int keys defined in this class
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getProperty(int name) throws Exception{
		try {
			if (!Config.isOpen()) {
				Config.open();
			}
			byte b[] = db.getRecord(name);
			return new String(b,0,b.length);
		} finally {
			Config.close();
		}
	}
	
	/**
	 * Retrieves a property into the record store, e.g. last connected
	 * GPS name. Uses the final int keys defined in this class
	 * 
	 * @param name
	 * @param value
	 * @throws Exception
	 */
	public static synchronized void setProperty(int name, String value) throws Exception {
		try {
			if (!Config.isOpen()) {
				Config.open();
			}
			boolean recordExists = false;
			byte bytes[];
			try {
				bytes = db.getRecord(name);
				recordExists = true;
			} catch(Exception e) {}
			bytes = value.getBytes();
			if (recordExists) {
				db.setRecord(name, bytes, 0, bytes.length);
			} else {
				db.addRecord(bytes,0,bytes.length);
			}
		} finally {
			Config.close();
		}
	}
}
