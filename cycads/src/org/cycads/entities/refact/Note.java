/*
 * Created on 12/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.INote;
import org.cycads.entities.INoteType;

public class Note implements INote
{
	INoteType	noteType;

	String		value;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.INote#getNoteType()
	 */
	public INoteType getNoteType()
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
