/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;


public interface ObjectGetter {

	public ObjectPathList getObjects(Object obj)
			throws GetterExpressionException;

}
