/*
 * Created on 16/12/2008
 */
package org.cycads.loaders.gff3;

import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.ParametersDefault;

public class GFF3Loader implements GFF3DocumentHandler
{
	String		seqDatabase;
	int			seqVersionDefault;
	Organism	organism;
	Pattern		exonPattern	= Pattern.compile(ParametersDefault.gff3ExonTagExpression());
	Pattern		genePattern	= Pattern.compile(ParametersDefault.gff3GeneTagExpression());
	Pattern		mrnaPattern	= Pattern.compile(ParametersDefault.gff3MRNATagExpression());
	Pattern		cdsPattern	= Pattern.compile(ParametersDefault.gff3CDSTagExpression());

	@Override
	public void commentLine(String comment) {
		// Do nothing
	}

	@Override
	public void endDocument() {
		// Do nothing
	}

	@Override
	public void recordLine(GFF3Record record) throws InvalidSequence {
		Sequence seq;
		String seqAccession = record.getSequenceID();
		if (ParametersDefault.getNameSpaceDefault().equals(seqDatabase)) {
			//sequence accession in the database
			seq = organism.getOrCreateSequence(seqAccession, seqVersionDefault);
		}
		else {
			//sequence accession is external
			Collection<DBLink< ? , ? extends Sequence, ? , ? >> dbLinks = organism.getDBLinksFromSequence(
				seqDatabase, seqAccession);
			if (dbLinks.size() != 1) {
				throw new InvalidSequence(seqDatabase, seqAccession);
			}
			else {
				// get the first
				seq = dbLinks.iterator().next().getSource();
			}
		}
		Location loc;
		if (record.getStrand()<0)
		{
			loc= seq.createLocation(record.getEnd(),record.getStart(), null);
		}
		else
		{
			loc = seq.createLocation(record.getStart(), record.getEnd(), null);
		}
		String type = record.getType();
		String method=record.getSource();
		if (genePattern.matcher(type).matches())
		{
			Gene gene = loc.createGene(method);
		}
		loc.


		// TODO Auto-generated method stub

	}

	@Override
	public void startDocument(String locator) {
		// Do nothing
	}

}
