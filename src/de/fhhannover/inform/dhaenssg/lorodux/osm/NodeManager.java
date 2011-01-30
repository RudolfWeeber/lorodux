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
 * Sie dient der Verwaltung von Nodes, die aus der OSM stammen.
 * 
 * Datum: 03.05.2010
 * Implementierung getNodesNear und loadNodes
 * 
 * Datum: 16.05.2010
 * Implementierung getWhereAmI Methode
 */

package de.fhhannover.inform.dhaenssg.lorodux.osm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import de.fhhannover.inform.dhaenssg.lorodux.datastore.OptionsStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;
import de.fhhannover.inform.dhaenssg.lorodux.util.GpsCalc;

public class NodeManager {

    /**
     * Deklaration des Vector-Objekts, welches die Nodes hält.
     */
    private static Vector mNodes = new Vector();

    /**
     * Methode, die die Nodes aus dem InputStream is in den Vector mNodes liest.
     * 
     * @param is
     *            InputStream-Objekt, dass auf die Nodes-Datei verweist.
     * @throws IOException
     */
    public static void loadNodes(final InputStream is) throws IOException {
	final DataInputStream dis = new DataInputStream(is);

	final int nodeCount = dis.readInt();

	Node node;

	for (int i = 0; i < nodeCount; i++) {
	    node = new Node(dis.readFloat(), dis.readFloat(), dis.readByte(),
		    dis.readUTF(), dis.readUTF(), dis.readUTF());
	    mNodes.addElement(node);
	}
    }

//    public static void removeAll() {
//	mNodes = new Vector();
//    }

