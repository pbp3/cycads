package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.IIntron;
import org.cycads.entities.ILocation;

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
	public Collection<IIntron> getIntrons() {
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

	public void addIntron(IIntron intron) {
		// TODO Auto-generated method stub

	}

	public IIntron addIntron(int startPos, int endPos) {
		// TODO Auto-generated method stub
		return null;
	}

}
