/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.extract.general.GetterExpressionException;

// LOC "ST"
public class ChangeToString extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		return obj.toString();
	}

}
