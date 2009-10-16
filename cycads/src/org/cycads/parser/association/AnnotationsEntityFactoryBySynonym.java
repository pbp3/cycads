/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class AnnotationsEntityFactoryBySynonym implements ObjectFactory<Collection<Annotation>>
{
	AnnotationFinder					annotationFinder;
	ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;

	public AnnotationsEntityFactoryBySynonym(AnnotationFinder annotationFinder,
			ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.annotationFinder = annotationFinder;
		this.dbxrefsFactory = dbxrefsFactory;
	}

	@Override
	public Collection<Annotation> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return new ArrayList<Annotation>();
		}
		Collection<Annotation> ret = null;
		for (Dbxref dbxref : dbxrefs) {
			if (ret == null) {
				ret = annotationFinder.getAnnotations(null, null, dbxref);
			}
			else {
				ret.addAll(annotationFinder.getAnnotations(null, null, dbxref));
			}
		}
		return ret;
	}

}
