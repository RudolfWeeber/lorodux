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
 * Sie dient der permanenten Speicherung der im Optionen-Menü
 * eingestellten Daten. 
 * 
 * @author Daniel Hänßgen
 * 
 * 09.05.2010 - Erste Implementierung
 * 11.05.2010 - um GPSLostAllowed erweitert
 * 22.05.2010 - chosenDirectionString erweitert.
 */

package de.fhhannover.inform.dhaenssg.lorodux.datastore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

public class OptionsStore {

    /**
     * Name des RecordStores
     */
    private static final String RSNAME = "Options";

    public static boolean vibrate = false;
    
    public static int radius = 100;
    public static int timeAutoRefresh = 5000;
    public static int timeGpsLostAllowed = 10000; 
    public static int chosenDirectionString = 0;
    public static boolean internalGps  = false;

    /**
     * Methode, die den Optionen-Vector in den RecordStore speichert.
     */
    public static void storeOptions() {
	deleteOptionsStore();
	saveOptionsStore();

    }

    private static void saveOptionsStore() {
	try {
	    RecordStore rs = RecordStore.openRecordStore(RSNAME, true);

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    DataOutputStream dos = new DataOutputStream(baos);

	    dos.writeBoolean(vibrate);
	    dos.writeInt(radius);
	    dos.writeInt(timeAutoRefresh);
	    dos.writeInt(timeGpsLostAllowed);
	    dos.writeInt(chosenDirectionString);
	    dos.writeBoolean(internalGps);

	    byte[] byteData = baos.toByteArray();
	    dos.close();
	    rs.addRecord(byteData, 0, byteData.length);

	    rs.closeRecordStore();
	} catch (RecordStoreFullException e) {
	    e.printStackTrace();
	} catch (RecordStoreNotFoundException e) {
	    e.printStackTrace();
	} catch (RecordStoreException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static void deleteOptionsStore() {
	try {
	    RecordStore.deleteRecordStore(RSNAME);
	} catch (RecordStoreNotFoundException e1) {
	    e1.printStackTrace();
	} catch (RecordStoreException e1) {
	    e1.printStackTrace();
	}
    }

    /**
     * Liest die Optionen aus dem RecordStore und schreibt diese
     * in die oben deklarierten Attribute.
     */
    public static void restoreOptions() {
	try {
	    RecordStore rs = RecordStore.openRecordStore(RSNAME, false);

	    ByteArrayInputStream bais = new ByteArrayInputStream(rs
		    .getRecord(1));
	    DataInputStream dis = new DataInputStream(bais);

	    vibrate = dis.readBoolean();
	    radius = dis.readInt();
	    timeAutoRefresh = dis.readInt();
	    timeGpsLostAllowed = dis.readInt();
	    chosenDirectionString = dis.readInt();
	    internalGps = dis.readBoolean();

	    
	    rs.closeRecordStore();
	} catch (RecordStoreFullException e) {
	    e.printStackTrace();
	} catch (RecordStoreNotFoundException e) {
	    e.printStackTrace();
	} catch (RecordStoreException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
