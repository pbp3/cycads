/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;
import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.synonym.Dbxref;

public class AddParentAnnotation extends SimpleRelationshipOperation<Annotation, Annotation>
{

	private EntityFactory		factory;
	private String				parentDBName;
	private AnnotationFinder	annotationFinder;

	protected AddParentAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, String parentDBName,
			EntityFactory factory, AnnotationFinder annotationFinder) {
		super(tagNameRegex, tagValueRegex);
		this.parentDBName = parentDBName;
		this.factory = factory;
		this.annotationFinder = annotationFinder;
	}

	@Override
	protected Collection<Annotation> execute(Annotation source, Note note) {
		Dbxref parentSynonym = factory.getDbxref(parentDBName, note.getValue());
		Collection<Annotation> parents = annotationFinder.getAnnotations(null, null, parentSynonym);
		for (Annotation parent : parents) {
			source.addParent(parent);
		}
		return parents;
	}

}
