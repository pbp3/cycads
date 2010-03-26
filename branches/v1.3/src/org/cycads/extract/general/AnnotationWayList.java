package org.cycads.extract.general;

import java.util.List;

public interface AnnotationWayList extends List<AnnotationWay>
{
	//Exclude repeated AnnotationWays

	//add obj as the first in the all AnnotationWays of this List
	public void addAllFirst(Object obj);

	public AnnotationWayListMethods getMethods();

}
