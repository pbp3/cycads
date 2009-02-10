/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.sequence.feature.Feature;
import org.cycads.entities.sequence.feature.FeatureNote;

public class FeatureNote extends Note implements FeatureNote
{
	private Feature	feature;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeatureNote#getFeature()
	 */
	public Feature getFeature()
	{
		return feature;
	}

}
