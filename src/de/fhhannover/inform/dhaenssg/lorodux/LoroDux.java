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
 * LoroDux - Navigationssystem für Blinde
 * 
 * @author Daniel Hänßgen
 * 
 * Dies ist das MIDlet.
 * Von hier wird die Anwenund gestartet.
 * 
 * Datum: 26.03.2010
 * 
 * 26.03.2010 - Implementierung von Views
 * 11.04.2010 - Erweiterung Command um FavoriteView
 * 05.05.2010 - NearByView hinzugefügt
 * 16.05.2010 - WhereAmIView hinzugefügt
 **/

package de.fhhannover.inform.dhaenssg.lorodux;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

import de.fhhannover.inform.dhaenssg.lorodux.datastore.FavoriteStore;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.gps.BluetoothGpsReader;
import de.fhhannover.inform.dhaenssg.lorodux.osm.NodeManager;
import de.fhhannover.inform.dhaenssg.lorodux.osm.Tags;
import de.fhhannover.inform.dhaenssg.lorodux.view.*;

public class LoroDux extends MIDlet {

    /**
     * Definition von Konstanten für die Adressierung der Views.
     */
    public static final int MAINVIEW = 0;
    public static final int GPSVIEW = 1;
    public static final int WHEREAMIVIEW = 2;
    public static final int FAVORITEVIEW = 3;
    public static final int NEARBYVIEW = 4;
    public static final int SEARCHVIEW = 5;
    public static final int OPTIONSVIEW = 6;

    /**
     * Singleton-Implementierung Deklaration
     */
    private static LoroDux instance;

    /**
     * Referenz auf BluetoothGPS-Gerät
     */
    private static boolean mBluetoothConnected;
    private static BluetoothGpsReader reader;

    /**
     * Deklaration der Views.
     */
    private static MainView mMainView;
    private static GpsView mGpsView;
    private static WhereAmIView mWhereAmIView;
    private static FavoriteManageView mFavoriteView;
    private static NearByView mNearByView;
    private static OptionsView mOptionsView;
    private static SearchView mSearchView;

    /**
     * Methode, die beim Beenden aufgerufen wird.
     * Dient der persisten Speicherung von Favoriten und Optionen,
     * sowie das Beenden der Verbindung zum Bluetooth-GPS-Empfänger.
     * Zum Schluss wird das MIDlet zerstört. 
     */
    public void exit() {
	FavoriteStore.storeFavoritesToRecordStore();
	OptionsStore.storeOptions();
	try {
	 reader.shutdown();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    notifyDestroyed();
	}
    }

    /**
     * threadsafe Singleton
     * @return Instanz von LoroDux(MIDlet)
     */
    public static LoroDux getInstance() {
	synchronized (instance) {
	    return instance;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
     */
    protected void destroyApp(boolean unconditional)
	    throws MIDletStateChangeException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.microedition.midlet.MIDlet#pauseApp()
     */
    protected void pauseApp() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.microedition.midlet.MIDlet#startApp()
     */
    protected void startApp() throws MIDletStateChangeException {
	// new LicenseAgreement(this);
	try {
	    FavoriteStore.getFavoritesFromRecordStore();
	    OptionsStore.restoreOptions();
	} catch (RecordStoreFullException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (RecordStoreNotFoundException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (RecordStoreException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	new Tags();

	InputStream is = getClass().getResourceAsStream("/nodes");
	try {
	    NodeManager.loadNodes(is);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	 discoverGPS();

//	Position pos = new Position();
//	pos.setCoordinates(52.390961f, 9.741852f);
//	pos.setHeading((short) 0);
//	pos.setDate("120000", "050510");
//	pos.setSpeed(0.0f);
//	pos.setStatus(true);
//	ActualPosition.setPosition(pos);

    }

    /**
     * Konstruktor des MIDlets.
     * Instanziiert auch alle Views.
     */
    public LoroDux() {
	instance = this;
	mMainView = new MainView(null);
	mGpsView = new GpsView();
	mWhereAmIView = new WhereAmIView();
	mFavoriteView = new FavoriteManageView();
	mNearByView = new NearByView();
	mSearchView = new SearchView();
	mOptionsView = new OptionsView();

    }

    /**
     * Standard Methode um einfachen Text auf MainView darzustellen
     * @param text
     */
    public static void displayStringOnMainView(String text) {
	mMainView.setBody(text);
    }

    /**
     * View auf Display anzeigen.
     * @param Integer, der View repräsentiert
     */
    public static synchronized void showView(int view) {
	switch (view) {
	case (MAINVIEW):
	    mMainView.show();
	    break;

	case (GPSVIEW):
	    mGpsView.show();
	    break;

	case (FAVORITEVIEW):
	    mFavoriteView.show();
	    break;

	case (WHEREAMIVIEW):
	    mWhereAmIView.show();
	    break;

	case (NEARBYVIEW):
	    mNearByView.show();
	    break;

	case (SEARCHVIEW):
	    mSearchView.show();
	    break;

	case (OPTIONSVIEW):
	    mOptionsView.show();
	    break;

	}
    }

    /**
     * Methode, um das neuaufsuchen eines Bluetooth-Gerätes anzustoßen.
     */
    public static void rediscoverGpsDevice() {
	reader.resetToCleanStatus();
	reader.discoverGPS();
    }

    /**
     * Methode um Bluetooth-GPS aufzusuchen.
     */
    public static void discoverGPS() {

	/*
	 * If we haven't got an integrated GPS this will throw an Exception
	 * quickly
	 */
	// try {
	// mMainView.setText("GPS-Info", "Suche nach integriertem Empfänger");
	// reader = new IntegratedGPSReader();
	// }
	// catch(Throwable t) {
	mMainView.setText("GPS-Info", "Suche nach Bluetooth-Empfänger");
	reader = new BluetoothGpsReader();
	// }
	// reader.resetToCleanStatus();
	reader.discoverGPS();
    }

    public static boolean isBluetoothConnected() {
	return mBluetoothConnected;
    }

    public static void setBluetoothConnected(boolean bluetoothConnected) {
	mBluetoothConnected = bluetoothConnected;
    }
}
