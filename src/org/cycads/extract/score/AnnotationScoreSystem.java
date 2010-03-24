/*
 * Created on 22/04/2009
 */
package org.cycads.extract.score;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public interface AnnotationScoreSystem
{

	public TransformScore getTransformScore(AnnotationMethod method);

	public double getScore(Annotation annotation);

}
