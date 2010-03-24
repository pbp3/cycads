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

public class ChangeToAnnotationsAsSource extends ObjectsGetterChangeObject
{

	@Override
	public List<Object> getObjects(Object obj) throws GetterExpressionException {
		if (!(obj instanceof BasicEntity)) {
			throw new GetterExpressionException("Object is not an entity. Object:" + obj);
		}
		Collection< ? extends Annotation< ? , BasicEntity>> retMethod = ((BasicEntity) obj).getAnnotations(null, null,
			null);
		if (retMethod instanceof List) {
			return (List<Object>) retMethod;
		}
		else {
			ArrayList<Object> ret = new ArrayList<Object>(retMethod);
			return ret;
		}
	}

}
