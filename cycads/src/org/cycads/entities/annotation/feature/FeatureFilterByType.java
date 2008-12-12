/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.regex.Pattern;

public class FeatureFilterByType implements FeatureFilter<Feature< ? , ? , ? , ? >>
{
	Pattern	pattern;

	public FeatureFilterByType(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean acceptType(String featureType) {
		return pattern.matcher(featureType).matches();
	}

	public boolean accept(Feature< ? , ? , ? , ? > feature) {
		return acceptType(feature.getType());
	}

}
