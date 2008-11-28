/*
 * Created on 28/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

import org.biojava.bio.Annotatable;
import org.biojava.utils.ChangeEvent;
import org.biojava.utils.ChangeListener;
import org.biojava.utils.ChangeVetoException;
import org.biojavax.RichAnnotation;

public class NotesBJ<N extends Note<H>, H extends NoteSource> implements NotesContainer<N>, ChangeListener
{
	NotesContainer<N>	notes;
	RichAnnotation		annotation;
	H					source;

	public NotesBJ(NotesContainer<N> notes, RichAnnotation annotation, H source) {
		this.notes = notes;
		this.annotation = annotation;
		this.annotation.addChangeListener(this, Annotatable.ANNOTATION);
		this.source = source;
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
