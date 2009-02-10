/*
 * Created on 06/06/2008
 */
package org.cycads.exceptions;

public class DBException extends Exception
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	public DBException()
	{
		// TODO Auto-generated constructor stub
	}

	public DBException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DBException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DBException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public static String getString(Object[] keys)
	{
		String keysStr = "";
		for (Object ob : keys)
		{
			keysStr += "|" + ob.toString();
		}
		keysStr += "|";
		return keysStr;
	}

}
