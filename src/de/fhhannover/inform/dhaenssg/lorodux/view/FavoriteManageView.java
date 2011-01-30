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
 * FavoriteManageView ist Teil der LoroDux MIDlet Suite.
 * Sie dient zur Darstellung und Bearbeitung von Favoriten.
 * 
 * @author Daniel Hänßgen
 * Version: 0.1
 * 11.04.2010 - Implementierung
 * Version: 0.2
 * 12.04.2010 - Anpassung an zwei double statt Coordinates Object
 * 				setCoordinates mit neuem Position Object
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
     * Entfernt alle Elemente aus der Liste, holt sich die aktuellen Daten aus
     * dem Store.
     */
    private void refreshList() {
	mList.deleteAll();
	final Vector items = FavoriteStore.get();

	for (int i = 0; i < items.size(); i++) {
	    mList.append(((Favorite) items.elementAt(i)).getName(), null);
	}
    }

    /**
     * Leert FavoriteInputView und zeigt diesen dann zur Dateneingabe an.
     */
    private void addFavorite() {
	mInputView = new FavoriteInputView(this);
	mInputView.show();
    }

    /**
     * Holt sich die aktuellen Positionsdaten vom GPS-Empfänger schreibt diese
     * auf den InputView und zeigt diesen dann an.
     */
    private void addPosition() {
	final Position actualPosition = ActualPosition.getPositionCopy();
	mInputView = new FavoriteInputView(this);
	mInputView.setCoordinates(actualPosition.getLat(),
		actualPosition.getLon());
	mInputView.show();
    }

    /**
     * Entfernt ausgewählen Favoriten aus dem Store
     * 
     * @param index
     *            Beinhaltet den Index des Favoriten.
     */
    private void removeFavorite(final int index) {
	FavoriteStore.remove(index);
	refreshList();
    }

    /**
     * Zeigt Favorit auf FavoriteShowView an.
     * 
     * @param index
     *            Beinhaltet den Index des Favoriten.
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
     * Wird aufgerufen vom FavoriteInputView und holt sich die Daten aus diesem
     * und speichert sie anschließend in den FavoriteStore, aktuallisiert die
     * Liste und zeigt diese auf dem Display an.
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
