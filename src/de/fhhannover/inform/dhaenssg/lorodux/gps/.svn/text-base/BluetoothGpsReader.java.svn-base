/* LocateMe
 * Copyright © 2009 Silent Software (Benjamin Brown)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/cpl1.0.php
 * 
 */

/**
 * Diese Klasse gehört zur LoroDux MIDlet Suite.
 * 
 * Adaptiert und angepasst im Original von Benjamin Brown.
 * Änderungen durch die Verwendung der deutschen Sprache und
 * durch die Verwendung von LoroDux-Klassen kenntlich gemacht.
 * Datum: 30.03.2010
 */

package de.fhhannover.inform.dhaenssg.lorodux.gps;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.Config;
import de.fhhannover.inform.dhaenssg.lorodux.view.AlertManager;
import de.fhhannover.inform.dhaenssg.lorodux.view.BluetoothListView;

/**
 * Simple class to associate with a Bluetooth GPS and read the NMEA data in,
 * making a call back to the current displayed view TODO: Extend from a
 * GPSReader interface so built in hardware GPS can be used instead
 */
public class BluetoothGpsReader implements GpsReader, DiscoveryListener {

    /**
     * The bluetooth discovery agent
     */
    private DiscoveryAgent da;

    /**
     * Services transaction search id
     */
    private int transactionId = -1;

    /**
     * Flag to close the bluetooth connection
     */
    private boolean shutdown = false;

    /**
     * Sleep between reads - so the mobile device isn't overloaded
     */
    private long READ_SLEEP = 10;

    /**
     * Wait until the stream is ready to read
     */
    private long READY_WAIT = 10000;

    /**
     * Discovered Bluetooth remote devices
     */
    private Vector rds;

    /**
     * Discovered Bluetooth remote devices names
     */
    private Vector rdNames;

    /**
     * Discovered Bluetooth remote devices
     */
    private ServiceRecord service;

    /**
     * The user selectable list of devices
     * 
     * @uml.property name="listView"
     * @uml.associationEnd
     */
    private BluetoothListView listView;

    /**
     * UUID for a serial port
     */
    private static final UUID[] uuid = new UUID[] { new UUID(0x1101) };

    /**
     * The selected GPS device's name
     */
    private static String selectedDeviceName;

