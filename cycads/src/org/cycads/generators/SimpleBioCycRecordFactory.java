/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.SequenceFeature;
import org.cycads.ui.generators.ECLinkCreator;

public class SimpleBioCycRecordFactory implements BioCycRecordFactory
{
	ECLinkCreator		ecCreator;
	FunctionCreator		functionCreator;
	DBLinkCreator		dbLinkCreator;
	BioCycIDGenerator	bioCycIdGenerator;

	public BioCycRecord createRecord(SequenceFeature feature) {
		return null;
	}

}
