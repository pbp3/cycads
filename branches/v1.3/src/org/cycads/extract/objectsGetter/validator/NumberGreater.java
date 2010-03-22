/*
 * Created on 19/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

public class NumberGreater extends CompNumber
{
	public NumberGreater(Number number) {
		super(number);
	}

	@Override
	public boolean compNumber(Number number) {
		return this.number.doubleValue() > number.doubleValue();
	}

}
