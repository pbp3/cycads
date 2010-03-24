/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.extract.general.GetterExpressionException;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;

public abstract class ChangeToManyObjects extends ObjectsGetterChangeObject
{

	@Override
	public List<Object> getObjects(Object obj) throws GetterExpressionException {
		Collection<Object> retMethod = executeMethod(obj);
		if (retMethod instanceof List) {
			return (List<Object>) retMethod;
		}
		else {
			ArrayList<Object> ret = new ArrayList<Object>(retMethod);
			return ret;
		}
	}

	public abstract Collection<Object> executeMethod(Object obj) throws GetterExpressionException;

}
