/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.Feature;
import org.cycads.entities.FeatureType;
import org.cycads.entities.Sequence;

public class FeatureType implements FeatureType
{
	Collection<Feature>	features;
	String					name;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeatureType#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeatureType#getFeatures(org.cycads.entities.refact.Sequence)
	 */
	public Collection<Feature> getFeatures(Sequence sequence) {
		return features;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeatureType#getFeatures()
	 */
	public Collection<Feature> getFeatures() {
		return features;
	}
}
