package org.cycads.extract.general;

import java.util.List;

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
	public List<AnnotationCluster> getAnnotationClusters(Object obj,
			String definitionName) {
		List<AnnotationCluster> ret = null;
		if (otherAnnotationClusterGetter != null) {
????
		}
		// TODO Auto-generated method stub
		return null;
	}
}
