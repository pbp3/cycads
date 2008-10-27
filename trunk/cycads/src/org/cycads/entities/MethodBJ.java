/*
 * Created on 20/10/2008
 */
package org.cycads.entities;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;

public class MethodBJ implements Method
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

	public void setDescription(String methodDescription) {
		term.setDescription(methodDescription);
	}
}
