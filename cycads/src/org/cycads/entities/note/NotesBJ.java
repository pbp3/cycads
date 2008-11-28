/*
 * Created on 28/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

import org.biojava.utils.ChangeEvent;
import org.biojava.utils.ChangeListener;
import org.biojava.utils.ChangeVetoException;
import org.biojavax.RichAnnotation;

public class NotesBJ<H extends NoteSource> implements NoteCollection<Note<H>>, ChangeListener
{
	NoteCollection<N>	notes;
	RichAnnotation		annotation;

	public NotesBJ(NoteCollection<N> notes, RichAnnotation annotation) {
		this.notes = notes;
		this.annotation = annotation;
	}

	public N addNote(N note) {
		// TODO Auto-generated method stub
		return null;
	}

	public N getNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<N> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<N> getNotes(String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void postChange(ChangeEvent cev) {
		// TODO Auto-generated method stub

	}

	public void preChange(ChangeEvent cev) throws ChangeVetoException {
		// TODO Auto-generated method stub

	}
}
