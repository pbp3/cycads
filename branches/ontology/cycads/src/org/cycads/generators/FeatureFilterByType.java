/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import java.util.regex.Pattern;

import org.cycads.entities.biojava.SequenceFeature;

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