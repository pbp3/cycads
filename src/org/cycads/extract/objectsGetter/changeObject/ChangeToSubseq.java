/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.Collection;

import org.cycads.entities.sequence.Sequence;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToSubseq extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Sequence)) {
			throw new GetterExpressionException("Object is not a sequence. Object:" + obj);
		}
		return ((Sequence) obj).getSubsequences();
	}

}
