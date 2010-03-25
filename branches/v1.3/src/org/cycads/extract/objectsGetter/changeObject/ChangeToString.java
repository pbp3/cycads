/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;


// LOC "ST"
public class ChangeToString extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) {
		return obj.toString();
	}

}
