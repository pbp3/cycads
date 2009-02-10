/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava.DBLinkCreator;
import org.cycads.entities.biojava.ECLinkCreator;
import org.cycads.entities.biojava.FunctionCreator;
import org.cycads.entities.biojava.SequenceFeature;
import org.cycads.general.Config;

public class SimpleBioCycRecordFactory implements BioCycRecordFactory
{
	ECLinkCreator		ecCreator;
	FunctionCreator		functionCreator;
	DBLinkCreator		dbLinkCreator;
	BioCycIDGenerator	bioCycIdGenerator;

	public BioCycRecord createRecord(SequenceFeature feature) {
		String type = Config.bioCycRecordType(feature.getType());
		if (type == null) {
			return null;
		}
		String bioCycID = bioCycIdGenerator.getOrCreate(feature);
		if (bioCycID == null) {
			return null;
		}
		BioCycRecord record = new SimpleBioCycRecord(type, bioCycID);

		record.setName(feature.getNote(Config.sequenceFeatureNameTag()).getValue());
		record.setLocation(feature.getLocation());

		record.setProductId(feature.getNote(Config.sequenceFeatureTagProductId(feature.getType())).getValue());
		record.setDBLinks(dbLinkCreator.create(feature));
		record.setFunctions(functionCreator.create(feature));

		record.setECs();
		record.setComments();
		record.setSynonyms();

		return record;
	}

}
