/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation;

import java.util.regex.Pattern;

public class FeatureFilterByType<F extends AnnotFeature< ? , ? , ? >> implements AnnotationFilter<F>
{
	Pattern	pattern;

	public FeatureFilterByType(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean acceptType(String featureType) {
		return pattern.matcher(featureType).matches();
	}

	public boolean accept(F feature) {
		return acceptType(feature.getType());
	}

}
