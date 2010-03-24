/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.extract.objectsGetter.ObjectsGetter;
import org.cycads.extract.score.AnnotationScoreSystem;

public interface AnnotationClustersGetter extends ObjectsGetter {

	public List<AnnotationCluster> getAnnotationClusters(Object obj);

	public AnnotationScoreSystem getScoreSystemCollection();

	public List<AnnotationWaysGetter> getAnnotationWaysGetters();

	// public List<NumberValidator> getRemoveByScore();
	//
	// public List<Pattern> getRemoveByString();

}