    /**
     * @param lat
     *            lat der Position um die gesucht werden soll
     * @param lon
     *            lon der Position um die gesucht werden soll
     * @param distance
     *            Distanz in Metern, wie weit gesucht werden soll
     * @return gibt die gefundenen Nodes als Vector aufsteigend sortiert nach
     *         Distanz zur Position zurück
     */
    public static Vector getNodesNearActualPosition(final int direction) {
	final int distance = OptionsStore.radius;
	final Position pos = ActualPosition.getPositionCopy();
	final float lat = pos.getLat();
	final float lon = pos.getLon();
	float radius = 0.0f;
	int calc;
	// long starttime = System.currentTimeMillis();
	do {
	    radius += 0.0005;
	    calc = GpsCalc.calcDistance(lat, lon, lat, lon + radius);
	    // System.out.println("Radius: " + radius + "° Calc: " + calc
	    // + "Meter");
	} while (distance >= calc);
	// long duration = System.currentTimeMillis() - starttime;
	// System.out.println("Radius Iteration: " + duration + "ms" +
	// " Radius: "
	// + calc);

	final Bounds boundingBox = new Bounds(lat, lon, radius);

	final Vector retVec = new Vector();
	final Enumeration enum = mNodes.elements();
	// starttime = System.currentTimeMillis();
	while (enum.hasMoreElements()) {
	    final Node node = (Node) enum.nextElement();
	    if (boundingBox.contains(node.getLat(), node.getLon())) {
		final int nodeDist = GpsCalc.calcDistance(lat, lon,
			node.getLat(), node.getLon());
		final short bearing = (short) GpsCalc.calcBearing(lat, lon,
			node.getLat(), node.getLon());
		if (nodeDist <= distance) {
		    if (direction == -1) {
			node.setDistance(nodeDist);
			node.setBearing(bearing);
			// System.out.println("Name: " + node.getName());
			if (retVec.isEmpty()) {
			    retVec.addElement(node);
			} else {
			    final int sizeBefore = retVec.size();
			    for (int i = 0; i < sizeBefore; i++) {
				final int compare = ((Node) retVec.elementAt(i))
					.getDistance() - node.getDistance();
				if (compare >= 0) {
				    retVec.insertElementAt(node, i);
				    break;
				} else {
				    if (compare < 0 && i == (sizeBefore - 1)) {
					retVec.addElement(node);
					break;
				    }
				}
			    }
			}
		    } else {
			if (bearing > direction - 45
				&& bearing < direction + 45) {
			    node.setDistance(nodeDist);
			    node.setBearing(bearing);
			    if (retVec.isEmpty()) {
				retVec.addElement(node);
			    } else {
				final int sizeBefore = retVec.size();
				for (int i = 0; i < sizeBefore; i++) {
				    final int compare = ((Node) retVec
					    .elementAt(i)).getDistance()
					    - node.getDistance();
				    if (compare >= 0) {
					retVec.insertElementAt(node, i);
					break;
				    } else {
					if (compare < 0
						&& i == (sizeBefore - 1)) {
					    retVec.addElement(node);
					    break;
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	// duration = System.currentTimeMillis() - starttime;
	// System.out.println("Vector bauen: " + duration + "ms Elemente: "
	// + retval.size());
	return retVec;
    }

    /**
     * Sucht alle Nodes, die in der Beschreibung oder Adresse den übergebenen
     * String beinhalten.
     * @param String, nach dem gesucht werden soll
     * @return Vector mit gefundenen Nodes. Geordnet aufsteigend nach Distanz
     */
    public static Vector getNodesContaining(String string) {
	final Position pos = ActualPosition.getPositionCopy();
	final float lat = pos.getLat();
	final float lon = pos.getLon();
	final String searchString = string.toLowerCase();

	boolean stringFound;

	final Vector retVec = new Vector();
	final Enumeration enum = mNodes.elements();
	while (enum.hasMoreElements()) {
	    final Node node = (Node) enum.nextElement();
	    stringFound = false;
	    final String address = node.getAddress().toLowerCase();
	    final String name = node.getName().toLowerCase();
	    final String description = node.getDescription().toLowerCase();
	    for (int i = 0; i <= (name.length() - searchString.length()); i++) {
		if (name.startsWith(searchString, i)) {
		    stringFound = true;
		    break;
		}
	    }
	    for (int i = 0; i <= (address.length() - searchString.length()); i++) {
		if (address.startsWith(searchString, i)) {
		    stringFound = true;
		    break;
		}
	    }
	    for (int i = 0; i <= (description.length() - searchString.length()); i++) {
		if (description.startsWith(searchString, i)) {
		    stringFound = true;
		    break;
		}
	    }

	    if (stringFound) {
		final int nodeDist = GpsCalc.calcDistance(lat, lon,
			node.getLat(), node.getLon());
		final short bearing = (short) GpsCalc.calcBearing(lat, lon,
			node.getLat(), node.getLon());
		node.setDistance(nodeDist);
		node.setBearing(bearing);
		// System.out.println("Name: " + node.getName());
		if (retVec.isEmpty()) {
		    retVec.addElement(node);
		} else {
		    final int sizeBefore = retVec.size();
		    for (int i = 0; i < sizeBefore; i++) {
			final int compare = ((Node) retVec.elementAt(i))
				.getDistance() - node.getDistance();
			if (compare >= 0) {
			    retVec.insertElementAt(node, i);
			    break;
			} else {
			    if (compare < 0 && i == (sizeBefore - 1)) {
				retVec.addElement(node);
				break;
			    }
			}
		    }
		}
	    }

	}

	return retVec;
    }

    /**
     * Diese Methode sucht nach dem nächstgelegenen Adress und
     * Place Node, um eine "Wo bin ich?" Funktion zu realisieren.
     * @return Vector mit zwei Elementen. [0] AdressNode [1] PlaceNode
     */
    public static Vector getWhereAmI() {
	final Position actualPosition = ActualPosition.getPositionCopy();
	final float lat = actualPosition.getLat();
	final float lon = actualPosition.getLon();
	final Vector retVec = new Vector();

	Node place = null;
	Node nearestAddress = null;
	int actualDist;
	short bearing;
	float radius = 0.0005f;
	Bounds boundingBox;

	do {
	    boundingBox = new Bounds(lat, lon, radius);
	    final Enumeration enum = mNodes.elements();
	    while (enum.hasMoreElements()) {
		final Node actualNode = (Node) enum.nextElement();
		if (boundingBox.contains(actualNode.getLat(),
			actualNode.getLon())) {
		    if (actualNode.getTag() == 199) { // ID für die Place-Node
			actualDist = GpsCalc.calcDistance(actualNode.getLat(),
				actualNode.getLon(), lat, lon);
			if (place == null) {
			    place = actualNode;
			    place.setDistance(actualDist);
			} else if (actualDist < place.getDistance()) {
			    place = actualNode;
			    place.setDistance(actualDist);
			}
		    } else if (!actualNode.getAddress().equalsIgnoreCase("")) {
			actualDist = GpsCalc.calcDistance(actualNode.getLat(),
				actualNode.getLon(), lat, lon);
			bearing = (short) GpsCalc.calcBearing(lat, lon,
				actualNode.getLat(), actualNode.getLon());
			if (nearestAddress == null) {
			    nearestAddress = actualNode;
			    nearestAddress.setDistance(actualDist);
			    nearestAddress.setBearing(bearing);
			} else if (actualDist < nearestAddress.getDistance()) {
			    nearestAddress = actualNode;
			    nearestAddress.setDistance(actualDist);
			    nearestAddress.setBearing(bearing);
			}
		    }
		}
	    }
	    radius += 0.0005;
	} while (place == null || nearestAddress == null || radius < 0.01); // TODO: Abbruchbedingung korrgieren

	retVec.addElement(nearestAddress);
	retVec.addElement(place);

	return retVec;
    }

}
