/*
 * Created on 31/10/2008
 */
package org.cycads.entities.biojava1;

import org.biojavax.ontology.ComparableTerm;
import org.cycads.general.biojava.TermEncapsulator;

public class FeatureNoteTypeBJ implements FeatureNoteType, TermEncapsulator
{
	ComparableTerm	term;

	public FeatureNoteTypeBJ(ComparableTerm term) {
		this.term = term;
	}

	public String getName() {
		return term.getName();
	}

	public ComparableTerm getTerm() {
		return term;
	}

	public boolean equals(Object o) {
		if (!(o instanceof TermEncapsulator)) {
			return false;
		}
		return term.equals(((TermEncapsulator) o).getTerm());
	}

}
