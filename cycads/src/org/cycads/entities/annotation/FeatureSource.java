/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.sequence.FeatureFilter;
import org.cycads.entities.sequence.Location;

public interface FeatureSource extends AnnotationSource<Feature>
{
	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public Feature addFeature(AnnotationMethod method, Location location, String type);

	public CDS addCDS(AnnotationMethod method, Location location);

	public RNA addRNA(AnnotationMethod method, Location location, String type);

	public Gene addGene(AnnotationMethod method, Location location);
}
