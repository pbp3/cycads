/*
 * Created on 28/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

import org.biojavax.RichAnnotation;

public class NotesBJ<N extends Note< ? extends NoteSource>> implements NoteCollection<N>, ChangeListener
{
	NoteCollection	notes;
	RichAnnotation	annotation;

	public NotesBJ(NoteCollection notes, RichAnnotation annotation)
	{
		this.notes = notes;
		this.annotation = annotation;
	}

	@Override
	public N addNote(N note)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public N getNote(String value, String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<N> getNotes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<N> getNotes(String noteTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
