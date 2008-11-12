package org.cycads.entities.refact;

import java.util.Collection;

/*
 * Created on 07/11/2008
 */

public class Location implements ILocation
{
	private int					start, end;
	private Collection<IIntron>	introns;
	private boolean				positiveStrand;

	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#getIntrons()
	 */
	public Collection<IIntron> getIntrons()
	{
		return introns;
	}

	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#isPositiveStrand()
	 */
	public boolean isPositiveStrand()
	{
		return positiveStrand;
	}

	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#getEnd()
	 */
	public int getEnd()
	{
		return end;
	}

	/*
	 * (non-javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#getStart()
	 */
	public int getStart()
	{
		return start;
	}

}
