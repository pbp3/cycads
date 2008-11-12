/*
 * Created on 12/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.note.NoteType;

public class NoteType implements NoteType
{
	String	name;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.INoteType#getName()
	 */
	public String getName()
	{
		return name;
	}

}
