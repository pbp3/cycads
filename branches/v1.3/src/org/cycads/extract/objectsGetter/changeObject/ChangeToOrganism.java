/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.sequence.Organism;
import org.cycads.extract.general.GetterExpressionException;

public class ChangeToOrganism extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (!(obj instanceof Organism)) {
			throw new GetterExpressionException("Object is not an organism. Object:" + obj);
		}
		return ((Organism) obj).getName();
	}

}
