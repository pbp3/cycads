package org.cycads.extract.general;

import java.util.List;

import org.cycads.entities.annotation.AnnotationMethod;

public interface AnnotationWay extends List<Object> {

	// use these methods to add objects

	public void addFirst(Object o);

	public void addLast(Object o);

	public Object getSource();

	public Object getTarget();

	public List<AnnotationMethod> getMethods();

}
