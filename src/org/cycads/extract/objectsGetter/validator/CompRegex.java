/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

import java.util.regex.Pattern;

public class CompRegex implements Validator {

	Pattern pattern;

	public CompRegex(Pattern pattern) {
		this.pattern = pattern;
	}

	public CompRegex(String patternStr) {
		this.pattern = Pattern.compile(patternStr);
	}

	@Override
	public boolean isValid(Object obj) {
		return pattern.matcher(obj.toString()).matches();
	}

}
