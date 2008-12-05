/*
 * Created on 03/12/2008
 */
package org.cycads.exceptions;

import org.cycads.general.Messages;

public class InvalidMethod extends RuntimeException
{

	public InvalidMethod()
	{
		super(Messages.exceptionInvalidMethod());
	}

	public InvalidMethod(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InvalidMethod(String message)
	{
		super(message);
	}

	public InvalidMethod(Throwable cause)
	{
		super(cause);
	}

}
