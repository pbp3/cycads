/*
 * Created on 23/10/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleEC extends SimpleDbxref implements EC
{

	public SimpleEC(String accession, EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > factory,
			EntityAnnotationFactory annotationFactory) {
		super(accession, EC.DBNAME, typeFactory, factory, annotationFactory);
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("Simple EC");
	}

}
