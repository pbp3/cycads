/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.ArrayList;
import java.util.Collection;

public class NotesArrayList<N extends Note< H extends NoteSource>> extends ArrayList<N> implements NoteCollection<N>
{
	public NotesArrayList()
	{
		super();
	}

	public NotesArrayList(int initialCapacity)
	{
		super(initialCapacity);
	}

	public N addNote(N note)
	{
		N note1 = getNote(note.getValue(), note.getType());
		if (note1 != null)
		{
			return note1;
		}
		add(note);
		return note;
	}

	public Collection<N> getNotes()
	{
		return this;
	}

	public Collection<N> getNotes(String noteTypeName)
	{
		ArrayList<N> notes = new ArrayList<N>();
		for (N note : this)
		{
			if (note.getType().equals(noteTypeName))
			{
				notes.add(note);
			}
		}
		return notes;
	}

	public N getNote(String value, String noteTypeName)
	{
		for (N note : this)
		{
			if (note.getType().equals(noteTypeName) && note.getValue().equals(value))
			{
				return note;
			}
		}
		return null;
	}

}
