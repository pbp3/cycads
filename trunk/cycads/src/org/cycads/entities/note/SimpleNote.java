/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import org.biojava.ontology.Term;

public class SimpleNote<H extends NoteSource> implements Note<H>
{
	String	value;
	String	noteType;
	H		holder;

	public SimpleNote(H holder, String value, String noteType)
	{
		this.value = value;
		this.noteType = noteType;
		this.holder = holder;
	}

	public SimpleNote(H holder, String value, Term noteType)
	{
		this.value = value;
		this.noteType = noteType.getName();
		this.holder = holder;
	}

	public String getType()
	{
		return noteType;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Note))
		{
			return false;
		}
		Note o = (Note) obj;
		return (o.getValue().equals(this.getValue()) && o.getType().equals(this.getType()));
	}

	public H getHolder()
	{
		return holder;
	}

}
