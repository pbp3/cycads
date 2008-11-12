/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public class FeatureType
{
	Collection<OtherFeature>	features;
	String						name;

	public String getName()
	{
		return name;
	}

	public Collection<OtherFeature> getFeatures(Sequence sequence)
	{
		return features;
	}
}
