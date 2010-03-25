/*
 * Created on 24/03/2010
 */
package org.cycads.extract.score;

import org.cycads.entities.annotation.Annotation;
import org.cycads.extract.general.AnnotationWay;
import org.cycads.extract.general.AnnotationWayList;
import org.cycads.general.ParametersDefault;

public class SimpleAnnotationWayListScoreSystem implements AnnotationWayListScoreSystem
{
	AnnotationScoreSystem	annotationScoreSystem;

	public SimpleAnnotationWayListScoreSystem(AnnotationScoreSystem annotationScoreSystem) {
		setAnnotationScoreSystem(annotationScoreSystem);
	}

	@Override
	public double getScore(AnnotationWayList annotationWayList) {
		// if (annotationWayList.isEmpty()) {
		// // return ParametersDefault.getAnnotationWayListScoreDefault();
		// return 0;
		// }
		double ret = 0;
		double scoreWay;
		Object objPrevious;
		Annotation annot;
		for (AnnotationWay annotationWay : annotationWayList) {
			if (!annotationWay.isEmpty()) {
				scoreWay = 1;
				objPrevious = null;
				for (Object obj : annotationWay) {
					if (obj instanceof Annotation) {
						annot = (Annotation) obj;
						if (annot.getSource().equals(objPrevious) && ParametersDefault.isValidAnnotForScore(annot)) {
							scoreWay = scoreWay * annotationScoreSystem.getScore((Annotation) obj);
						}
					}
					objPrevious = obj;
				}
				ret += scoreWay;
			}
		}
		return ret;
	}

	public AnnotationScoreSystem getAnnotationScoreSystem() {
		return annotationScoreSystem;
	}

	public void setAnnotationScoreSystem(AnnotationScoreSystem annotationScoreSystem) {
		if (annotationScoreSystem == null) {
			annotationScoreSystem = new SimpleAnnotationScoreSystem();
		}
		this.annotationScoreSystem = annotationScoreSystem;
	}

}
