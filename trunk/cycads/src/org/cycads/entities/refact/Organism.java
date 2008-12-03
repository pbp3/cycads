package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.sequence.Organism;

/*
 * Created on 07/11/2008
 */

/** 
 */
public class Organism implements Organism
{

	private int						id;
	private String					name;
	private Collection<Sequence>	sequences;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IOrganism#getSequences()
	 */
	public Collection<Sequence> getSequences()
	{
		return sequences;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IOrganism#getSequences(double)
	 */
	public Collection<Sequence> getSequences(double version)
	{
		return sequences;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IOrganism#getName()
	 */
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IOrganism#getId()
	 */
	public int getId()
	{
		return id;
	}

}