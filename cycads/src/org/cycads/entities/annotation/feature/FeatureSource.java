/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.feature;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureSource<F extends Feature< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public F createFeature(M method, String type);

}
