/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.BasicEntity;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToEntityNotes extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof BasicEntity)) {
			throw new GetterExpressionException("Object is not an entity. Object:" + obj);
		}
		return ((BasicEntity) obj).getNotes();
	}

}
