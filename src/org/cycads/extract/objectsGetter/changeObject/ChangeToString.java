/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.Feature;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.Function;
import org.cycads.extract.general.GetterExpressionException;

// LOC "ST"
public class ChangeToString extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Object) {
			return obj.toString();
		}
		else {
			throw new GetterExpressionException("Not an object. Object:" + obj);
		}
		
	}

}
