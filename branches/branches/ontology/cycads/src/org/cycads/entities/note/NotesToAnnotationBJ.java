/*
 * Created on 28/11/2008
 */
package org.cycads.entities.note;

import java.util.Set;

import org.biojava.bio.Annotatable;
import org.biojavax.RichAnnotation;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.general.biojava.TermsAndOntologies;

public class NotesToAnnotationBJ<N extends Note< ? >, S extends NoteSource< ? >>
		implements ChangeListener<N>, org.biojava.utils.ChangeListener
{
	NotesContainer<N>	notes;
	RichAnnotation		annotation;
	S					source;

	private NotesToAnnotationBJ(NotesContainer<N> notes, RichAnnotation annotation, S source) {
		this.notes = notes;
		this.annotation = annotation;
		this.source = source;
		annotation.addChangeListener(this, Annotatable.ANNOTATION);
		notes.addChangeListener(this, ChangeType.NOTE);
	}

	public static <N extends Note< ? >, H extends NoteSource< ? >> NotesHashTable<N> createNotesHashTable(
			RichAnnotation annotation, H source) {
		NotesHashTable<N> notes = new NotesHashTable<N>();
		new NotesToAnnotationBJ<N, H>(notes, annotation, source);
		return notes;
	}

	public static <N extends Note< ? >, H extends NoteSource< ? >> NotesArrayList<N> createNotesArrayList(
			RichAnnotation annotation, H source) {
		NotesArrayList<N> notes = new NotesArrayList<N>();
		new NotesToAnnotationBJ<N, H>(notes, annotation, source);
		return notes;
	}

	// this method is fired by BioJava Annotation
	public void postChange(org.biojava.utils.ChangeEvent cev) {
		notes.removeChangeListener(this, ChangeType.NOTE);

		org.biojavax.Note noteOld = (org.biojavax.Note) cev.getPrevious();
		if (noteOld != null) {
			// notes.removeNote(noteOld.getValue(), noteOld.getTerm().getName());
		}
		org.biojavax.Note noteNew = (org.biojavax.Note) cev.getChange();
		if (noteNew != null) {
			notes.addNote(source.createNote(noteNew.getTerm().getName(), noteNew.getValue()));
		}

		notes.addChangeListener(this, ChangeType.NOTE);
	}

	public void preChange(org.biojava.utils.ChangeEvent cev) {
		// nothing to do
	}

	// this method is fired by NotesCollection
	// the method needs to change the annotation
	public void postChange(org.cycads.entities.change.ChangeEvent<N> cev) {
		annotation.removeChangeListener(this, Annotatable.ANNOTATION);

		N noteOld = cev.getPrevious();
		if (noteOld != null) {
			removeNoteForAnnotation(noteOld, annotation);
		}
		N noteNew = cev.getChange();
		if (noteNew != null) {
			addNoteForAnnotation(noteNew, annotation);
		}

		annotation.addChangeListener(this, Annotatable.ANNOTATION);
	}

	public void preChange(org.cycads.entities.change.ChangeEvent<N> cev)
			throws org.cycads.entities.change.ChangeVetoException {
		// nothing to do
	}

	public org.biojavax.Note addNoteForAnnotation(N note, RichAnnotation annot) {
		return addNoteForAnnotation(note.getValue(), note.getType(), annot);
	}

	public org.biojavax.Note addNoteForAnnotation(String value, String type, RichAnnotation annot) {
		ComparableTerm noteType = TermsAndOntologies.getOntologyNotes().getOrCreateTerm(type);
		Set<org.biojavax.Note> notes = annot.getNoteSet();
		int rank = 0;
		for (org.biojavax.Note note : notes) {
			if (note.getTerm().equals(noteType)) {
				if (note.getValue().equalsIgnoreCase(value)) {
					return null;
				}
				rank++;
			}
		}
		org.biojavax.Note note = new org.biojavax.SimpleNote(noteType, value, rank);
		annot.addNote(note);
		return note;
	}

	public org.biojavax.Note removeNoteForAnnotation(N note, RichAnnotation annot) {
		return removeNoteForAnnotation(note.getValue(), note.getType(), annot);
	}

	public org.biojavax.Note removeNoteForAnnotation(String value, String type, RichAnnotation annot) {
		ComparableTerm noteType = TermsAndOntologies.getOntologyNotes().getOrCreateTerm(type);
		Set<org.biojavax.Note> notes = annot.getNoteSet();
		org.biojavax.Note noteToRemove = null;
		for (org.biojavax.Note note : notes) {
			if ((note.getTerm().equals(noteType)) && (note.getValue().equalsIgnoreCase(value))) {
				noteToRemove = note;
			}
		}
		if (noteToRemove != null) {
			annot.removeNote(noteToRemove);
		}
		return noteToRemove;
	}

}
