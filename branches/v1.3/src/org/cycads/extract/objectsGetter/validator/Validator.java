/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

import org.cycads.extract.general.GetterExpressionException;

public interface Validator
{

	public boolean isValid(Object obj) throws GetterExpressionException;
}
