/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.extract.general.GetterExpressionException;

public class CompAnnotWaysGetter implements Validator
{

	AnnotationWaysGetter	annotWaysGetter;

	public CompAnnotWaysGetter(AnnotationWaysGetter annotWaysGetter) {
		this.annotWaysGetter = annotWaysGetter;
	}

	@Override
	public boolean isValid(Object obj) throws GetterExpressionException {
		return !annotWaysGetter.getAnnotationWays(obj).isEmpty();
	}

}
