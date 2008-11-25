/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

public interface Note<H extends NoteHolder<H>>
{

	public NoteType getType();

	public String getValue();

	public H getHolder();

}