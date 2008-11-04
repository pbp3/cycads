/*
 * Created on 31/10/2008
 */
package org.cycads.entities;

import java.util.regex.Pattern;

public class FeatureFilterByType implements FeatureFilter
{
	Pattern	pattern;

	public FeatureFilterByType(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean accept(String type) {
		return pattern.matcher(type).matches();
	}

	public boolean accept(SequenceFeature feature) {
		return accept(feature.getType());
	}

}
