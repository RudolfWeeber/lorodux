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
 * Dient zur einfachen Speicherung von OSMNodes.
 * 
 * @author Daniel Hänßgen
 * Datum 05.05.2010
 * 
 * Datum 16.05.2010
 * Erweitert um Adress und Description
 */

package de.fhhannover.inform.dhaenssg.lorodux.osm;

public class Node {

    private float mLat;
    private float mLon;
    private byte mTag;
    private String mName;
    private String mAddress;
    private String mDescription;

    private int mDistance;
    private short mBearing;

    public Node(float lat, float lon, byte tag, String name, String addr, String desc) {
	mLat = lat;
	mLon = lon;
	mTag = tag;
	mName = name;
	mAddress = addr;
	mDescription = desc;
    }

    public float getLat() {
	return mLat;
    }

    public float getLon() {
	return mLon;
    }

    public String getName() {
	return mName;
    }

    public void setDistance(final int distance) {
	mDistance = distance;
    }

    public int getDistance() {
	return mDistance;
    }

    public void setBearing(final short bearing) {
	mBearing = bearing;
    }

    public short getBearing() {
	return mBearing;
    }

    public int getTag() {
	return mTag + 128;
    }

    public String getAddress() {
	return mAddress;
    }

    public String getDescription() {
	return mDescription;
    }

}
