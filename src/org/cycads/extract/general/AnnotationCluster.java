/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.entities.annotation.AnnotationMethod;

public interface AnnotationCluster
{

	//Exclude repeated AnnotationWays

	public Object getSource();

	public Object getTarget();

	public double getScore();

	//	public String getMethodsStr();

	public List<List<AnnotationMethod>> getMethods();

	public void addAnnotationWay(AnnotationWay annotationWay);

	public AnnotationWayList getAnnotationWays();

}
