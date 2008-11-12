/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface IFeatureType
{

	public String getName();

	public Collection<Feature> getFeatures(ISequence sequence);

	public Collection<Feature> getFeatures();

}