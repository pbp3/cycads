package org.cycads.extract.general;

import java.util.ArrayList;
import java.util.List;

import org.cycads.extract.parser.AnnotationWaysGetterReader;
import org.cycads.extract.score.ScoreSystemCollection;
import org.cycads.parser.ParserException;

public class SimpleAnnotationClusterGetter implements AnnotationClustersGetter {

	List<AnnotationWaysGetter> annotationWaysGetters;
	ScoreSystemCollection scoreSystemCollection;

	public SimpleAnnotationClusterGetter(
			List<AnnotationWaysGetter> annotationWaysGetters,
			ScoreSystemCollection scoreSystemCollection) {
		this.annotationWaysGetters = annotationWaysGetters;
		this.scoreSystemCollection = scoreSystemCollection;
	}

	public SimpleAnnotationClusterGetter(List<String> locations,
			AnnotationWaysGetterReader annotationWaysGetterReader,
			ScoreSystemCollection scoreSystemCollection) throws ParserException {
		this.scoreSystemCollection = scoreSystemCollection;
		annotationWaysGetters = new ArrayList<AnnotationWaysGetter>(locations
				.size());
		for (String location : locations) {
			annotationWaysGetters.add(annotationWaysGetterReader
					.parse(location));
		}
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
	public List<AnnotationWaysGetter> getAnnotationWaysGetters() {
		return annotationWaysGetters;
	}

	@Override
	public ScoreSystemCollection getScoreSystemCollection() {
		return scoreSystemCollection;
	}

	@Override
	public List<? extends Object> getObjects(Object obj)
			throws GetterExpressionException {
		return getAnnotationClusters(obj);
	}
}
