/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.IFeatureAnnotationMethod;

public class FeatureAnnotationMethod extends AnnotationMethod implements IFeatureAnnotationMethod
{
	private Collection<Feature>	features;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IFeatureAnnotationMethod#getFeatures()
	 */
	public Collection<Feature> getFeatures()
	{
		return features;
	}
}
