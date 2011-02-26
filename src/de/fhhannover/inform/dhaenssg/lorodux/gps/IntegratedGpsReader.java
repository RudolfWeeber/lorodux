/* FollowMe
 * Copyright © 2009 Silent Software (Benjamin Brown)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/cpl1.0.php
 */
package de.fhhannover.inform.dhaenssg.lorodux.gps;

import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;


import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.view.AlertManager;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
/**
 * Simple class to associate with an integrated
 * GPS and read the NMEA data in,
 * Modified for LoroDux 
 */
public class IntegratedGpsReader implements GpsReader, LocationListener {

	/**
	 * The location provider for this phone based
	 * on the required criteria
	 */
    private LocationProvider lp;
    private static boolean isConnected=false;

    /**
     * Default constructor
     * 
     * @throws LocationException if provider unavailable/integrated GPS does not exist
     */
    public IntegratedGpsReader() throws LocationException {
		Criteria criteria = new Criteria();
		criteria.setSpeedAndCourseRequired(true);
		criteria.setCostAllowed(true);
		criteria.setVerticalAccuracy(100);
		criteria.setHorizontalAccuracy(10);
		criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_HIGH);


		lp = LocationProvider.getInstance(criteria);
		if (lp == null) {
                        AlertManager.displayInfo("Der Ort kann nicht mit der gewuenschten Genauigkeit bestimmt werden.");
			// Any will do, but we won't be able to use
			// the bearing/course/speed without correct 
			//criteria
			lp = LocationProvider.getInstance(null);
		}
		
        lp.setLocationListener(this, -1, -1, -1);
    }

    /*
     * {@inheritDoc}
     */
    public void locationUpdated(LocationProvider lp, Location loc) {
       // If the location given isn't valid, we don't have usable coordinates (yet) 
       if (! loc.isValid()) {
        return;
       }
       
       // Get the positino object ot update
       final Position pos = ActualPosition.getPosition();

       // If there was no gps fix before, let the user knwo that there's a 
       // position now

       if (!isConnected) {
         LoroDux.displayStringOnMainView("GPS bereit: Integriertes GPS");
         isConnected=true;
       }
       // Update position object with basic info from gps
       pos.setCoordinates((float)loc.getQualifiedCoordinates().getLatitude(),(float)loc.getQualifiedCoordinates().getLongitude());
       pos.setAccuracy(loc.getQualifiedCoordinates().getHorizontalAccuracy());

       // The location object is not guaranteed to contain a valid speed and dir
       if (loc.getSpeed()>=0.0)
       {
         // We multiply by 3.6 to convert m/s to km/h
         pos.setSpeed((float)(loc.getSpeed() *3.6));
         pos.setHeading((short) loc.getCourse());
       }
       pos.setTimestamp(loc.getTimestamp());
       pos.setStatus(true); // Means: a position was reported
       pos.setInfo(loc.getExtraInfo("text/plain") );
	       
       // Todo: Find out number of satelites ()
       // http://discussion.forum.nokia.com/forum/showthread.php?124912-How-to-know-the-number-of-GPS-satellites

    }

    /*
     * {@inheritDoc}
     */
    public void providerStateChanged(LocationProvider lp, int arg1) {
    	//FollowMe.pushDataToView(""+arg1);
    }

    /*
     * {@inheritDoc}
     */
    public void shutdown() {
        lp.setLocationListener(null, 1, 1, 1);
    }

    public void resetToCleanStatus() {
     // Nothing to do?
    }


    /*
     * {@inheritDoc}
     */
	public void discoverGPS() {
		// Not needed
	}
}
