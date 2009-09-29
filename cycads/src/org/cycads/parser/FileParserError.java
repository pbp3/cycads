/*
 * Created on 16/03/2009
 */
package org.cycads.parser;

public class FileParserError extends Exception {

	/**
	 * @param message
	 * @param cause
	 */
	public FileParserError(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public FileParserError(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FileParserError(Throwable cause) {
		super(cause);
	}

}
