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
 * GpsCalc beinhaltet statische Methoden zur Berechnung der Distanz und
 * Richtung (in Grad, Richtung, und auf der Uhr) zwischen zwei Koordinaten.
 * 
 * calcDistance adaptiert von
 * http://www.codeguru.com/cpp/cpp/algorithms/general/article.php/c5115
 * 
 * @author Daniel Hänßgen
 * 
 * Version: 0.1
 * 05.04.2010 - Implementierung der Berechnung der Distanz anhand der
 * approximierten ellipsoid-Formel.
 * PROBLEM: atan2 nicht in Math von J2ME vorhanden
 * Version: 0.2
 * 11.04.2010 -> MathSupport von sun eingebunden
 * 				Implementierung der Berechnung der Richtung.
 * 12.04.2010 -> Coordinates entfernt und double lat lon eingeführt
 * Version: 0.3
 * 05.05.2010 -> calcDistance gibt int zurück.
 * 	
 */

package de.fhhannover.inform.dhaenssg.lorodux.util;

public class GpsCalc {
    // private static final double TWOPI = 6.28318530718;
    private static final double DE2RA = 0.01745329252;
    // private static final double RA2DE = 57.2957795129;
    private static final double ERAD = 6378137;
    // private static final double ERADM = 6378135.0;
    // private static final double AVG_ERAD = 6371.0;
    private static final double FLATTENING = 1.0 / 298.257223563;
    // Earth flattening (WGS '84)
    // private static final double EPS = 0.000000000005;
    // private static final double KM2MI = 0.621371;
    // private static final double GEOSTATIONARY_ALT = 35786.0; // km
    public static double heading;

    /**
     * Berechnet die Distanz in Metern und rundet auf ganze Zahlen
     */
    public static int calcDistance(double lat1, double lon1, double lat2,
	    double lon2) {

	lat1 = DE2RA * lat1;
	lon1 = -DE2RA * lon1;
	lat2 = DE2RA * lat2;
	lon2 = -DE2RA * lon2;

	final double F = (lat1 + lat2) / 2.0;
	final double G = (lat1 - lat2) / 2.0;
	final double L = (lon1 - lon2) / 2.0;

	final double sing = Math.sin(G);
	final double cosl = Math.cos(L);
	final double cosf = Math.cos(F);
	final double sinl = Math.sin(L);
	final double sinf = Math.sin(F);
	final double cosg = Math.cos(G);

	final double S = sing * sing * cosl * cosl + cosf * cosf * sinl * sinl;
	final double C = cosg * cosg * cosl * cosl + sinf * sinf * sinl * sinl;
	final double W = MathSupport.atan2(Math.sqrt(S), Math.sqrt(C));
	final double R = Math.sqrt((S * C)) / W;
	final double H1 = (3 * R - 1.0) / (2.0 * C);
	final double H2 = (3 * R + 1.0) / (2.0 * S);
	final double D = 2 * W * ERAD;
	final double result = (D * (1 + FLATTENING * H1 * sinf * sinf * cosg * cosg - FLATTENING
		* H2 * cosf * cosf * sing * sing));
	return MathSupport.round(result);
    }

    // public static int calcDistance(double lat1, double lon1, double lat2,
    // double lon2) {
    // double distance = 0.0;
    // double faz, baz;
    // double r = 1.0 - FLATTENING;
    // double tu1, tu2, cu1, su1, cu2, x, sx, cx, sy, cy, y, sa, c2a, cz, e, c,
    // d;
    // double cosy1, cosy2;
    //
    // if ((lon1 == lon2) && (lat1 == lat2))
    // return MathSupport.round(distance);
    // lon1 *= DE2RA;
    // lon2 *= DE2RA;
    // lat1 *= DE2RA;
    // lat2 *= DE2RA;
    //
    // cosy1 = Math.cos(lat1);
    // cosy2 = Math.cos(lat2);
    //
    // if (cosy1 == 0.0)
    // cosy1 = 0.0000000001;
    // if (cosy2 == 0.0)
    // cosy2 = 0.0000000001;
    //
    // tu1 = r * Math.sin(lat1) / cosy1;
    // tu2 = r * Math.sin(lat2) / cosy2;
    // cu1 = 1.0 / Math.sqrt(tu1 * tu1 + 1.0);
    // su1 = cu1 * tu1;
    // cu2 = 1.0 / Math.sqrt(tu2 * tu2 + 1.0);
    // x = lon2 - lon1;
    //
    // distance = cu1 * cu2;
    // baz = distance * tu2;
    // faz = baz * tu1;
    //
    // do {
    // sx = Math.sin(x);
    // cx = Math.cos(x);
    // tu1 = cu2 * sx;
    // tu2 = baz - su1 * cu2 * cx;
    // sy = Math.sqrt(tu1 * tu1 + tu2 * tu2);
    // cy = distance * cx + faz;
    // y = MathSupport.atan2(sy, cy);
    // sa = distance * sx / sy;
    // c2a = -sa * sa + 1.0;
    // cz = faz + faz;
    // if (c2a > 0.0)
    // cz = -cz / c2a + cy;
    // e = cz * cz * 2. - 1.0;
    // c = ((-3.0 * c2a + 4.0) * FLATTENING + 4.0) * c2a * FLATTENING
    // / 16.0;
    // d = x;
    // x = ((e * cy * c + cz) * sy * c + y) * sa;
    // x = (1.0 - c) * x * FLATTENING + lon2 - lon1;
    // } while (Math.abs(d - x) > EPS);
    //
    // x = Math.sqrt((1.0 / r / r - 1.0) * c2a + 1.0) + 1.0;
    // x = (x - 2.0) / x;
    // c = 1.0 - x;
    // c = (x * x / 4.0 + 1.0) / c;
    // d = (0.375 * x * x - 1.0) * x;
    // x = e * cy;
    // distance = 1.0 - e - e;
    // distance = ((((sy * sy * 4.0 - 3.0) * distance * cz * d / 6.0 - x) * d
    // / 4.0 + cz)
    // * sy * d + y)
    // * c * ERAD * r;
    //
    // return MathSupport.round(distance);
    // }

    /**
     * Berechnet mittels Bewegungsrichtung und Bearing den Kurs.
     */
    public static short calcCourse(final short heading, final short bearing) {
	return (short) ((360 - (heading - bearing)) % 360);
    }

    /**
     * Berechnet Sollkurs anhand zweier Koordinaten.
     * 
     * @param lat1
     *            Breitengrad des ersten Punkts
     * @param lon1
     *            Längengrad des ersten Punkts
     * @param lat2
     *            Breitengrad des zweiten Punkts
     * @param lon2
     *            Längengrad des zweiten Punkts
     * 
     * @return the course (direction) in degrees
     */
    public static int calcBearing(final double lat2, final double lon2,
	    final double lat1, final double lon1) {
	final double fullCircle = 360;
	final double halfCircle = fullCircle / 2;
	final double quarterCircle = halfCircle / 2;
	final double dLat = lat1 - lat2;
	final double dLon = lon1 - lon2;
	final double alpha = MathSupport.atan2(dLat, dLon) * halfCircle / Math.PI;
	if (alpha <= quarterCircle) {
	    return MathSupport.round(quarterCircle - alpha);
	} else {
	    return MathSupport.round((fullCircle + quarterCircle) - alpha);
	}
    }
}
