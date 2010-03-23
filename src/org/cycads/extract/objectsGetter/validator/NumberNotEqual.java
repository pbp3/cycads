/*
 * Created on 19/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

public class NumberNotEqual extends CompNumber
{
	public NumberNotEqual(Number number) {
		super(number);
	}

	@Override
	public boolean compNumber(Number number) {
		return !this.number.equals(number);
	}

}
