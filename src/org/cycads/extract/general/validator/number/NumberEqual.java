/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general.validator.number;

public class NumberEqual implements NumberValidator
{
	double	number;

	@Override
	public boolean isValid(double number) {
		return number == this.number;
	}

}
