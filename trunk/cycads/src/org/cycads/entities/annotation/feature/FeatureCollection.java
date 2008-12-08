/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureCollection<F extends Feature< ? , ? , ? >, S extends FeatureSource< ? , ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public void addFeature(F feature);

	public Collection<F> getFeatures(FeatureFilter<F> featureFilter);

	public Collection<F> getFeatures(M method, String type);

	public F getFeature(M method, String type, S source);

}
