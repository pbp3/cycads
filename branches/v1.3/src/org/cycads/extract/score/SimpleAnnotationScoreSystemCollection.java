/*
 * Created on 22/04/2009
 */
package org.cycads.extract.score;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.AnnotationMethod;

public class SimpleAnnotationScoreSystemCollection implements AnnotationScoreSystemCollection
{

	List<Pattern>								patterns				= new ArrayList<Pattern>();
	List<AnnotationScoreSystem>					annotationScoreSystems	= new ArrayList<AnnotationScoreSystem>();

	Hashtable<String, AnnotationScoreSystem>	methods					= new Hashtable<String, AnnotationScoreSystem>();

	@Override
	public AnnotationScoreSystem getScoreSystem(AnnotationMethod method) {
		AnnotationScoreSystem ret = methods.get(method.getName());
		if (ret == null) {
			for (int i = 0; i < patterns.size() && ret == null; i++) {
				if (patterns.get(i).matcher(method.getName()).matches()) {
					ret = annotationScoreSystems.get(i);
				}
			}
			if (ret == null) {
				ret = new SimpleAnnotationScoreSystem(null);
			}
			methods.put(method.getName(), ret);
		}
		return ret;
	}

	public void addScoreSystem(Pattern pattern, AnnotationScoreSystem annotationScoreSystem) {
		patterns.add(pattern);
		annotationScoreSystems.add(annotationScoreSystem);
	}

}
