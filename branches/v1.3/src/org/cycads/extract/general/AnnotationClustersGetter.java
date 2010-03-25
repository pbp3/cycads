/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.extract.objectsGetter.ObjectsGetter;
import org.cycads.extract.score.AnnotationWayListScoreSystem;

public interface AnnotationClustersGetter extends ObjectsGetter
{

	public List<AnnotationCluster> getAnnotationClusters(Object obj) throws GetterExpressionException;

	public AnnotationWayListScoreSystem getScoreSystem();

	public List<AnnotationWaysGetter> getAnnotationWaysGetters();

	public String getMsgChangeTarget();

	public void setMsgChangeTarget(String msgChangeTarget);

	// public List<NumberValidator> getRemoveByScore();
	//
	// public List<Pattern> getRemoveByString();

}
