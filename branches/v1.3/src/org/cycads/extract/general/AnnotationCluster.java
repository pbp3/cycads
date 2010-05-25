/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import org.cycads.extract.score.AnnotationWayListScoreSystem;

public interface AnnotationCluster extends AnnotationWayList
{

	//all AnnotWays with the same source and target
	//score system

	public Object getSource();

	public Object getTarget();

	public double getScore();

	public AnnotationWayListScoreSystem getScoreSystem();

	void setScoreSystem(AnnotationWayListScoreSystem annotationWayListScoreSystem);

}
