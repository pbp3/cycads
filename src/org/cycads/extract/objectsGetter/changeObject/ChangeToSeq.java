/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToSeq extends ChangeToManyObjects
{
	// LOC "SE"
	@Override
	public Collection<Object> executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Subsequence) {
			ArrayList<Object> ret = new ArrayList<Object>(1);
			ret.add(((Subsequence) obj).getSequence());
			return ret;
		}
		else if (obj instanceof Organism) {
			return ((Organism) obj).getSequences();
		}
		else {
			throw new GetterExpressionException("Object is neither an organism nor a subsequence. Object:" + obj);
		}
	}
}
