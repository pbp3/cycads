/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface FeatureCollection
{
	public void addFeature(Feature feature);

	public Feature getFeature(AnnotationMethod method, String type);

	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

}
