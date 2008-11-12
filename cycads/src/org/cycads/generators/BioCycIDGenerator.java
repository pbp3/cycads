/*
 * Created on 04/11/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava1.SequenceFeature;

public interface BioCycIDGenerator
{

	public String getOrCreate(SequenceFeature feature);

}
