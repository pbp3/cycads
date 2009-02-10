/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface AnnotFeatureSource<F extends AnnotFeature< ? , ? , ? >, C extends CDS< ? , ? , ? >, R extends RNA< ? , ? , ? >, G extends Gene< ? , ? , ? >, M extends AnnotationMethod> {
	public F getOrCreateFeature(M method, String type);

	public C getOrCreateCDS(M method);

	public R getOrCreateRNA(M method, String type);

	public R getOrCreateMRNA(M method);

	public G getOrCreateGene(M method);

	public Collection<F> getFeatures(AnnotationFilter<F> featureFilter);

	public Collection<F> getFeatures(String type);

	public Collection<F> getFeatures(M method);

	public Collection<F> getFeatures();

	public Collection<G> getGenes();

	public Collection<R> getMRNAs();

	public Collection<R> getRNAs();

	public Collection<C> getCDSs();

	public F getFeature(M method, String type);

	public G getGene(M method);

	public R getMRNA(M method);

	public R getRNA(M method, String type);

	public C getCDS(M method);

	public M getMethodInstance(String method);

}
