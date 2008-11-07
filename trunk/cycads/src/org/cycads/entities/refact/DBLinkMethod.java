package org.cycads.entities.refact;

import java.util.Collection;

/** 
 */
public class DBLinkMethod extends Term
{

	private Collection<DBLink>	dBLinks;

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName()
	{
		return getValue();
	}

	public Collection<DBLink> getDBLinks()
	{
		return dBLinks;
	}

}
