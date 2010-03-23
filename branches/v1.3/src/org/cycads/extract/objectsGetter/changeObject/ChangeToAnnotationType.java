/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.annotation.*;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToAnnotationType extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Annotation)) {
			throw new GetterExpressionException("Object is not an annotation. Object:" + obj);
		}
		/* for an annotation type, is there EntityType or AssociationType ??
		return ((Annotation) obj).getEntityType();
		*/
	}

}
