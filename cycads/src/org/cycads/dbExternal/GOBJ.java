/*
 * Created on 09/06/2008
 */
package org.cycads.dbExternal;

import org.biojavax.ontology.ComparableTerm;
import org.cycads.general.biojava.TermsAndOntologies;

public class GOBJ implements GO
{
	ComparableTerm	term;

	public GOBJ(String id) {
		term = TermsAndOntologies.getOntologyGO().getOrCreateTerm(id);
	}

	public GOBJ(ComparableTerm term) {
		this.term = term;
	}

	/* (non-Javadoc)
	 * @see org.cycads.dbExternal.GO#getId()
	 */
	public String getId() {
		return term.getName();
	}

	public ComparableTerm getTerm() {
		return term;
	}

	public int compareTo(GO go) {
		if (go instanceof GOBJ) {
			return term.compareTo(((GOBJ) go).getTerm());
		}
		return getId().compareTo(go.getId());
	}

}
