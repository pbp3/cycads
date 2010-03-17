package org.cycads.entities.annotation;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleAnnotation<SO extends BasicEntity, TA extends BasicEntity> extends SimpleAssociation<SO, TA>
		implements Annotation<SO, TA>
{

	AnnotationMethod			method;
	String						score;
	Collection<BasicEntity>	parents	= new ArrayList<BasicEntity>();

	public SimpleAnnotation(SO source, TA target, AnnotationMethod method, String score,
			EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > dbxrefFactory,
			EntityAnnotationFactory< ? > annotationFacotry) {
		super(source, target, typeFactory, dbxrefFactory, annotationFacotry);
		this.method = method;
		this.score = score;
	}

	@Override
	public void addParent(BasicEntity parent) {
		parents.add(parent);
	}

	@Override
	public AnnotationMethod getAnnotationMethod() {
		return method;
	}

	@Override
	public Collection<BasicEntity> getParents() {
		return parents;
	}

	@Override
	public String getScore() {
		return score;
	}

	@Override
	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("SimpleAnnotation");
	}

	@Override
	public String getEntityTypeName() {
		return "SimpleAnnotation";
	}

}
