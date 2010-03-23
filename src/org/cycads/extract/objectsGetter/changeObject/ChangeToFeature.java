/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.Feature;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToFeature extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Feature)) {
			throw new GetterExpressionException("Object is not a feature. Object:" + obj);
		}
		return ((Feature) obj).getName();
	}

}
