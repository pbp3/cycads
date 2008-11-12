/*
 * Created on 20/10/2008
 */
package org.cycads.entities.biojava1;

import java.util.ArrayList;
import java.util.Collection;

import org.biojava.ontology.Term;
import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.TermsAndOntologies;

public class MethodTypeBJ implements MethodType
{
	public static final MethodType	CDS_TO_KO	= new MethodTypeBJ(ParametersDefault.cdsToKOMethodType());

	public static final MethodType	CDS_TO_EC	= new MethodTypeBJ(ParametersDefault.cdsToECMethodType());

	ComparableOntology				ont;

	public MethodTypeBJ(ComparableOntology ont) {
		this.ont = ont;
	}

	public MethodTypeBJ(String methodTypeName) {
		this.ont = TermsAndOntologies.getOntologyMethodType(methodTypeName);
	}

	public Collection<Method> getMethods() {
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Term term : ont.getTerms()) {
			methods.add(new MethodBJ((ComparableTerm) term));
		}
		return methods;
	}

	public String getName() {
		return ont.getName();
	}

	public Method getOrCreateMethod(String name) {
		if (ont.containsTerm(name)) {
			return new MethodBJ(ont.getOrCreateTerm(name));
		}
		else {
			ComparableTerm term = ont.getOrCreateTerm(name);
			term.setDescription(ParametersDefault.methodDescription());
			return new MethodBJ(term);
		}
	}

}
