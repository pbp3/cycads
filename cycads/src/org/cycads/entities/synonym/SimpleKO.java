/*
 * Created on 23/10/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleKO extends SimpleDbxref implements KO
{

	String	definition;
	String	name;

	public SimpleKO(String accession, EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > factory,
			EntityAnnotationFactory annotationFactory) {
		super(accession, KO.DBNAME, typeFactory, factory, annotationFactory);
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("Simple KO");
	}

	@Override
	public String getDefinition() {
		return definition;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
