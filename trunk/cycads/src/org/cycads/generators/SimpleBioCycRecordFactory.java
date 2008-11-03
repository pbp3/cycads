/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import java.util.regex.Pattern;

import org.cycads.entities.SequenceFeature;
import org.cycads.general.Config;

public class SimpleBioCycRecordFactory implements BioCycRecordFactory
{
	ECLinkCreator		ecCreator;
	FunctionCreator		functionCreator;
	DBLinkCreator		dbLinkCreator;
	BioCycIDGenerator	bioCycIdGenerator;

	public BioCycRecord createRecord(SequenceFeature feature) {
		String type = Config.bioCycRecordType(feature.getType());
		if (type==null)
		{
			return null;
		}
		String bioCycID = bioCycIdGenerator.getOrCreate(feature);
		if (bioCycID == null) {
			return null;
		}
		BioCycRecord record = new SimpleBioCycRecord(bioCycID);
		String type = feature.getType();

		if (proteinPattern.matcher(type).matches()) {
			record.setType(BioCycRecord.PROTEIN_TYPE);
		}
		else 		if (tRNAPattern.matcher(type).matches())
		{

		}
		record.setName(feature.getNote(Config.bioCycRecordFactoryNameTag()))
		return record;
	}

	class TypeTransform
	{

	}
}
