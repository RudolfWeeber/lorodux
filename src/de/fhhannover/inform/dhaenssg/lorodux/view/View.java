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
 * 
 * @author Daniel Hänßgen
 * 
 * 06.05.2010 - Erste Implementierung
 * 09.05.2010 - setSimpleData hinzugefügt und show sichtbarkeis-Deskriptor verpasst.
 */

package de.fhhannover.inform.dhaenssg.lorodux.view;

import javax.microedition.lcdui.CommandListener;

public abstract class View implements CommandListener{
    
    abstract void setSimpleData(String data);
    
    /**
     * Abstrakte Methode, die die erbenden Klassen implementieren müssen.
     * Sie dient der Darstellung des in der View-Klasse enthaltenen Displayable-Objekts  
     */
    abstract public void show();

}
