/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.extract.general.GetterExpressionException;

// LOC "NO"
public class ChangeToNotes extends ChangeToManyObjects
{

	@Override
	public Collection< ? extends Object> executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof BasicEntity)) {
			throw new GetterExpressionException("Object is not an entity. Object:" + obj);
		}
		return ((BasicEntity) obj).getNotes();

	}

}
