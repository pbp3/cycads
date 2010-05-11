/*
 * Created on 24/03/2010
 */
package org.cycads.extract.score;

import java.util.ArrayList;
import java.util.List;

import org.cycads.general.ParametersDefault;

//first transform a string to double and after transform the double, can be in other scale

public class SimpleTransformScore implements TransformScore
{
	List<TransformDouble>	transforms;
	TransformStringToDouble	stringToDouble;
	String					scoreDefault	= ParametersDefault.getAnnotationScoreDefault();

	public SimpleTransformScore(List<TransformDouble> transforms, TransformStringToDouble stringToDouble) {
		setTransforms(transforms);
		setStringToDouble(stringToDouble);
	}

	public SimpleTransformScore(List<TransformDouble> transforms) {
		this(transforms, new TransformStringToDouble());
	}

	public SimpleTransformScore() {
		this(null);
	}

	@Override
	public double getScoreDbl(String scoreStr) {
		if ((scoreStr == null) || (scoreStr.trim().length() == 0)) {
			scoreStr = getScoreDefault();
		}
		double score = stringToDouble.getDouble(scoreStr);
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

	public void addTransform(TransformDouble transform) {
		getTransforms().add(transform);
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

	public String getScoreDefault() {
		return scoreDefault;
	}

	public void setScoreDefault(String scoreDefault) {
		this.scoreDefault = scoreDefault;
	}

}
