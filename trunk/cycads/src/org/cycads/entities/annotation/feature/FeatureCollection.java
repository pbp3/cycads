/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureCollection
{
	public void addFeature(Feature feature);

	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public Collection<Feature> getFeatures(AnnotationMethod method, String type);

	public Feature getFeature(AnnotationMethod method, String type, FeatureSource source);

}
