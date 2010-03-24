/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.annotation.Annotation;
import org.cycads.extract.general.GetterExpressionException;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;

//LOC "AT"
public class ChangeToAnnotationsAsTarget extends ChangeToManyObjects
{
	// not implemented
	@Override
	public Collection<Object> executeMethod(Object obj) throws GetterExpressionException {
		throw new GetterExpressionException("Not implemented. Object:" + obj);
		/* not implemented for target entity
		if (!(obj instanceof BasicEntity)) {
			throw new GetterExpressionException("Object is not an entity. Object:" + obj);
		}
		return (Collection<Object>) ((BasicEntity) obj).getAnnotations(null, null, null);
		*/
	}

}
