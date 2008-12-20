/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava.SequenceFeature;

public interface FeatureFilter
{
	public boolean accept(SequenceFeature feature);

}
