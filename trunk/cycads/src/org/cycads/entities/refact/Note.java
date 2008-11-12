/*
 * Created on 12/11/2008
 */
package org.cycads.entities.refact;

public class Note
{
	NoteType	noteType;

	String		value;

	public NoteType getNoteType()
	{
		return noteType;
	}

	public String getTypeName()
	{
		return noteType.getName();
	}

	public String getValue()
	{
		return value;
	}

}
