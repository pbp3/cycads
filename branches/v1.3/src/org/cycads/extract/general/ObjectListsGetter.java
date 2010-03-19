/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

public interface ObjectListsGetter
{
	//	public List<List<Object>> getObjectLists(Collection<Object> objs, String loc);

	public List<List<Object>> getObjectLists(Object obj, List<Step> steps) throws GetterExpressionException;
}
