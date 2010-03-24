/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToMethod extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof AnnotationMethod)) {
			throw new GetterExpressionException("Object is not an annotation method. Object:" + obj);
		}
		return ((AnnotationMethod) obj).getName();
	}

}
