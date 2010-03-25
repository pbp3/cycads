/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;


// LOC "ST"
public class ChangeToStringReplaced extends ChangeToOneObject
{
	String	regex;
	String	replacement;

	public ChangeToStringReplaced(String regex, String replacement) {
		this.regex = regex;
		this.replacement = replacement;
	}

	@Override
	public Object executeMethod(Object obj) {
		return obj.toString().replaceAll(regex, replacement);
	}

}
