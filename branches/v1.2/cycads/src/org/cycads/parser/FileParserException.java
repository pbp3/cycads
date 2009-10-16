/*
 * Created on 16/03/2009
 */
package org.cycads.parser;

public class FileParserException extends Exception {

	/**
	 * @param message
	 * @param cause
	 */
	public FileParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public FileParserException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FileParserException(Throwable cause) {
		super(cause);
	}

}
