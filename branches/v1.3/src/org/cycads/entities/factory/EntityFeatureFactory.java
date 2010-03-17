/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import java.util.Collection;

import org.cycads.entities.Feature;

public interface EntityFeatureFactory<F extends Feature>
{
	public F getFeature(String name);

	public Collection<F> getFeatures();

}
