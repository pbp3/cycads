/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import org.biojava.ontology.Term;
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

}
