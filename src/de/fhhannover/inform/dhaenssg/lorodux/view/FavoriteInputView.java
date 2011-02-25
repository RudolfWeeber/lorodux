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
 * This class is part of the LoroDux midlet suite
 * Displays a view for the entry of a favorite's name and coordinates
 *
 * @author Daniel Hänßgen
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.FavoriteStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Favorite;

public class FavoriteInputView extends View {

    private final transient View mManageView;

    /**
     * Declaration of commands
     */
    private final transient Command OK = new Command("OK", Command.ITEM, 0);
    private final transient Command BACK = new Command("Abbruch", Command.BACK, 1);

    private final transient Command YES = new Command("Ja", Command.OK, 0);
    private final transient Command NO = new Command("Nein", Command.CANCEL, 1);

    /**
     * Displayable Form
     */
    private final transient Form mForm;

    /**
     * Declaration of text fields shown on the form
     */
    private final transient TextField mName;
    private final transient TextField mLat;
    private final transient TextField mLon;

    public FavoriteInputView(final View manageView) {

	mManageView = manageView;
	mForm = new Form("Neuer Favorit");

	final TextField nameLabel = new TextField(null, "Name", 4, TextField.UNEDITABLE);
	final TextField latLabel = new TextField(null, "Breitengrad", 11, TextField.UNEDITABLE);
	final TextField lonLabel = new TextField(null, "Längengrad", 10, TextField.UNEDITABLE);
	
	
	mName = new TextField(null, "", 255, TextField.ANY);
	mLat = new TextField(null, "", 10, TextField.DECIMAL);
	mLon = new TextField(null, "", 10, TextField.DECIMAL);

	mForm.append(nameLabel);
	mForm.append(mName);
	mForm.append(latLabel);
	mForm.append(mLat);
	mForm.append(lonLabel);
	mForm.append(mLon);

	mForm.addCommand(OK);
	mForm.addCommand(BACK);
	mForm.setCommandListener(this);

    }

    public String getName() {
	return mName.getString();
    }

    public float getLat() {
	return Float.valueOf(mLat.getString()).floatValue();
    }

    public float getLon() {
	return Float.valueOf(mLon.getString()).floatValue();
    }

    public void setCoordinates(final float lat, final float lon) {
	try {
	    mLat.setString(String.valueOf(lat));
	    mLon.setString(String.valueOf(lon));

	} catch (Exception e) {
	    LoroDux.displayStringOnMainView("FavInputView setCoordinates(): "
		    + e.toString());
	}
    }

    public void setName(final String name) {
	mName.setString(name);
    }

    public synchronized void show() {
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }

    /**
     * Create new favorite object and saves it
     * If a favorite with the same name exists, ask whether to replace
     */
    private void addFavorite() {
	final Favorite newFav = new Favorite(getName(), getLat(), getLon());
	if (FavoriteStore.add(newFav)) {
	    mManageView.show();
	} else {
	    final Alert overwrite = new Alert("Name bereits vorhanden",
		    "Favorit ersetzen?", null, AlertType.WARNING);
	    overwrite.addCommand(YES);
	    overwrite.addCommand(NO);
	    overwrite.setCommandListener(this);
	    overwrite.setTimeout(Alert.FOREVER);
	    Display.getDisplay(LoroDux.getInstance()).setCurrent(overwrite);
	}
    }

    /**
     * Is executed when the user selects a command
     */
    public void commandAction(final Command c, final Displayable d) {
	switch (c.getCommandType()) {
	case Command.ITEM:
	    addFavorite();
	    break;

	case Command.BACK:
	    mManageView.show();
	    break;

	case Command.OK:
	    FavoriteStore.replaceCurrent();
	    mManageView.show();
	    break;

	case Command.CANCEL:
	    show();
	    break;
	default:
	    break;
	}

    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub
	
    }

}
