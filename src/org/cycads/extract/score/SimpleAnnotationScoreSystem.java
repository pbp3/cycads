/*
 * Created on 24/03/2010
 */
package org.cycads.extract.score;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.general.TransformStringToDouble;

public class SimpleAnnotationScoreSystem implements AnnotationScoreSystem
{
	List<TransformDouble>	transforms;
	TransformStringToDouble	stringToDouble;

	public SimpleAnnotationScoreSystem(List<TransformDouble> transforms, TransformStringToDouble stringToDouble) {
		setTransforms(transforms);
		setStringToDouble(stringToDouble);
	}

	public SimpleAnnotationScoreSystem(List<TransformDouble> transforms) {
		this(transforms, new TransformStringToDouble());
	}

	@Override
	public double getScore(Annotation annotation) {
		double score = stringToDouble.getScoreDouble(annotation.getScore());
		for (TransformDouble transformDouble : transforms) {
			score = transformDouble.transform(score);
		}
		return score;
	}

	public List<TransformDouble> getTransforms() {
		return transforms;
	}

	public void setTransforms(List<TransformDouble> transforms) {
		if (transforms == null) {
			transforms = new ArrayList<TransformDouble>();
		}
		this.transforms = transforms;
	}

	public TransformStringToDouble getStringToDouble() {
		return stringToDouble;
	}

	public void setStringToDouble(TransformStringToDouble stringToDouble) {
		if (stringToDouble == null) {
			stringToDouble = new TransformStringToDouble();
		}
		this.stringToDouble = stringToDouble;
	}

}
