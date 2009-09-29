package org.cycads.entities.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SimpleHasSynonymsNoteble;

public class SimpleAnnotation<AParent extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SimpleHasSynonymsNoteble<X> implements Annotation<AParent, X, T, M>
{

	EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? , ? >	factory;

	Collection<AParent>													parents	= new ArrayList<AParent>();
	Collection<T>														types	= new TreeSet<T>();
	M																	method;
	String																score;

	public SimpleAnnotation(EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? , ? > factory, M method) {
		super(factory);
		this.factory = factory;
		this.method = method;
	}

	@Override
	public void addParent(AParent parent) {
		parents.add(parent);
	}

	@Override
	public T addType(String type) {
		T t = factory.getAnnotationType(type);
		types.add(t);
		return t;
	}

	@Override
	public T addType(T type) {
		types.add(type);
		return type;
	}

	@Override
	public M getAnnotationMethod() {
		return method;
	}

	@Override
	public Collection<AParent> getParents() {
		return parents;
	}

	@Override
	public Collection<T> getTypes() {
		return types;
	}

	@Override
	public boolean hasType(String type) {
		return types.contains(factory.getAnnotationMethod(type));
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	// @Override
	// public int compareTo(Annotation<?, ?, ?, ?> annot) {
	// return this.getAnnotationMethod().compareTo(annot.getAnnotationMethod());
	// }
	//
}
