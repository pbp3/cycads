/*
 * Created on 24/03/2010
 */
package org.cycads.extract.general;

import java.util.List;
import java.util.regex.Pattern;

import org.cycads.extract.score.AnnotationWayListScoreSystem;
import org.cycads.extract.score.SimpleAnnotationScoreSystem;
import org.cycads.extract.score.SimpleAnnotationWayListScoreSystem;
import org.cycads.extract.score.TransformScore;
import org.cycads.general.Config;

public class ConfigAnnotationClustersGetterRepository implements AnnotationClustersGetterRepository
{

	@Override
	public AnnotationClustersGetter getAnnotationClusterGetter(String clusterName) {
		List<String> locations = Config.getAnnotationClusterLocs(clusterName);
		AnnotationWayListScoreSystem scoreSystem = getScoreSystem(clusterName);
		if (locations != null && !locations.isEmpty()) {
			return new SimpleAnnotationClustersGetter(locations, scoreSystem);
		}
		else {
			return null;
		}
	}

	public AnnotationWayListScoreSystem getScoreSystem(String clusterName) {
		SimpleAnnotationScoreSystem annotationScoreSystem = new SimpleAnnotationScoreSystem();
		List<Pattern> patterns = Config.getScoreMethodPatterns(clusterName);
		List<TransformScore> transformScores = Config.getScoreMethodTransforms(clusterName);
		for (int i = 0; i < patterns.size(); i++) {
			annotationScoreSystem.addTransformScore(patterns.get(i), transformScores.get(i));
		}
		return new SimpleAnnotationWayListScoreSystem(annotationScoreSystem);
	}
}
