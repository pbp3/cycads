package org.cycads.entities.refact;

import java.util.Collection;

/*
 * Created on 07/11/2008
 */

/** 
 */
public class Organism
{

	private int						id;
	private String					name;
	private Collection<Sequence>	sequences;

	public Collection<Sequence> getSequences()
	{
		return sequences;
	}

	public Collection<Sequence> getSequences(double version)
	{
		return sequences;
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

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * 
	 */
	public int getId()
	{
		return id;
	}

}
