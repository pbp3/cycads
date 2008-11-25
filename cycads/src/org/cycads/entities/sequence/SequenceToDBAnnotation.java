/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import org.cycads.entities.annotation.ExternalAnnotation;

public interface SequenceToDBAnnotation extends ExternalAnnotation
{

	/**
	 * Getter of the property <tt>sequence</tt>
	 * 
	 * @return Returns the sequence.
	 * 
	 */
	public Sequence getSequence();

}