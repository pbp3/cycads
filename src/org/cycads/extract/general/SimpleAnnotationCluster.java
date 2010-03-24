package org.cycads.extract.general;

import org.cycads.extract.score.AnnotationWayListScoreSystem;

public class SimpleAnnotationCluster extends SimpleAnnotationWayList implements AnnotationCluster
{

	Object							source;
	Object							target;
	AnnotationWayListScoreSystem	annotationWayListScoreSystem;

	public SimpleAnnotationCluster(Object source, Object target,
			AnnotationWayListScoreSystem annotationWayListScoreSystem) {
		this.source = source;
		this.target = target;
		this.annotationWayListScoreSystem = annotationWayListScoreSystem;
	}

	@Override
	public boolean add(AnnotationWay annotationWay) {
		if (!annotationWay.getTarget().equals(target)) {
			throw new RuntimeException("AnnotationCluster with 2 targets: " + target + "; " + annotationWay.getTarget());
		}
		if (!annotationWay.getSource().equals(source)) {
			throw new RuntimeException("AnnotationCluster with 2 sources: " + source + "; " + annotationWay.getSource());
		}
		return super.add(annotationWay);
	}

	@Override
	public double getScore() {
		return getScoreSystem().getScore(this);
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public Object getTarget() {
		return target;
	}

	public AnnotationWayListScoreSystem getScoreSystem() {
		return annotationWayListScoreSystem;
	}

}
