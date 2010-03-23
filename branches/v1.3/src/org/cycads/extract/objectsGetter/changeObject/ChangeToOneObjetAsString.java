/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.ArrayList;
import java.util.List;

import org.cycads.extract.general.GetterExpressionException;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;

public class ChangeToOneObjetAsString extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		
		return obj.toString();
	}

}
