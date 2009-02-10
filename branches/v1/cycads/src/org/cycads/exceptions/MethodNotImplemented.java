/*
 * Created on 03/12/2008
 */
package org.cycads.exceptions;

import org.cycads.general.Messages;

public class MethodNotImplemented extends RuntimeException
{

	public MethodNotImplemented()
	{
		super(Messages.exceptionMethodNotImplemented());
	}

	public MethodNotImplemented(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MethodNotImplemented(String message)
	{
		super(message);
	}

	public MethodNotImplemented(Throwable cause)
	{
		super(cause);
	}

}
