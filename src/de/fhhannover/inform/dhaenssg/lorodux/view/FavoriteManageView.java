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
 * FavoriteManageView is part of the LoroDux midlet suite
 *Used for showing and editing favorites
 * 
 * @author Daniel Hänßgen
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;
import de.fhhannover.inform.dhaenssg.lorodux.datastore.FavoriteStore;
import de.fhhannover.inform.dhaenssg.lorodux.entity.ActualPosition;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Favorite;
import de.fhhannover.inform.dhaenssg.lorodux.entity.Position;

public class FavoriteManageView extends View {

    // Declaration of commands
    private final transient Command ADDFAV = new Command("Favorit hinzufügen",
	    Command.SCREEN, 0);
    private final transient Command REMFAV = new Command("Favorit entfernen",
	    Command.SCREEN, 0);
    private final transient Command SHOWFAV = new Command("Favorit anzeigen",
	    Command.SCREEN, 0);
    private final transient Command ADDPOS = new Command(
	    "Aktuelle Position als Favorit speichern", Command.SCREEN, 0);
    private final transient Command BACK = new Command("zurück", Command.BACK,
	    1);

    private final transient List mList;

    private transient FavoriteInputView mInputView;

    public FavoriteManageView() {
	mList = new List("Favoriten verwalten", List.IMPLICIT);

	mList.addCommand(SHOWFAV);
	mList.addCommand(ADDFAV);
	mList.addCommand(REMFAV);
	mList.addCommand(ADDPOS);
	mList.addCommand(BACK);
	mList.setCommandListener(this);

	// refreshList();
    }

    /**
     * Rebuild the list from the store
     */
    private void refreshList() {
	mList.deleteAll();
	final Vector items = FavoriteStore.get();

	for (int i = 0; i < items.size(); i++) {
	    mList.append(((Favorite) items.elementAt(i)).getName(), null);
	}
    }

    /**
     * Shows an empty favorite entry view
     */
    private void addFavorite() {
	mInputView = new FavoriteInputView(this);
	mInputView.show();
    }

    /**
     * Shows an entry view already containing current gps coordinates
     */
    private void addPosition() {
	final Position actualPosition = ActualPosition.getPositionCopy();
	mInputView = new FavoriteInputView(this);
	mInputView.setCoordinates(actualPosition.getLat(),
		actualPosition.getLon());
	mInputView.show();
    }

    /**
     * Remove a favorite from the store
     * 
     * @param index
     *           Index of the favorite to remove
     */
    private void removeFavorite(final int index) {
	FavoriteStore.remove(index);
	refreshList();
    }

    /**
     * Shows a favorite in detail
     * 
     * @param index
     *           Index of the favorite to show
     */
    private void showFavorite(final int index) {
	new FavoriteShowView(this, (Favorite) FavoriteStore.get().elementAt(
		index));
    }

    public void show() {
	refreshList();
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mList);
    }

    /**
     * handles all the commands
     */
    public void commandAction(final Command c, final Displayable arg1) {

	if (c == ADDFAV) {
	    addFavorite();
	} else if (c == REMFAV) {
	    removeFavorite(mList.getSelectedIndex());
	} else if (c == SHOWFAV) {
	    showFavorite(mList.getSelectedIndex());
	} else if (c == ADDPOS) {
	    addPosition();
	} else if (c == BACK) {
	    LoroDux.showView(LoroDux.MAINVIEW);
	} else if (c == List.SELECT_COMMAND) {
	    showFavorite(mList.getSelectedIndex());
	}

    }

    void setSimpleData(String data) {
	// TODO Auto-generated method stub

    }

}
