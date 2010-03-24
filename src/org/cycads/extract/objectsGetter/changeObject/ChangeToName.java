/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.Feature;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.Function;
import org.cycads.extract.general.GetterExpressionException;

// LOC "NA"
public class ChangeToName extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Feature) {
			return ((Feature) obj).getName();
		}
		else if (obj instanceof Function) {
			return ((Function) obj).getName();
		}
		else if (obj instanceof Organism) {
			return ((Organism) obj).getName();
		}
		else {
			throw new GetterExpressionException("Object is neither a feature nor a function nor an organism. Object:" + obj);
		}
		
	}

}
