/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;


public interface RNA<RNA_TYPE extends RNA< ? , ? , ? >, SS extends AnnotFeatureSource< ? , ? , ? , ? , ? >, M extends AnnotationMethod>
		extends AnnotFeature<RNA_TYPE, SS, M> {
	// public Collection<G> getGenesContains();

	public SS getParent();

	public void setParent(SS geneSubseq);

	public boolean isMRNA();

}
