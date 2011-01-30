/* 
 * Copyright 2010 Daniel H‰nﬂgen (daniel.haenssgen@stud.fh-hannover.de)
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
 * FavoriteStore dient der permanenten Speicherung von Favoriten.
 * Genutzt wird der RecordStore von J2ME.
 * Import und Export-Funktionen werden folgen
 * 
 * @author Daniel H‰nﬂgen
 * 07.04.2010 stub-Klasse
 * 11.04.2010 Implementierung der Funktionen (ohne RMS)
 * 12.04.2010 Implementierung der Verwendung von RMS
 */

package de.fhhannover.inform.dhaenssg.lorodux.datastore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Favorite;

public class FavoriteStore {

    /**
     * Vector, der die Daten tempor‰r h‰lt.
     */
    private static Vector mStore = new Vector();

    private static RecordStore mRecordStore;

    /**
     * Name des RecordStores.
     */
    private static String mRSName = "Favorites";

    /**
     * Stelle des aktuell verwendeten Favoriten.
     */
    private static int mCurrentFavoriteIndex;

    /**
     * Referenz auf aktuell verwendeten Favoriten.
     */
    private static Favorite mCurrentFavorite;

    /**
     * Methode holt Daten aus dem RecordStore und schreibt
     * diese in den oben deklarierten Vector. 
     * @throws RecordStoreFullException
     * @throws RecordStoreNotFoundException
     * @throws RecordStoreException
     * @throws IOException
     */
    public static void getFavoritesFromRecordStore()
	    throws RecordStoreFullException, RecordStoreNotFoundException,
	    RecordStoreException, IOException {
	mRecordStore = RecordStore.openRecordStore(mRSName, true);

	int size = mRecordStore.getNumRecords();
	for (int i = 0; i < size; i++) {
	    Favorite fav = new Favorite();
	    ByteArrayInputStream bais = new ByteArrayInputStream(mRecordStore
		    .getRecord(i + 1));
	    DataInputStream dis = new DataInputStream(bais);
	    
	    fav.setLat(dis.readFloat());
	    fav.setLon(dis.readFloat());
	    fav.setName(dis.readUTF());

	    mStore.addElement(fav);
	}
	mRecordStore.closeRecordStore();
    }

    /**
     * Speichert Vector in den RecordStore.
     */
    public static void storeFavoritesToRecordStore() {

	try {
	    RecordStore.deleteRecordStore(mRSName);
	} catch (RecordStoreNotFoundException e1) {
	    LoroDux.displayStringOnMainView(e1.toString());
	} catch (RecordStoreException e1) {
	    LoroDux.displayStringOnMainView(e1.toString());
	}
	
	try {
	    mRecordStore = RecordStore.openRecordStore(mRSName, true);
	} catch (RecordStoreFullException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (RecordStoreNotFoundException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (RecordStoreException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	for (int i = 0; i < mStore.size(); i++) {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    DataOutputStream dos = new DataOutputStream(baos);

	    Favorite fav = (Favorite) mStore.elementAt(i);

	    try {
		dos.writeFloat(fav.getLat());
		dos.writeFloat(fav.getLon());
		dos.writeUTF(fav.getName());
		byte[] byteData = baos.toByteArray();
		dos.close();
		mRecordStore.addRecord(byteData, 0, byteData.length);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (RecordStoreNotOpenException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (RecordStoreFullException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (RecordStoreException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

	try {
	    mRecordStore.closeRecordStore();
	} catch (RecordStoreNotOpenException e) {
	    LoroDux.displayStringOnMainView(e.toString());
	} catch (RecordStoreException e) {
	    LoroDux.displayStringOnMainView(e.toString());
	}
    }

    /**
     * F¸gt ein Favorite Object alphabetisch sortiert dem Vector hinzu. Als
     * Sortier-Algorithmus wird Insertionsort verwendet
     * 
     * @param Favorit-Objekt, der hinzugef¸gt werden soll.
     */
    public static boolean add(Favorite favorite) {
	if (mStore.size() == 0) {
	    mStore.addElement(favorite);
	    return true;
	} else {
	    int sizeBefore = mStore.size();
	    for (int i = 0; i < sizeBefore; i++) {
		int compare = ((Favorite) mStore.elementAt(i)).getName()
			.compareTo(favorite.getName());
		/* Falls aktueller Favorit schon existiert */ 
		if (compare == 0) {
		    mCurrentFavorite = favorite;
		    mCurrentFavoriteIndex = i;
		    return false;
		} else {
		    if (compare > 0) {
			mStore.insertElementAt(favorite, i);
			return true;
		    } else {
			if (compare < 0 && i == (sizeBefore - 1)) {
			    mStore.addElement(favorite);
			    return true;
			}
		    }
		}
	    }
	}
	/* Ganz anderer Fehler */
	return false;
    }

    /**
     * Methode wird aufgerufen, wenn in der add Methode festgestellt wurde,
     * dass ein Favorit schon existiert und dieser ersetzt werden soll.
     */
    public static void replaceCurrent() {
	mStore.setElementAt(mCurrentFavorite, mCurrentFavoriteIndex);
    }

    /**
     * Entfernt Favoriten aus Vector.
     * @param Index des Objects, welches entfernt werden soll.
     */
    public static void remove(int index) {
	mStore.removeElementAt(index);
    }

    /**
     * @return Kompletter Favoriten-Vector.
     */
    public static Vector get() {
	return mStore;
    }

}
