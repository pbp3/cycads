/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.BasicEntity;
import org.cycads.extract.general.GetterExpressionException;

// LOC "TY"
public class ChangeToType extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof BasicEntity)) {
			throw new GetterExpressionException("Object is not an entity. Object:" + obj);
		}
		return ((BasicEntity) obj).getEntityTypeName();
	}

}