    /**
     * Default constructor with a view (data renderer) argument
     */
    public BluetoothGpsReader() {
	listView = new BluetoothListView(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @seejavax.bluetooth.DiscoveryListener#deviceDiscovered(javax.bluetooth.
     * RemoteDevice, javax.bluetooth.DeviceClass)
     */
    public void deviceDiscovered(RemoteDevice rd, DeviceClass dc) {
	try {
	    String deviceName = rd.getFriendlyName(false);
	    if (deviceName.trim().length() != 0) {
		rds.addElement(rd);
		rdNames.addElement(deviceName);
		AlertManager.displayInfo("Gerät gefunden " + deviceName);
	    }
	} catch (Exception io) {
	    // Failed discovering one device - not worthy of an alert
	    // LoroDux.showInfo("Gerät nicht gefunden", io.getMessage());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.bluetooth.DiscoveryListener#inquiryCompleted(int)
     */
    public void inquiryCompleted(int discType) {
	transactionId = -1;
	if (discType != INQUIRY_COMPLETED)
	    return;
	if (rds.size() == 0) {
	    AlertManager.displayError("Fehler", "Kein GPS-Gerät gefunden");
	    resetToCleanStatus();
	    return;
	}
	String lastDeviceName = getLastDeviceName();
	// LoroDux.showInfo("Geräte gefunden", "Anzahl "+rds.size());
	if (lastDeviceName != null && lastDeviceName.trim().length() > 0) {
	    // LoroDux.showInfo("GPS-Info", "Zuletzt benutzt: " +
	    // lastDeviceName);
	    LoroDux.displayStringOnMainView("Zuletzt benutzt: "
		    + lastDeviceName);
	    selectDevice(lastDeviceName);
	} else {
	    // try{LoroDux.showInfo("GPS-Info",
	    // "Kein zuletzt benutztes Geräte gefunden");}catch(Exception e){}
	    listView.displayList(rdNames);
	}
    }

    /**
     * Returns the BT name of the last GPS that was connected to
     * 
     * @return
     */
    public String getLastDeviceName() {

	try {
	    return Config.getProperty(Config.GPS_DEVICE_NAME_INDEX);
	} catch (Exception e) {
	    /*
	     * Diese Ausgabe bringt nur den Screenreader durcheinander! 
	     */
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.bluetooth.DiscoveryListener#servicesDiscovered(int,
     * javax.bluetooth.ServiceRecord[])
     */
    public void servicesDiscovered(int transID, ServiceRecord[] sr) {
	if (service == null) {
	    service = sr[0];
	}
    }

    /**
     * Resets the Bluetooth discovery transaction id, whether BT is still
     * connected, and blanks the device name in the configuration. Typically
     * used when a GPS connection fails.
     */
    public void resetToCleanStatus() {
	transactionId = -1;
	LoroDux.setBluetoothConnected(false);
	try {
	    Config.setProperty(Config.GPS_DEVICE_NAME_INDEX, "");
	} catch (Exception e) {
	    AlertManager.displayError("GPS-Error",
		    "Konnte ungültiges Gerät nicht löschen");
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.bluetooth.DiscoveryListener#serviceSearchCompleted(int, int)
     */
    public void serviceSearchCompleted(int arg0, int respCode) {
	// LoroDux.showInfo("GPS Suche beendet", null);
	if (respCode != DiscoveryListener.SERVICE_SEARCH_COMPLETED) {
	    AlertManager.displayError("GPS-Error", "Konnte kein GPS-Dienst ausmachen");
	    // LocateMe.showAlert("Unable to connect to GPS service");
	    resetToCleanStatus();
	    return;
	}
	// LoroDux.showInfo("GPS-Info", "Versuche GPS-Dienst...");
	// LocateMe.pushDataToView("Trying GPS service...");
	String url = ((ServiceRecord) service).getConnectionURL(0, false);
	readData(url);
    }

    /**
     * Connect to the chosen BT GPS
     * 
     * @param index
     */
    public void selectDevice(int index, String name) {
	selectedDeviceName = name;
	RemoteDevice rd = (RemoteDevice) (rds.elementAt(index));
	try {
	    // LoroDux.showInfo("Bluetooth", "Erforsche Dienste");
	    // LocateMe.pushDataToView("Getting services");
	    transactionId = da.searchServices(null, uuid, rd, this);
	} catch (Exception e) {
	    AlertManager.displayError("Schwerer Fehler",
		    "Verbindung beim Dienste suchen gescheitert");
	    // LocateMe.showAlert("Connection failed getting services");
	}
    }

    /**
     * Connect to the chosen Bluetooth GPS
     * 
     * @param name
     */
    public void selectDevice(String name) {
	try {
	    for (int i = 0; i < rdNames.size(); i++) {
		if (((String) rdNames.elementAt(i)).equals(name)) {
		    selectDevice(i, name);
		    return;
		}
	    }
	    AlertManager.displayError("Bluetooth-Error",
		    "Konnte zuletzt benutztes Gerät nicht finden");
	    // LocateMe.showAlert("Unable to find last used device");
	    resetToCleanStatus();
	} catch (Exception e) {
	    AlertManager.displayError("Verbindung gescheitert!", null);
	    // LocateMe.showAlert("Connection failed");
	}
    }

    /**
     * Saves the last Bluetooth device name to the configuration
     */
    private void saveConfig() {
	try {
	    Config
		    .setProperty(Config.GPS_DEVICE_NAME_INDEX,
			    selectedDeviceName);
	} catch (Exception e) {
	    AlertManager.displayError("Schwerer Fehler", "Konnte letztes GPS Gerät nicht speichern");
	    // LocateMe.pushDataToView("Unable to open config db");
	}
    }

    /**
     * Data reading method itself - reads a line of data one character at a
     * time, then sends it to the datarenderer. This method also has some
     * resilience and rediscovers the bluetooth remote device (GPS) if it
     * crashes
     * 
     * TODO: Reading a character at a time is inefficient - consider using a
     * larger char buffer/seek etc.
     * 
     * @param url
     */
    private void readData(String url) {
	saveConfig();
	InputStreamReader ir = null;
	StreamConnection c = null;
	InputStream is = null;
	try {
	    c = (StreamConnection) Connector.open(url);
	    is = c.openInputStream();
	    ir = new InputStreamReader(is);
	    long startWait = System.currentTimeMillis();
	    while (!ir.ready()) {
		Thread.sleep(READ_SLEEP);
		if (System.currentTimeMillis() - startWait > READY_WAIT) {
		    throw new InterruptedException("Wait time exceeded");
		}
	    }
	    StringBuffer buf = new StringBuffer();
	    char data;
	    AlertManager.displayInfo("Warten auf gültigen GPS Empfang");
	    new CheckGps(); /* Änderung durch dhaenssg */
	    while ((data = (char) ir.read()) != 1 && shutdown != true) {
		if ('$' == data) {
		    try {
			NMEAParser.process(buf.toString()); /* Änderung durch dhaenssg*/
			Thread.sleep(READ_SLEEP);
		    } catch (Exception e) {
			LoroDux.displayStringOnMainView("readData: "
				+ e.toString());
		    }
		    buf = new StringBuffer();
		}
		buf.append(data);
	    }
	} catch (Exception e) {
	    resetToCleanStatus();
	    AlertManager.displayError("Bluetooth-Error", "Bluetooth Verbindung abgebrochen");
	    // LocateMe.showAlert("Connection failed");
	} finally {
	    LoroDux.setBluetoothConnected(false);
	    transactionId = -1;
	    if (c != null) {
		try {
		    c.close();
		} catch (Throwable t) {
		}
	    }
	    if (is != null) {
		try {
		    is.close();
		} catch (Throwable t) {
		}
	    }
	    if (ir != null) {
		try {
		    ir.close();
		} catch (Throwable t) {
		}
	    }
	}
    }

    /**
     * Simple method to search for any nearby bluetooth device and initiate
     * connection (if it has a serial port - see inquiryCompleted method)
     */
    public synchronized void discoverGPS() {
	try {
	    if (transactionId == -1) {
		LoroDux.setBluetoothConnected(true);
		rds = new Vector();
		rdNames = new Vector();
		service = null;
		selectedDeviceName = null;
		da = LocalDevice.getLocalDevice().getDiscoveryAgent();
		da.startInquiry(DiscoveryAgent.GIAC, this);
	    }
	} catch (BluetoothStateException bse) {
	    // LoroDux.showError("Bluetooth-Error",
	    // "Unfähig ein Bluetooth-Gerät zu benutzen");
	    // LocateMe.showAlert("Unable to use a bluetooth device");
	    LoroDux.displayStringOnMainView("BSE: " + bse.getMessage());
	    // LocateMe.pushDataToView("BSE: "+bse.getMessage());
	}
    }

    /**
     * Flag to shutdown the reader and disconnect the socket
     */
    public void shutdown() {
	shutdown = true;
	if (da != null) {
	    try {
		da.cancelInquiry(this);

	    } catch (Exception e) {
		LoroDux.displayStringOnMainView(e.toString());
	    }
	}
	if (transactionId != -1) {
	    try {
		da.cancelServiceSearch(transactionId);
	    } catch (Throwable t) {
	    }
	}
	if (Config.isOpen()) {
	    Config.close();
	}
    }
}