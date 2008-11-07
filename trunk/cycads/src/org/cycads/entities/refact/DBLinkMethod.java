package org.cycads.entities.refact;

import java.util.Collection;
import java.util.Iterator;/*
 * Created on 07/11/2008
 */

/** 
 */
public class DBLinkMethod
{

	/**
	 * 
	 */
	private Collection<DBLink>	dBLink;

	/*
	 * (non-javadoc)
	 */
	private Term							type;

	/**
	 * Getter of the property <tt>type</tt>
	 * 
	 * @return Returns the type.
	 * 
	 */
	public Term getType() {
		return type;
	}

	/*
	 * (non-javadoc)
	 */
	private String	name;

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName() {
		return name;
	}

}
