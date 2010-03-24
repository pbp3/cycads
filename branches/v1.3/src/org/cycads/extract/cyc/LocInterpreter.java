/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.score.AnnotationScoreSystem;

public interface LocInterpreter
{
	public List<StringAndPath> getCycValues(Annotation< ? extends Subsequence, ? > annot, List<String> locs);

	public String getFirstString(Annotation< ? extends Subsequence, ? > annot, List<String> locs);

	public Collection<String> getStrings(Annotation< ? extends Subsequence, ? > annot, List<String> locs);

	public Collection<CycDbxrefAnnotationPaths> getCycDbxrefPathAnnots(Annotation< ? extends Subsequence, ? > annot,
			List<String> locs, AnnotationScoreSystem scoreSystems);

}
