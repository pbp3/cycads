/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

public class FeatureFilterByType implements FeatureFilter
{
	String	regex;

	public FeatureFilterByType(String regex) {
		this.regex = regex;
	}

}
