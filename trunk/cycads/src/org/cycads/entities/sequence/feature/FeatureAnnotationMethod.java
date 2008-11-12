/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureAnnotationMethod extends AnnotationMethod
{

	public Collection<Feature> getFeatures();

}