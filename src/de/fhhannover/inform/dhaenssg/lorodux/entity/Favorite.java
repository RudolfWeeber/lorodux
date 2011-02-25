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
 * Einfache Klasse zum Speichern von Favoriten-Daten
 * 
 * @author Daniel Hänßgen
 * Version: 0.1
 * 07.04.2010
 * Version: 0.2
 * 12.04.2010 - Aus Coordiantes zwei double Felder gemacht.
 */

package de.fhhannover.inform.dhaenssg.lorodux.entity;

public class Favorite {

    private String mName;
    private float mLat;
    private float mLon;

    public Favorite() {
    }

    public Favorite(final String name, final float lat, final float lon) {
	mName = name;
	mLat = lat;
	mLon = lon;
    }
    
    public String getName() {
	return mName;
    }

    public void setName(String name) {
	mName = name;
    }

    public float getLat() {
	return mLat;
    }
    
    public void setLat(final float lat) {
	mLat = lat;
    }

    public float getLon() {
	return mLon;
    }
    
    public void setLon(final float lon){
	mLon = lon;
    }

    public void setCoordinates(final float lat, final float lon) {
	mLat = lat;
	mLon = lon;
    }
    
}
