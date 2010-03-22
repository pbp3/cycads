/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general.validator;

import org.cycads.extract.general.GetterExpressionException;

public abstract class CompNumber implements Validator
{
	protected Number	number;

	public CompNumber(Number number) {
		this.number = number;
	}

	@Override
	public boolean isValid(Object obj) throws GetterExpressionException {
		Number numberToComp = getNumber(obj);
		if (numberToComp == null) {
			throw new GetterExpressionException("Object " + obj + "is not a number.");
		}
		return compNumber(numberToComp);
	}

	protected abstract boolean compNumber(Number numberToComp);

	private Number getNumber(Object obj) {
		if (obj instanceof Number) {
			return (Number) obj;
		}
		else {
			try {
				return Double.parseDouble(obj.toString());
			}
			catch (NumberFormatException e) {
				return null;
			}
		}
	}
}
