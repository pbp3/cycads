/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToSubSeqBegin extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Subsequence)) {
			throw new GetterExpressionException("Object is not a subsequence. Object:" + obj);
		}
		return ((Subsequence) obj).getStart();
	}

}
