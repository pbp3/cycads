/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.ArrayList;
import java.util.List;

import org.cycads.extract.general.GetterExpressionException;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;

public abstract class ChangeToOneObject extends ObjectsGetterChangeObject
{

	@Override
	public List<Object> getObjects(Object obj) throws GetterExpressionException {
		ArrayList<Object> ret = new ArrayList<Object>(1);
		Object retO = executeMethod(obj);
		if (retO != null) {
			ret.add(retO);
		}
		return ret;
	}

	public abstract Object executeMethod(Object obj) throws GetterExpressionException;

}
