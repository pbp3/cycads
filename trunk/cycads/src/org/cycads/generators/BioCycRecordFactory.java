/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava1.SequenceFeature;

public interface BioCycRecordFactory
{

	public BioCycRecord createRecord(SequenceFeature feature);

}
