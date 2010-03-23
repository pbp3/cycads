/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.extract.objectsGetter.ObjectsGetter;
import org.cycads.extract.score.ScoreSystemCollection;

public interface AnnotationClustersGetter extends ObjectsGetter
{
	// public List<AnnotationCluster> getAnnotationClustera(
	// Collection<Object> objs, String definitionName);

	public List<AnnotationCluster> getAnnotationClusters(Object obj);

	public List<String> getLocations();

	public ScoreSystemCollection getScoreSystemCollection();

	// public List<NumberValidator> getRemoveByScore();
	//
	// public List<Pattern> getRemoveByString();

}
