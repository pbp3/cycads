/*
 * Created on 21/10/2008
 */
package org.cycads.exceptions;

public class LoadLineError extends Exception
{
	public LoadLineError(String line) {
		super("Error at line: " + line);
	}

	public LoadLineError(String line, Throwable cause) {
		super("Error at line: " + line, cause);
	}

}
