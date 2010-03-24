package org.cycads.extract.score;

import org.cycads.entities.annotation.Annotation;

public interface AnnotationScoreSystem
{
	public double getScore(Annotation annotation);

	// public double getScore(double score, AnnotationMethod annotationMethod);
}
