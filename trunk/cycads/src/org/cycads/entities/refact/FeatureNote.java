/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import org.cycads.entities.Feature;
import org.cycads.entities.IFeatureNote;

public class FeatureNote extends Note implements IFeatureNote
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
