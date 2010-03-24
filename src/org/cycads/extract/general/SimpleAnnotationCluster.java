package org.cycads.extract.general;

import org.cycads.extract.score.AnnotationScoreSystemCollection;
import org.cycads.general.ParametersDefault;

public class SimpleAnnotationCluster extends SimpleAnnotationWayList implements AnnotationCluster
{

	Object					source;
	Object					target;
	AnnotationScoreSystemCollection	annotationScoreSystemCollection;

	public SimpleAnnotationCluster(Object source, Object target, AnnotationScoreSystemCollection annotationScoreSystemCollection) {
		this.source = source;
		this.target = target;
		this.annotationScoreSystemCollection = annotationScoreSystemCollection;
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
		if (getScoreSystemCollection() == null) {
			return ParametersDefault.getAnnotationClusterScoreDefault();
		}
		else {

		}
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public Object getTarget() {
		return target;
	}

	public AnnotationScoreSystemCollection getScoreSystemCollection() {
		return annotationScoreSystemCollection;
	}

}
