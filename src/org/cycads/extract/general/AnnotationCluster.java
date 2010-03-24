/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

public interface AnnotationCluster extends AnnotationWayList
{

	//Exclude repeated AnnotationWays
	//all AnnotWays with the same source and target
	//score system

	public Object getSource();

	public Object getTarget();

	public double getScore();

}
