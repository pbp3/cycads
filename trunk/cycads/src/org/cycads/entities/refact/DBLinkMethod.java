package org.cycads.entities.refact;

import java.util.Collection;

/** 
 */
public class DBLinkMethod
{

	private Collection<DBLink>	dBLink;

	private Term				type;

	private String				name;

	/**
	 * Getter of the property <tt>type</tt>
	 * 
	 * @return Returns the type.
	 * 
	 */
	public Term getType()
	{
		return type;
	}

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName()
	{
		return name;
	}

}
