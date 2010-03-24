/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.sequence.Sequence;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToEnd extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Subsequence) {
			return ((Subsequence) obj).getEnd();
		}
		else {
			throw new GetterExpressionException("Object is not a subsequence. Object:" + obj);
		}	
	}

}
