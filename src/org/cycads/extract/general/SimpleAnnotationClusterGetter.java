package org.cycads.extract.general;

import java.util.List;

import org.cycads.extract.score.ScoreSystemCollection;

public class SimpleAnnotationClusterGetter implements AnnotationClusterGetter {

	AnnotationClusterGetter otherAnnotationClusterGetter;
	AnnotationWaysGetter annotationWayGetter;

	public SimpleAnnotationClusterGetter(
			AnnotationClusterGetter otherAnnotationClusterGetter,
			AnnotationWaysGetter annotationWayGetter) {
		this.otherAnnotationClusterGetter = otherAnnotationClusterGetter;
		this.annotationWayGetter = annotationWayGetter;
	}

	@Override
	public List<AnnotationCluster> getAnnotationClusters(Object obj) {
		List<AnnotationCluster> ret = null;
		if (otherAnnotationClusterGetter != null) {
????
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScoreSystemCollection getScoreSystemCollection() {
		// TODO Auto-generated method stub
		return null;
	}
}
