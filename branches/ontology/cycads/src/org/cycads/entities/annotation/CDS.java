/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

public interface CDS<CDS_TYPE extends CDS< ? , ? , ? >, SS extends AnnotFeatureSource< ? , ? , ? , ? , ? >, M extends AnnotationMethod>
		extends AnnotFeature<CDS_TYPE, SS, M> {
	// public Collection<R> getRNAsContains();

	public SS getParent();

	public void setParent(SS rnaSubseq);

}
