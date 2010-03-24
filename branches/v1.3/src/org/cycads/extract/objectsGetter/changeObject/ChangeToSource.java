/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.annotation.Association;
import org.cycads.extract.general.GetterExpressionException;

// LOC "SO"
public class ChangeToSource extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Association)) {
			throw new GetterExpressionException("Object is not an association. Object:" + obj);
		}
		return ((Association) obj).getSource();
	}

}
