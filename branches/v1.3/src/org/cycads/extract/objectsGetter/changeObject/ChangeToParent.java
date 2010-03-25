/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.annotation.Annotation;
import org.cycads.extract.general.GetterExpressionException;

// LOC "PA"
public class ChangeToParent extends ChangeToManyObjects
{

	@Override
	public Collection<Object> executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Annotation) {
			return ((Annotation) obj).getParents();
		}
		else if (obj instanceof BasicEntity) {
			throw new GetterExpressionException("Not implemented. Object:" + obj);
		}
		else {
			throw new GetterExpressionException("Object is not a BasicEntity. Object:" + obj);
		}
	}

}
