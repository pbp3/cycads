/*
 * Created on 19/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

public class NumberGeq extends CompNumber
{
	public NumberGeq(Number number) {
		super(number);
	}

	@Override
	public boolean compNumber(Number number) {
		return this.number.doubleValue() >= number.doubleValue();
	}

}
