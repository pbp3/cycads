/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.Feature;

public interface EntityFeatureFactory<F extends Feature>
{
	public F getFeature(String name);

}
