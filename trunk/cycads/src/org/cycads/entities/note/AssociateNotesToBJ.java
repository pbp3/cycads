/*
 * Created on 28/11/2008
 */
package org.cycads.entities.note;

import org.biojava.bio.Annotatable;
import org.biojavax.RichAnnotation;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;

public class AssociateNotesToBJ<N extends Note<H>, H extends NoteSource> implements ChangeListener<N>,
		org.biojava.utils.ChangeListener
{
	NotesContainer<N>	notes;
	RichAnnotation		annotation;
	H					source;

	private AssociateNotesToBJ(NotesContainer<N> notes, RichAnnotation annotation, H source)
	{
		this.notes = notes;
		this.annotation = annotation;
		this.source = source;
		annotation.addChangeListener(this, Annotatable.ANNOTATION);
		notes.addChangeListener(this, ChangeType.NOTE);
	}

	public static <N extends Note<H>, H extends NoteSource> NotesHashTable<N> createNotesHashTable(
			RichAnnotation annotation, H source)
	{
		NotesHashTable<N> notes = new NotesHashTable<N>();
		new AssociateNotesToBJ<N, H>(notes, annotation, source);
		return notes;
	}

	public static <N extends Note<H>, H extends NoteSource> NotesArrayList<N> createNotesArrayList(
			RichAnnotation annotation, H source)
	{
		NotesArrayList<N> notes = new NotesArrayList<N>();
		new AssociateNotesToBJ<N, H>(notes, annotation, source);
		return notes;
	}

	// this method is fired by BioJava Annotation
	public void postChange(org.biojava.utils.ChangeEvent cev)
	{
		notes.removeChangeListener(this, ChangeType.NOTE);

		org.biojavax.Note noteOld = (org.biojavax.Note) cev.getPrevious();
		if (noteOld != null)
		{
			// notes.removeNote(noteOld.getValue(), noteOld.getTerm().getName());
		}
		org.biojavax.Note noteNew = (org.biojavax.Note) cev.getChange();
		if (noteNew != null)
		{
			notes.addNote((N) source.createNote(noteNew.getValue(), noteNew.getTerm().getName()));
		}

		notes.addChangeListener(this, ChangeType.NOTE);
	}

	public void preChange(org.biojava.utils.ChangeEvent cev)
	{
		// nothing to do
	}

	// this method is fired by NotesCollection
	// the method needs to change the annotation
	public void postChange(org.cycads.entities.change.ChangeEvent<N> cev)
	{
		annotation.removeChangeListener(this, Annotatable.ANNOTATION);

		N noteOld = cev.getPrevious();
		if (noteOld != null)
		{
			NoteWithTermBJ.removeNoteForAnnotation(noteOld, annotation);
		}
		N noteNew = cev.getChange();
		if (noteNew != null)
		{
			NoteWithTermBJ.addNoteForAnnotation(noteNew, annotation);
		}

		annotation.addChangeListener(this, Annotatable.ANNOTATION);
	}

	public void preChange(org.cycads.entities.change.ChangeEvent<N> cev)
			throws org.cycads.entities.change.ChangeVetoException
	{
		// nothing to do
	}
}
