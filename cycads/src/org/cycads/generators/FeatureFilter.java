/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.SequenceFeature;

public interface FeatureFilter
{
	public boolean isValid(SequenceFeature feature);

}
