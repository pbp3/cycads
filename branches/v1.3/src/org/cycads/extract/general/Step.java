/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

public interface Step
{

	List<Object> execute(Object obj) throws GetterExpressionException;

}
