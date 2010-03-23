/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.annotation.Annotation;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToScore extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Annotation)) {
			throw new GetterExpressionException("Object is not an annotation. Object:" + obj);
		}
		return ((Annotation) obj).getScore();
	}

}
