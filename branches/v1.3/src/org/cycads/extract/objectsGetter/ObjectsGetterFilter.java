/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter;

import java.util.ArrayList;
import java.util.List;

import org.cycads.extract.general.GetterExpressionException;
import org.cycads.extract.general.validator.Validator;

public class ObjectsGetterFilter implements ObjectsGetter
{

	Validator	validator;

	public ObjectsGetterFilter(Validator validator) {
		this.validator = validator;
	}

	@Override
	public List<Object> getObjects(Object obj) throws GetterExpressionException {
		List<Object> ret = new ArrayList<Object>();
		if (validator.isValid(obj)) {
			ret.add(obj);
		}
		return ret;
	}

}
