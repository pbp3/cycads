/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.Association;


import org.cycads.extract.general.GetterExpressionException;

// LOC "AY"
public class ChangeToAnnotationTypes extends ChangeToManyObjects
{
	@Override
	public Collection<Object> executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Annotation)) {
			throw new GetterExpressionException("Object is not an annotation. Object:" + obj);
		}
		return ((Association) obj).getTypes();
	}

}
