/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.Set;

import org.biojava.ontology.Term;
import org.biojavax.RichAnnotation;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.general.biojava.TermsAndOntologies;

public class NoteWithTermBJ<H extends NoteSource> implements Note<H>
{
	String	value;
	Term	noteType;
	H		holder;

	public NoteWithTermBJ(H holder, String value, String noteType) {
		this.value = value;
		this.noteType = TermsAndOntologies.getOntologyNotes().getOrCreateTerm(noteType);
		this.holder = holder;
	}

	public NoteWithTermBJ(Note<H> note) {
		this.value = note.getValue();
		this.noteType = TermsAndOntologies.getOntologyNotes().getOrCreateTerm(note.getType());
		this.holder = note.getHolder();
	}

	public NoteWithTermBJ(H holder, String value, Term noteType) {
		this.value = value;
		this.noteType = noteType;
		this.holder = holder;
	}

	public String getType() {
		return noteType.getName();
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Note)) {
			return false;
		}
		Note o = (Note) obj;
		return (o.getValue().equals(this.getValue()) && o.getType().equals(this.getType()));
	}

	public H getHolder() {
		return holder;
	}

	public static org.biojavax.Note addNoteForAnnotation(Note note, RichAnnotation annot) {
		return addNoteForAnnotation(note.getValue(), note.getType(), annot);
	}

	public static org.biojavax.Note addNoteForAnnotation(String value, String type, RichAnnotation annot) {
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

	public static org.biojavax.Note removeNoteForAnnotation(Note note, RichAnnotation annot) {
		return removeNoteForAnnotation(note.getValue(), note.getType(), annot);
	}

	public static org.biojavax.Note removeNoteForAnnotation(String value, String type, RichAnnotation annot) {
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
