//License: GPL. Copyright 2007 by Immanuel Scholz and others

/**
 * Diese Klasse ist Teil der LoroDux MIDlet Suite.
 * Sie wurde angepasst, sodass sie auf J2ME l‰uft.
 * double in float umgewandelt, parameter finalisiert.
 * 
 * @author Daniel H‰nﬂgen
 * Datum 05.05.2010
 */

package de.fhhannover.inform.dhaenssg.lorodux.osm;

/**
 * LatLon are unprojected latitude / longitude coordinates.
 * 
 * This class is immutable.
 * 
 * @author Imi
 */
public class LatLon {

    float mLat;
    float mLon;

    /**
     * @param lat
     *            the unprojected latitude
     * @param lon
     *            unprojected longitude
     */
    public LatLon(final float lat, final float lon) {
	mLat = lat;
	mLon = lon;
    }

    /**
     * @return the unprojected latitude
     */
    public float lat() {
	return mLat;
    }

    /**
     * @return the unprojected longitude
     */
    public float lon() {
	return mLon;
    }

    /**
     * @return <code>true</code> if this is within the given bounding box.
     * @param b
     *            the bounds to check against
     */
    public boolean isWithin(final Bounds b) {
	return lat() >= b.getMin().lat() && lat() <= b.getMax().lat()
		&& lon() > b.getMin().lon() && lon() < b.getMax().lon();
    }

    /**
     * @return A string-representation of our coordinates.
     */
    public String toString() {
	return "LatLon[lat=" + lat() + ",lon=" + lon() + "]";
    }

}
