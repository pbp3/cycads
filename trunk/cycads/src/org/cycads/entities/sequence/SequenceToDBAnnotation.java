/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import org.cycads.entities.annotation.DBAnnotation;

public interface SequenceToDBAnnotation extends DBAnnotation
{

	/**
	 * Getter of the property <tt>sequence</tt>
	 * 
	 * @return Returns the sequence.
	 * 
	 */
	public Sequence getSequence();

}