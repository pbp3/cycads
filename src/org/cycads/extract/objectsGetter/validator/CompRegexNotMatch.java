/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.validator;

import java.util.regex.Pattern;

public class CompRegexNotMatch extends CompRegex
{

	public CompRegexNotMatch(Pattern pattern) {
		super(pattern);
	}

	public CompRegexNotMatch(String patternStr) {
		super(patternStr);
	}

	@Override
	public boolean isValid(Object obj) {
		return !pattern.matcher(obj.toString()).matches();
	}

}
