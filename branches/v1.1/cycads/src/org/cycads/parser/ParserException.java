/*
 * Created on 16/03/2009
 */
package org.cycads.parser;

public class ParserException extends Exception {

	/**
	 * @param message
	 * @param cause
	 */
	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ParserException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ParserException(Throwable cause) {
		super(cause);
	}

}
