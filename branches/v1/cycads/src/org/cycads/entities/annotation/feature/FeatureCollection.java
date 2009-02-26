/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureCollection<F extends Feature< ? , ? , ? , ? >, S extends FeatureSource< ? , ? , ? , ? , ? >, M extends AnnotationMethod, C extends CDS< ? , ? , ? , ? , ? >, R extends RNA< ? , ? , ? , ? , ? , ? >, G extends Gene< ? , ? , ? , ? , ? >>
{
	public void addFeature(F feature);

	public Collection<F> getFeatures(FeatureFilter<F> featureFilter);

	public Collection<F> getFeatures(M method, String type);

	public Collection<F> getFeatures(String method, String type);

	public Collection<F> getFeatures(String type);

	public Collection<F> getFeatures(S source, M method, String type);

	public Collection<F> getFeatures(S source, String method, String type);

	public G getGene(M method);

	public G getGene(String method);

	public R getMRNA(M method);

	public R getMRNA(String method);

	public C getCDS(M method);

	public C getCDS(String method);

}
