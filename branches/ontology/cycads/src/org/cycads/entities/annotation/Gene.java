/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

public interface Gene<GENE_TYPE extends Gene< ? , ? , ? >, SS extends AnnotFeatureSource< ? , ? , ? , ? , ? >, M extends AnnotationMethod>
		extends AnnotFeature<GENE_TYPE, SS, M> {
}
