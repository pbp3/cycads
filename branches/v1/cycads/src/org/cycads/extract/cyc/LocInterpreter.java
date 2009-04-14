/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.SubseqAnnotation;

public interface LocInterpreter {
	public List<CycValue> getCycValues(SubseqAnnotation annot, List<String> locs);

	public String getFirstString(SubseqAnnotation annot, List<String> locs);

	public Collection<String> getStrings(SubseqAnnotation annot, List<String> locs);
}
