package org.cycads.entities.refact;

import org.cycads.entities.Feature;
import org.cycads.entities.FeatureToDBAnnotation;

public class FeatureToDBAnnotation extends DBAnnotation implements FeatureToDBAnnotation
{

	private Feature	feature;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeatureToDBAnnotation#getFeature()
	 */
	public Feature getFeature()
	{
		return feature;
	}

}