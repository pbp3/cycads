/*
 * Created on 31/10/2008
 */
package org.cycads.entities.sequence;

import org.cycads.entities.annotation.Feature;

public interface FeatureFilter
{
	public boolean accept(Feature feature);

}
