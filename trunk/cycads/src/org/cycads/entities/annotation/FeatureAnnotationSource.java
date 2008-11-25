/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.sequence.feature.FeatureFilter;

public interface FeatureAnnotationSource extends AnnotationSource
{
	public Collection<FeatureAnnotation> getFeatures();

	public Collection<FeatureAnnotation> getFeatures(FeatureFilter featureFilter);

	public FeatureAnnotation addFeature(AnnotationMethod method, String type);

	public CDSAnnotation addCDS(AnnotationMethod method);

	public RNAAnnotation addRNA(AnnotationMethod method, String type);

	public GeneAnnotation addGene(AnnotationMethod method);
}
