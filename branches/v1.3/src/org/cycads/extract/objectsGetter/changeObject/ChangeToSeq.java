/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.sequence.Organism;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToSeq extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Subsequence) {
			return ((Subsequence) obj).getSequence();
			}
		else if (obj instanceof Organism) {
			return ((Organism) obj).getSequences();
		}
		else
		{
			throw new GetterExpressionException("Object is neither an organism nor a subsequence. Object:" + obj);
		}
	}

}
