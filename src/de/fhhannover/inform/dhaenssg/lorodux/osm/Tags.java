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
 * Sie dient dem Einlesen der Tags.
 * 
 * @author Daniel Hänßgen
 * Datum: 12.05.2010
 */

package de.fhhannover.inform.dhaenssg.lorodux.osm;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.fhhannover.inform.dhaenssg.lorodux.util.BufferedReader;

public class Tags {

    /**
     * Deklaration des String-Arrays, das die Bezeichnung der Tags enthält.
     */
    public static final String[] ELEMENT = new String[200];

    /**
     * Konstruktor, der die Tags ausliest und in das String-Array schreibt.
     * Leider nicht als static implementierbar, da getClass().getResourceAsStream()
     * nicht in einer statischen Methode funktioniert.
     */
    public Tags() {
	InputStream is = getClass().getResourceAsStream("/Tags");
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader in = new BufferedReader(isr);
	try {
	    for (int i = 0; i < ELEMENT.length; i++) {
		ELEMENT[i] = in.readLine();
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
