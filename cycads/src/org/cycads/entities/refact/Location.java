package org.cycads.entities.refact;

import java.util.Collection;

/*
 * Created on 07/11/2008
 */

public class Location
{

	/*
	* (non-javadoc)
	*/
	private int			end;

	Collection<Intron>	introns;
	boolean				positiveStrand;

	public Collection<Intron> getIntrons() {
		return introns;
	}

	public boolean isPositiveStrand() {
		return positiveStrand;
	}

	/**
	 * Getter of the property <tt>end</tt>
	 * 
	 * @return Returns the end.
	 * 
	 */
	public int getEnd() {
		return end;
	}

	/*
	* (non-javadoc)
	*/
	private int	start;

	/**
	 * Getter of the property <tt>start</tt>
	 * 
	 * @return Returns the start.
	 * 
	 */
	public int getStart() {
		return start;
	}

}
