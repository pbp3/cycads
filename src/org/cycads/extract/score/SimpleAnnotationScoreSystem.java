/*
 * Created on 22/04/2009
 */
package org.cycads.extract.score;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public class SimpleAnnotationScoreSystem implements AnnotationScoreSystem
{

	List<Pattern>						patterns		= new ArrayList<Pattern>();
	List<TransformScore>				transformScores	= new ArrayList<TransformScore>();

	Hashtable<String, TransformScore>	methods			= new Hashtable<String, TransformScore>();

	public TransformScore getTransformScore(AnnotationMethod method) {
		TransformScore ret = methods.get(method.getName());
		if (ret == null) {
			for (int i = 0; i < patterns.size() && ret == null; i++) {
				if (patterns.get(i).matcher(method.getName()).matches()) {
					ret = transformScores.get(i);
				}
			}
			if (ret == null) {
				ret = new SimpleTransformScore();
			}
			methods.put(method.getName(), ret);
		}
		return ret;
	}

	public void addTransformScore(Pattern pattern, TransformScore transformScore) {
		patterns.add(pattern);
		transformScores.add(transformScore);
	}

	@Override
	public double getScore(Annotation annotation) {
		String score = annotation.getScore();
		//		if (score == null) {
		//			score = ParametersDefault.getAnnotationScoreDefault();
		//		}
		TransformScore transformScore = getTransformScore(annotation.getAnnotationMethod());
		return transformScore.getScoreDbl(score);
	}

}
