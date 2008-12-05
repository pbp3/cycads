/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.feature;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureSource
{
	public Feature createFeature(AnnotationMethod method, String type);

	public CDS createCDS(AnnotationMethod method);

	public RNA createRNA(AnnotationMethod method, String type);

	public Gene createGene(AnnotationMethod method);
}
