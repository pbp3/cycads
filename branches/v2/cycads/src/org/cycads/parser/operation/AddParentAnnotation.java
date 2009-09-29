/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.synonym.Dbxref;

public class AddParentAnnotation<A extends Annotation> extends SimpleRelationshipOperation<A, Annotation>
{

	private EntityFactory		factory;
	private String				parentDBName;
	private AnnotationFinder	annotationFinder;

	public AddParentAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, String parentDBName, EntityFactory factory,
			AnnotationFinder annotationFinder) {
		super(tagNameRegex, tagValueRegex);
		this.parentDBName = parentDBName;
		this.factory = factory;
		this.annotationFinder = annotationFinder;
	}

	@Override
	protected Collection<Annotation> execute(A source, Note note) {
		Dbxref parentSynonym = factory.getDbxref(parentDBName, note.getValue());
		Collection<Annotation> parents = annotationFinder.getAnnotations(null, null, parentSynonym);
		for (Annotation parent : parents) {
			source.addParent(parent);
		}
		return parents;
	}

	public EntityFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}

	public String getParentDBName() {
		return parentDBName;
	}

	public void setParentDBName(String parentDBName) {
		this.parentDBName = parentDBName;
	}

	public AnnotationFinder getAnnotationFinder() {
		return annotationFinder;
	}

	public void setAnnotationFinder(AnnotationFinder annotationFinder) {
		this.annotationFinder = annotationFinder;
	}

}
