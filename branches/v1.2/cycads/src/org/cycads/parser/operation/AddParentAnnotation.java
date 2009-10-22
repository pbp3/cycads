/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.synonym.Dbxref;

public class AddParentAnnotation<SOURCE extends Annotation> extends SimpleRelationshipOperation<SOURCE, Annotation>
{

	private EntityDbxrefFactory	factory;
	private String				parentDBName;
	private AnnotationFinder	annotationFinder;

	public AddParentAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, String parentDBName,
			EntityDbxrefFactory factory, AnnotationFinder annotationFinder) {
		super(tagNameRegex, tagValueRegex);
		this.parentDBName = parentDBName;
		this.factory = factory;
		this.annotationFinder = annotationFinder;
	}

	@Override
	protected Collection<Annotation> execute(SOURCE source, Note note) {
		Dbxref parentSynonym = factory.getDbxref(parentDBName, note.getValue());
		Collection<Annotation> parents = annotationFinder.getAnnotations(null, null, parentSynonym);
		for (Annotation parent : parents) {
			source.addParent(parent);
		}
		return parents;
	}

	public EntityDbxrefFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityDbxrefFactory factory) {
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
