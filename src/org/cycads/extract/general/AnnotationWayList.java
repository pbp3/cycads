package org.cycads.extract.general;

import java.util.List;

public interface AnnotationWayList extends List<AnnotationWay>
{

	//add obj as the first in the all AnnotationWays of this List
	public void addAllFirst(Object obj);

	//	//add all elements in the annotationWays argument to this and 
	//	//add obj as the first in the all AnnotationWays of this List
	//	public void addAllAndFirst(AnnotationWayList annotationWays, Object obj);

	public AnnotationWayListMethods getMethods();

}
