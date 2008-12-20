/*
 * Created on 06/06/2008
 */
package org.cycads.exceptions;

public class DBObjectNotFound extends DBException
{

	public DBObjectNotFound() {
	}

	public DBObjectNotFound(String message) {
		super(message);
	}

	public DBObjectNotFound(Object[] keys) {
		this("Object with keys " + getString(keys) + " not found");
	}

}
