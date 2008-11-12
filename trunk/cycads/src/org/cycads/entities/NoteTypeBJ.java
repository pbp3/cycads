/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Hashtable;

import org.biojava.ontology.Term;
import org.cycads.general.biojava.TermsAndOntologies;

public class NoteTypeBJ implements NoteType
{
	static Hashtable<String, NoteTypeBJ>	hash	= new Hashtable<String, NoteTypeBJ>();
	Term									term;

	private NoteTypeBJ(Term term) {
		this.term = term;
	}

	public String getName() {
		return term.getName();
	}

	public static NoteTypeBJ getNoteTypeInstance(String noteTypeName) {
		if (noteTypeName == null) {
			return null;
		}

		NoteTypeBJ ret;
		try {
			ret = hash.get(noteTypeName);
			if (ret == null || !ret.getName().equals(noteTypeName)) {
				hash = new Hashtable<String, NoteTypeBJ>();
				ret = null;
			}
		}
		catch (Exception e) {
			ret = null;
			hash = new Hashtable<String, NoteTypeBJ>();
		}

		if (ret == null) {
			ret = new NoteTypeBJ(TermsAndOntologies.getOntologyNotes().getOrCreateTerm(noteTypeName));
			hash.put(noteTypeName, ret);
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NoteType)) {
			return false;
		}
		return ((NoteType) obj).getName().equals(this.getName());
	}

}
