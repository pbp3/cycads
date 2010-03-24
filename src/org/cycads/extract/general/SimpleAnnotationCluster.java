package org.cycads.extract.general;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.extract.score.ScoreSystemCollection;

public class SimpleAnnotationCluster implements AnnotationCluster {

	Object source;
	Object target;
	ScoreSystemCollection scoreSystemCollection;
	List<AnnotationWay> annotationWays = new ArrayList<AnnotationWay>();

	public SimpleAnnotationCluster(Object source, Object target,
			ScoreSystemCollection scoreSystemCollection) {
		this.source = source;
		this.target = target;
		this.scoreSystemCollection = scoreSystemCollection;
	}

	@Override
	public void addAnnotationWay(AnnotationWay annotationWay) {
		if (!annotationWay.getTarget().equals(target)) {
			throw new RuntimeException("AnnotationCluster with 2 targets: "
					+ target + "; " + annotationWay.getTarget());
		}
		if (!annotationWay.getSource().equals(source)) {
			throw new RuntimeException("AnnotationCluster with 2 sources: "
					+ source + "; " + annotationWay.getSource());
		}
		annotationWays.add(annotationWay);
	}

	@Override
	public List<List<AnnotationMethod>> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMethodsStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getScore() {
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

}
