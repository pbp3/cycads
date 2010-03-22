/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

public interface ObjectsGetter
{
	public List<Object> getObjects(Object obj) throws GetterExpressionException;
}
