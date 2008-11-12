/*
 * Created on 29/10/2008
 */
package org.cycads.entities.biojava1;

import java.util.Collection;


public interface Sequence
{

	public Collection<SequenceFeature> getFeatures(FeatureFilter featureFilter);

}
