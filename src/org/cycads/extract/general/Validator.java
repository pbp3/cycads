/*
 * Created on 22/03/2010
 */
package org.cycads.extract.general;

public interface Validator
{

	public boolean isValid(Object obj) throws GetterExpressionException;
}
