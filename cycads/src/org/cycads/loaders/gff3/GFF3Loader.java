/*
 * Created on 16/12/2008
 */
package org.cycads.loaders.gff3;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ExternalDatabaseBJ;
import org.cycads.entities.sequence.NCBIOrganismBJ;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class GFF3Loader implements GFF3DocumentHandler
{
	ExternalDatabaseBJ	seqDatabase;
	int					version;
	NCBIOrganismBJ		organism;

	@Override
	public void commentLine(String comment)
	{
		// Do nothing
	}

	@Override
	public void endDocument()
	{
		// Do nothing
	}

	@Override
	public void recordLine(GFF3Record record)
	{
		DBRecordBJ dbRecord = seqDatabase.getOrCreateDBRecord(record.getSequenceID());
		Collection<DBLink< ? , ? , ? , ? >> dbLinks = dbRecord.getDBLinksFromSequence(organism);
		Sequence seq;
		if (dbLinks.size() == 0)
		{
			// create sequence
			seq = organism
		}
		else
		{
			// get the first
		}
		String type = record.getType();

		// TODO Auto-generated method stub

	}

	@Override
	public void startDocument(String locator)
	{
		// Do nothing
	}

}
