/*
 * Created on 20/10/2008
 */
package org.cycads.entities.biojava;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.general.biojava.TermEncapsulator;

public class MethodBJ implements Method, TermEncapsulator
{
	ComparableTerm	term;

	public MethodBJ(ComparableTerm term) {
		this.term = term;
	}

	public String getName() {
		return term.getName();
	}

	public MethodType getType() {
		return new MethodTypeBJ((ComparableOntology) term.getOntology());
	}

	public ComparableTerm getTerm() {
		return term;
	}

	//	public void setDescription(String methodDescription) {
	//		term.setDescription(methodDescription);
	//	}
	//
	public int getWeight() {
		return Integer.parseInt(term.getDescription());
	}

	public void setWeight(int weight) {
		term.setDescription("" + weight);
	}

	public boolean equals(Object o) {
		if (!(o instanceof TermEncapsulator)) {
			return false;
		}
		return term.equals(((TermEncapsulator) o).getTerm());
	}

}
