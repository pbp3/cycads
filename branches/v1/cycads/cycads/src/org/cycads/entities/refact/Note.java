/*
 * Created on 12/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteType;

public class Note implements Note
{
	NoteType	noteType;

	String		value;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.INote#getNoteType()
	 */
	public NoteType getNoteType()
	{
		return noteType;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.INote#getTypeName()
	 */
	public String getTypeName()
	{
		return noteType.getName();
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.INote#getValue()
	 */
	public String getValue()
	{
		return value;
	}

}
