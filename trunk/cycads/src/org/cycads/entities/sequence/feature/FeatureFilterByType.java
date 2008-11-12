/*
 * Created on 31/10/2008
 */
package org.cycads.entities.sequence.feature;

import java.util.regex.Pattern;

public class FeatureFilterByType implements FeatureFilter
{
	Pattern	pattern;

	public FeatureFilterByType(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean accept(FeatureType featureType) {
		return pattern.matcher(featureType.getName()).matches();
	}

	public boolean accept(Feature feature) {
		return accept(feature.getFeatureType());
	}

}
