/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.synonym.Dbxref;
import org.cycads.extract.general.GetterExpressionException;

// LOC "AC"
public class ChangeToAccession extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Dbxref)) {
			throw new GetterExpressionException("Object is not a dbxref. Object:" + obj);
		}
		return ((Dbxref) obj).getAccession();
	}

}
