/* LocateMe
 * Copyright © 2009 Silent Software (Benjamin Brown) and Apache Software Foundation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/cpl1.0.php
 * 
 * The method "split(String str, char separatorChar)" below is derived 
 * from Apache 2.0 licensed code. In keeping with this, for simplicity 
 * Benjamin Brown also allows his amendments made only to this method 
 * to be licensed under Apache 2.0 or the Common Public License at your
 * discretion. 
 * The rest of the method "split(String str, char separatorChar)" is 
 * still Apache 2.0 licensed as per its original license - a copy 
 * is available at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 */

/**
 * Diese Klasse ist Teil der LoroDux-MIDlet-Suite.
 * @author Daniel H‰nﬂgen
 * 
 * Angepasst wurde lediglich die Paket-Struktur.
 */
package de.fhhannover.inform.dhaenssg.lorodux.util;

import java.util.Vector;

/**
 * Provides String manipulation functions, ported from 
 * Apache Commons StringUtils.
 */
public class StringUtils {

	/**
	 * Modified from MIDP from Apache Commons StringUtils
	 * (Apache licence allows use)
	 * Change - does not ignore empty breaks between separators 
	 * anymore, inserts a white space into the result instead
	 * 
	 * 
	 * @param str
	 * @param separatorChar
	 * @return
	 */
	public static String[] split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[]{};
		}
		Vector list = new Vector();
		int i = 0, start = 0;
		boolean match = false;
		int index = -1;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (i == index+1) {
					list.addElement(" ");
				}
				index = i;
				if (match) {
					String s = str.substring(start, i);
					list.addElement(s);
					match = false;
				}
				start = ++i;
				continue;
			}
			match = true;
			i++;
		}
		if (match) {
			list.addElement(str.substring(start, i));
		}
		String[] result = new String[list.size()];
		list.copyInto(result);
		return result; 
	}
}