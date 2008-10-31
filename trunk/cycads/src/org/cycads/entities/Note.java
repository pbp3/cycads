/*
 * Created on 31/10/2008
 */
package org.cycads.entities;

public interface Note
{
	public String getValue();

	public NoteType getType();

	public SequenceFeature getSequenceFeature();
}
