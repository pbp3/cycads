/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface RNAAnnotation extends FeatureAnnotation
{
	public Collection<GeneAnnotation> getGenesContains();

	public GeneAnnotation getGene();
}
