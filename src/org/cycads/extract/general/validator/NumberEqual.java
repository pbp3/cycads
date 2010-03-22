/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general.validator;


public class NumberEqual extends CompNumber
{
	public NumberEqual(Number number) {
		super(number);
	}

	@Override
	public boolean compNumber(Number number) {
		return this.number.equals(number);
	}

}
