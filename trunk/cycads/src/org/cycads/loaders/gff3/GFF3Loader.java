/*
 * Created on 16/12/2008
 */
package org.cycads.loaders.gff3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.ParametersDefault;

public class GFF3Loader implements GFF3DocumentHandler
{
	String										seqDatabase;
	int											seqVersionDefault;
	Organism									organism;
	Pattern										genePattern	= Pattern.compile(ParametersDefault.gff3GeneTagExpression());
	Hashtable<String, GFF3Record>				genes		= new Hashtable<String, GFF3Record>();
	Hashtable<String, GFF3Record>				mrnas		= new Hashtable<String, GFF3Record>();
	Hashtable<String, ArrayList<GFF3Record>>	cdss		= new Hashtable<String, ArrayList<GFF3Record>>();
	Hashtable<String, ArrayList<GFF3Record>>	exons		= new Hashtable<String, ArrayList<GFF3Record>>();
	ArrayList<GFF3Record>						lastCdsList;
	ArrayList<GFF3Record>						lastExonList;
	GFF3Record									lastMrnaCdsList, lastMrnaExonList;

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
			Collection<DBLink< ? , ? extends Sequence, ? , ? >> dbLinks = organism.getSequenceDBLinks(
				seqDatabase, seqAccession);
			if (dbLinks.size() != 1) {
				throw new InvalidSequence(seqDatabase, seqAccession);
			}
			else {
				// get the only one
				seq = dbLinks.iterator().next().getSource();
			}
		}
		Location loc;
		if (record.getStrand()<0)
		{
			loc= seq.getOrCreateLocation(record.getEnd(),record.getStart(), null);
		}
		else
		{
			loc = seq.getOrCreateLocation(record.getStart(), record.getEnd(), null);
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
