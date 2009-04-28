/*
 * Created on 22/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.AnnotationMethod;

public class SimpleScoreSystemCollection implements ScoreSystemCollection {

	List<Pattern>					patterns		= new ArrayList<Pattern>();
	List<ScoreSystem>				scoreSystems	= new ArrayList<ScoreSystem>();

	Hashtable<String, ScoreSystem>	methods			= new Hashtable<String, ScoreSystem>();

	@Override
	public ScoreSystem getScoreSystem(AnnotationMethod method) {
		ScoreSystem ret = methods.get(method.getName());
		if (ret == null) {
			for (int i = 0; i < patterns.size() && ret == null; i++) {
				if (patterns.get(i).matcher(method.getName()).matches()) {
					ret = scoreSystems.get(i);
				}
			}
			if (ret == null) {
				ret = new FixAndFileScoreSystem(method.getWeight(), null);
			}
			else if (ret instanceof FixScoreSystem) {
				method.setWeight(((FixScoreSystem) ret).getFixValue());
			}
			methods.put(method.getName(), ret);
		}
		return ret;
	}

	public void addScoreSystem(Pattern pattern, ScoreSystem scoreSystem) {
		patterns.add(pattern);
		scoreSystems.add(scoreSystem);
	}

}
