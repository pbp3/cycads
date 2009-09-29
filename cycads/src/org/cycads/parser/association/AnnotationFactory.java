/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.FileParserError;

public class AnnotationFactory implements FieldFactory<Annotation>
{
	EntityFactory	entityFactory;
	DbxrefFactory	dbxrefFactory;

	public AnnotationFactory(EntityFactory entityFactory, DbxrefFactory dbxrefFactory) {
		this.entityFactory = entityFactory;
		this.dbxrefFactory = dbxrefFactory;
	}

	@Override
	public Annotation create(String value) throws FileParserError {
		Dbxref dbxref = dbxrefFactory.create(value);
		Collection<Annotation> annots = entityFactory.getAnnotations(dbxref);
		if (annots != null && !annots.isEmpty()) {
			return annots.iterator().next();
		}
		return null;
	}

}
