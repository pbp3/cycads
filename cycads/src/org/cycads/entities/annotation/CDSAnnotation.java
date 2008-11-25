/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface CDSAnnotation extends FeatureAnnotation
{
	public Collection<RNAAnnotation> getRNAsContains();

	public RNAAnnotation getRNA();
}
