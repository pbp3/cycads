/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface AnnotFeatureSource<F extends AnnotFeature< ? , ? , ? >, C extends CDS< ? , ? , ? , ? >, R extends RNA< ? , ? , ? , ? , ? >, G extends Gene< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public F createFeature(M method, String type);

	public C createCDS(M method);

	public R createRNA(M method, String type);

	public R createMRNA(M method);

	public G createGene(M method);

	public Collection<F> getFeatures(AnnotationFilter<F> featureFilter);

	public Collection<F> getFeatures(M method, String type);

	public Collection<F> getFeatures(String type);

	public Collection<G> getGenes(M method);

	public Collection<R> getMRNAs(M method);

	public Collection<C> getCDSs(M method);

	public M getMethodInstance(String method);

}
