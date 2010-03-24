/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToAnnotationsAsSource extends ChangeToManyObjects
{

	@Override
	public Collection<Object> executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof BasicEntity)) {
			throw new GetterExpressionException("Object is not an entity. Object:" + obj);
		}
		return (Collection<Object>) ((BasicEntity) obj).getAnnotations(null, null, null);
	}

}
