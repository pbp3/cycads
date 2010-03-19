/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

public class GetterExpressionException extends Exception
{
	/**
	 * @param message
	 * @param cause
	 */
	public GetterExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public GetterExpressionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GetterExpressionException(Throwable cause) {
		super(cause);
	}

}
