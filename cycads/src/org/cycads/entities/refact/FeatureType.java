/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public class FeatureType extends Term
{
	Collection<OtherFeature>	features;

	public Collection<OtherFeature> getFeatures(Sequence sequence)
	{
		return features;
	}
}
