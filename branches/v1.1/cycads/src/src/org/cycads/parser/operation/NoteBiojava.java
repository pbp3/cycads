/*
 * Created on 16/09/2009
 */
package org.cycads.parser.operation;

import org.biojavax.RichObjectFactory;
import org.biojavax.ontology.ComparableTerm;

public class NoteBiojava implements Note
{

	private org.biojavax.Note	noteBiojava;

	public NoteBiojava(org.biojavax.Note noteBiojava) {
		this.noteBiojava = noteBiojava;
	}

	@Override
	public String getType() {
		return noteBiojava.getTerm().getName();
	}

	@Override
	public String getValue() {
		return noteBiojava.getValue();
	}

	@Override
	public void setType(String type) {
		noteBiojava.setTerm(RichObjectFactory.getDefaultOntology().getOrCreateTerm(type));
	}

	public void setType(ComparableTerm type) {
		noteBiojava.setTerm(type);
	}

	@Override
	public void setValue(String value) {
		noteBiojava.setValue(value);
	}

}
