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
 * Position beinhaltet Calendern wie Längen und Breitengrad, sowie Richtung etc.
 * 
 * Methoden zum setzen der Attribute direkt, sowie "AsString" Methoden, um
 * Daten direkt irgendwo darstellen zu können.
 * 
 * @author Daniel Hänßgen
 * 
 * Version 0.1
 * 01.04.2010	Erste Implementierung - long,lat,heading,date,speed
 * Version 0.2
 * 12.04.2010	Coordinates durch zwei double Werte ersetzt.
 * 				copy()-Methode eingeführt
 * Version 0.3
 * 10.05.2010 Anzahl Steliiten und HDOP wird gespeichert.
 */

package de.fhhannover.inform.dhaenssg.lorodux.entity;

import java.util.Date;
import java.util.Calendar;

import de.fhhannover.inform.dhaenssg.lorodux.util.MathSupport;

public class Position {

    private Calendar mDate;
    private float mLat;
    private float mLon;
    private float mSpeed;
    private short mHeading;
    private byte mSatellites;
    private float mHDOP;
    private boolean mStatus;
    private String mInfo;
   

    public Position() {
	mDate = new Calendar() {
	    protected void computeTime() {
	    }
	    protected void computeFields() {
	    }
	};
	mLat = 0.0f;
	mLon = 0.0f;
	mSpeed = 0.0f;
	mHeading = 0;
	mSatellites = 0;
	mHDOP = -1;
	mStatus = false;
    }

    /**
     * Methode baut neues Position-Objekt,
     * kopiert die Attribute und gibt dieses zurück.
     * @return kopie des aktuellen Positions-Objekts.
     */
    public Position copy() {
	Position newPosition = new Position();
	newPosition.mHeading = mHeading;
	newPosition.mDate = mDate;
	newPosition.mLat = mLat;
	newPosition.mLon = mLon;
	newPosition.mSpeed = mSpeed;
	newPosition.mSatellites = mSatellites;
	newPosition.mHDOP = mHDOP;
	newPosition.mStatus = mStatus;
        newPosition.mInfo = mInfo;
	return newPosition;
    }

    public Calendar getDate() {
	return mDate;
    }

    public String getDateAsString() {
	return mDate.get(Calendar.DAY_OF_MONTH) + "."
		+ mDate.get(Calendar.MONTH) + "." + mDate.get(Calendar.YEAR)
		+ "  " + mDate.get(Calendar.HOUR_OF_DAY) + ":"
		+ mDate.get(Calendar.MINUTE) + ":" + mDate.get(Calendar.SECOND);
    }

    public void setDate(String hhmmss, String ddmmyy) {
	mDate.set(Calendar.HOUR_OF_DAY, Integer
		.parseInt(hhmmss.substring(0, 2)));
	mDate.set(Calendar.MINUTE, Integer.parseInt(hhmmss.substring(2, 4)));
	mDate.set(Calendar.SECOND, Integer.parseInt(hhmmss.substring(4, 6)));
	mDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ddmmyy
		.substring(0, 2)));
	mDate.set(Calendar.MONTH, Integer.parseInt(ddmmyy.substring(2, 4)));
	mDate.set(Calendar.YEAR, Integer.parseInt(ddmmyy.substring(4, 6)));
    }
    
    public void setDateMs(long timeInMs) {
      Date d = new Date(timeInMs);
      mDate.setTime(d);
    }

    public float getLat() {
	return mLat;
    }

    public void setInfo(String info) {
      mInfo=info;
    }

    public String getInfo() {
      return mInfo;
    }


    public float getLon() {
	return mLon;
    }

    public String getCoordinatesAsString() {
	return "Breitengrad: " + mLat + " \nLängengrad: " + mLon;
    }

    public void setCoordinates(float lat, float lon) {
	mLat = (float) (MathSupport.round(lat * 1000000.0) / 1000000.0);
	mLon = (float) (MathSupport.round(lon * 10000000.0) / 10000000.0);
    }

    public float getSpeed() {
	return mSpeed;
    }

    public void setSpeed(final float speed) {
	mSpeed = speed;
    }

    public short getHeading() {
	return mHeading;
    }

    public void setHeading(final short heading) {
	mHeading = heading;
    }

    public byte getSatellites() {
	return mSatellites;
    }

    public void setSatellites(final byte satellites) {
	mSatellites = satellites;
    }

    public float getHDOP() {
	return mHDOP;
    }

    public void setHDOP(final float hdop) {
	mHDOP = hdop;
    }

    public boolean getStatus() {
	return mStatus;
    }

    public void setStatus(boolean status) {
	mStatus = status;
    }

}
