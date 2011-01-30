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
 * Sie dient dem synchonisierten Zugriff auf die aktuelle Position.
 * 
 * @author Daniel Hänßgen
 * Version: 0.1
 * 11.04.2010 - Implementierung
 */

package de.fhhannover.inform.dhaenssg.lorodux.entity;

public class ActualPosition {

	private static Position mPosition = new Position();

	public static Position getPosition() {
		synchronized (mPosition) {
			return mPosition;
		}
	}
	
	public static Position getPositionCopy(){
		synchronized (mPosition) {
			return mPosition.copy();
		}
	}

	public static void setPosition(Position position) {
		synchronized (mPosition) {
			mPosition = position;
		}
	}

}
