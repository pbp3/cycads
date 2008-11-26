/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import org.biojava.ontology.Term;
import org.cycads.general.biojava.TermsAndOntologies;

public class NoteBJ<H extends NoteSource> implements Note<H>
{
	String	value;
	Term	noteType;
	H		holder;

	public NoteBJ(H holder, String value, String noteType) {
		this.value = value;
		this.noteType = TermsAndOntologies.getOntologyNotes().getOrCreateTerm(noteType);
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
