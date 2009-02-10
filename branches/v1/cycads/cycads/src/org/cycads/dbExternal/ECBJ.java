/*
 * Created on 09/06/2008
 */
package org.cycads.dbExternal;

import org.biojavax.ontology.ComparableTerm;
import org.cycads.general.biojava.TermsAndOntologies;

public class ECBJ implements EC
{
	ComparableTerm	term;

	public ECBJ(String id) {
		term = TermsAndOntologies.getOntologyEC().getOrCreateTerm(id);
	}

	public ECBJ(ComparableTerm term) {
		this.term = term;
	}

	/* (non-Javadoc)
	 * @see org.cycads.dbExternal.EC#getId()
	 */
	public String getId() {
		return term.getName();
	}

	public ComparableTerm getTerm() {
		return term;
	}

	public int compareTo(EC ec) {
		if (ec instanceof ECBJ) {
			return term.compareTo(((ECBJ) ec).getTerm());
		}
		return getId().compareTo(ec.getId());
	}

}
