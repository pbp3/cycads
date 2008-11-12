/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.Intron;

public class Intron implements Intron
{
	private int	start, end;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IIntron#getStart()
	 */
	public int getStart()
	{
		return start;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IIntron#getEnd()
	 */
	public int getEnd()
	{
		return end;
	}

}
