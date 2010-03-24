/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;


import org.cycads.entities.note.Note;
import org.cycads.extract.general.GetterExpressionException;

// LOC "NT"
public class ChangeToNoteType extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Note)) {
			throw new GetterExpressionException("Object is not a note. Object:" + obj);
		}
		return ((Note) obj).getType().getName();
	}

}
