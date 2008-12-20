package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Location;

/*
 * Created on 07/11/2008
 */

public class Location implements Location
{
	private int					start, end;
	private Collection<Intron>	introns;
	private boolean				positiveStrand;

	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#getIntrons()
	 */
	public Collection<Intron> getIntrons() {
		return introns;
	}

	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#isPositiveStrand()
	 */
	public boolean isPositiveStrand() {
		return positiveStrand;
	}

	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#getEnd()
	 */
	public int getEnd() {
		return end;
	}

	/*
	 * (non-javadoc)
	 */
	/*
	 * (non-Javadoc)
	 * @see org.cycads.entities.refact.ILocation#getStart()
	 */
	public int getStart() {
		return start;
	}

	public void addIntron(Intron intron) {
		// TODO Auto-generated method stub

	}

	public Intron addIntron(int startPos, int endPos) {
		// TODO Auto-generated method stub
		return null;
	}

}
