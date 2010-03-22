/*
 * Created on 19/03/2010
 */
package org.cycads.extract.objectsGetter;

import java.util.List;

import org.cycads.extract.general.GetterExpressionException;

public interface ObjectsGetter
{
	public List<Object> getObjects(Object obj) throws GetterExpressionException;
}
