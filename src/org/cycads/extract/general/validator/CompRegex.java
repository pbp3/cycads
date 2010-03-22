/*
 * Created on 22/03/2010
 */
package org.cycads.extract.general.validator;

import java.util.regex.Pattern;


public class CompRegex implements Validator
{

	Pattern	pattern;

	public CompRegex(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public boolean isValid(Object obj) {
		return pattern.matcher(obj.toString()).matches();
	}

}
