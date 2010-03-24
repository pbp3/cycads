/*
 * Created on 24/03/2010
 */
package org.cycads.extract.score;

import java.util.ArrayList;
import java.util.List;

import org.cycads.general.TransformStringToDouble;

public class SimpleTransformScore implements TransformScore
{
	List<TransformDouble>	transforms;
	TransformStringToDouble	stringToDouble;

	public SimpleTransformScore(List<TransformDouble> transforms, TransformStringToDouble stringToDouble) {
		setTransforms(transforms);
		setStringToDouble(stringToDouble);
	}

	public SimpleTransformScore(List<TransformDouble> transforms) {
		this(transforms, new TransformStringToDouble());
	}

	@Override
	public double getScoreDbl(String scoreStr) {
		double score = stringToDouble.getScoreDouble(scoreStr);
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
