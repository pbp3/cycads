package org.cycads.extract.general;

import java.util.List;

public interface AnnotationWay extends List<Object>
{

	// use these methods to add objects

	public void addFirst(Object o);

	public void addLast(Object o);

	public Object getSource();

	public Object getTarget();

	public AnnotationWayListMethods getMethods();

}
