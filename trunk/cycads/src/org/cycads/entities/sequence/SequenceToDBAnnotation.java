/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import org.cycads.entities.annotation.DBLink;

public interface SequenceToDBAnnotation extends DBLink
{

	/**
	 * Getter of the property <tt>sequence</tt>
	 * 
	 * @return Returns the sequence.
	 * 
	 */
	public Sequence getSequence();

}