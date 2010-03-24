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
		this.annotationScoreSystem = annotationScoreSystem;
	}

	@Override
	public double getScore(AnnotationWayList annotationWayList) {
		//		if (annotationWayList.isEmpty()) {
		//			//			return ParametersDefault.getAnnotationWayListScoreDefault();
		//			return 0;
		//		}
		double ret = 0;
		double scoreWay;
		for (AnnotationWay annotationWay : annotationWayList) {
			if (!annotationWay.isEmpty()) {
				scoreWay = 1;
				for (Object obj : annotationWay) {
					if (obj instanceof Annotation && ParametersDefault.isValidAnnotForScore(((Annotation) obj))) {
						scoreWay = scoreWay * annotationScoreSystem.getScore((Annotation) obj);
					}
				}
				ret += scoreWay;
			}
		}
		return ret;
	}

}
