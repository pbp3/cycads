package org.cycads.entities.refact;

import org.cycads.entities.ISequence;
import org.cycads.entities.ISequenceToDBAnnotation;

/*
 * Created on 07/11/2008
 */

public class SequenceToDBAnnotation extends DBAnnotation implements ISequenceToDBAnnotation
{

	private ISequence	sequence;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequenceToDBAnnotation#getSequence()
	 */
	public ISequence getSequence()
	{
		return sequence;
	}

}
