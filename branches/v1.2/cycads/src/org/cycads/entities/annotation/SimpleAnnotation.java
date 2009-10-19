package org.cycads.entities.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.cycads.entities.EntityObject;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.SimpleHasSynonymsNoteble;

public class SimpleAnnotation<SO extends EntityObject, TA extends EntityObject> extends SimpleHasSynonymsNoteble
		implements Annotation<SO, TA>
{

	SO							source;
	TA							target;

	Collection<EntityObject>	parents	= new ArrayList<EntityObject>();
	Collection<Type>			types	= new TreeSet<Type>();
	AnnotationMethod			method;
	String						score;

	public SimpleAnnotation(SO source, TA target, AnnotationMethod method, EntityTypeFactory< ? > typeFactory,
			EntityDbxrefFactory< ? > dbxrefFactory) {
		super(typeFactory, dbxrefFactory);
		this.method = method;
		this.source = source;
		this.target = target;
	}

	@Override
	public void addParent(EntityObject parent) {
		parents.add(parent);
	}

	@Override
	public Type addType(String type) {
		Type t = typeFactory.getAnnotationType(type);
		types.add(t);
		return t;
	}

	@Override
	public Type addType(Type type) {
		types.add(type);
		return type;
	}

	@Override
	public AnnotationMethod getAnnotationMethod() {
		return method;
	}

	@Override
	public Collection<EntityObject> getParents() {
		return parents;
	}

	@Override
	public Collection<Type> getTypes() {
		return types;
	}

	@Override
	public boolean isType(String type) {
		return isType(typeFactory.getAnnotationType(type));
	}

	@Override
	public boolean isType(Type type) {
		return types.contains(type);
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public SO getSource() {
		return source;
	}

	@Override
	public TA getTarget() {
		return target;
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getNoteType(OBJECT_TYPE_NAME);
	}

	// @Override
	// public int compareTo(Annotation<?, ?, ?, ?> annot) {
	// return this.getAnnotationMethod().compareTo(annot.getAnnotationMethod());
	// }
	//
}
