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
 * Sie dient jeder anderen Klasse als einfaches Eingabefeld.
 * 
 * @author Daniel Hänßgen
 * 06.05.2010 - Erste Implementierung
 * 09.05.2010 - Im Zuge der Implementierung des MIDlets als Singleton angepasst
 * 07.06.2010 - Konstruktor um Zeichenanzahl erweitert
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import de.fhhannover.inform.dhaenssg.lorodux.LoroDux;

public class SimpleInputView implements CommandListener{
    
    private final transient Command OK = new Command("OK", Command.OK, 0);
    private final transient Command CANCEL = new Command("Abbruch", Command.CANCEL, 1);
    
    private final transient TextField mInputField;
    private final transient View mView;
    
    /**
     * Konstruktor
     * @param view
     * 		View, von dem der Aufruf kommt
     * @param title
     * 		Titel, der angezeigt werden soll
     * @param charcount
     * 		Maximale Anzahl von Zeichen, die erlaubt sein sollen
     * @param constraints
     * 		Constraints für das TextField
     */
    public SimpleInputView(final View view, final String title, final int charcount, final int constraints){
	mView = view;
	final Form mForm = new Form(title);
	mInputField = new TextField("", "", charcount, constraints);
	
	mForm.append(mInputField);
	mForm.addCommand(OK);
	mForm.addCommand(CANCEL);
	mForm.setCommandListener(this);
	
	Display.getDisplay(LoroDux.getInstance()).setCurrent(mForm);
    }
    
    public void commandAction(final Command c, final Displayable arg1) {
	if (c == OK){
	    mView.setSimpleData(mInputField.getString());
	    mView.show();
	} else if (c == CANCEL)
	    mView.show();
	
    }

}
