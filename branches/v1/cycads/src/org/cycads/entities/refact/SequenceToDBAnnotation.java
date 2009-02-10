package org.cycads.entities.refact;

import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SequenceToDBAnnotation;

/*
 * Created on 07/11/2008
 */

public class SequenceToDBAnnotation extends DBAnnotation implements SequenceToDBAnnotation
{

	private Sequence	sequence;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ISequenceToDBAnnotation#getSequence()
	 */
	public Sequence getSequence()
	{
		return sequence;
	}

}
