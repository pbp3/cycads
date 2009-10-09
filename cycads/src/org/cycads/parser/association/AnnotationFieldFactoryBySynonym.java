/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.FileParserError;

public class AnnotationFieldFactoryBySynonym implements FieldFactory<Annotation>
{
	AnnotationFinder	annotationFinder;
	DbxrefFieldFactory	dbxrefFieldFactory;

	public AnnotationFieldFactoryBySynonym(AnnotationFinder annotationFinder, DbxrefFieldFactory dbxrefFieldFactory) {
		this.annotationFinder = annotationFinder;
		this.dbxrefFieldFactory = dbxrefFieldFactory;
	}

	@Override
	public Annotation create(String value) throws FileParserError {
		Dbxref dbxref = dbxrefFieldFactory.create(value);
		Collection<Annotation> annots = annotationFinder.getAnnotations(null, null, dbxref);
		if (annots != null && !annots.isEmpty()) {
			return annots.iterator().next();
		}
		return null;
	}

}
