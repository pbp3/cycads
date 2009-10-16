package org.cycads.entities.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SimpleHasSynonymsNoteble;

public class SimpleAnnotation<SO extends AnnotationObject< ? >, TA extends AnnotationObject< ? >, AParent extends Annotation< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SimpleHasSynonymsNoteble<X> implements Annotation<SO, TA, AParent, X, T, M>
{

	EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? , ? >	factory;

	SO																	source;
	TA																	target;

	Collection<AParent>													parents	= new ArrayList<AParent>();
	Collection<T>														types	= new TreeSet<T>();
	M																	method;
	String																score;

	public SimpleAnnotation(SO source, TA target,
			EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? , ? > factory, M method) {
		super(factory);
		this.factory = factory;
		this.method = method;
		this.source = source;
		this.target = target;
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
		return types.contains(factory.getAnnotationType(type));
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

	// @Override
	// public int compareTo(Annotation<?, ?, ?, ?> annot) {
	// return this.getAnnotationMethod().compareTo(annot.getAnnotationMethod());
	// }
	//
}
