/*
 * Created on 16/12/2008
 */
package org.cycads.loaders.gff3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.note.Note;
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
	Pattern										mRNAPattern	= Pattern.compile(ParametersDefault.gff3MRNATagExpression());
	Pattern										exonPattern	= Pattern.compile(ParametersDefault.gff3ExonTagExpression());
	Pattern										cdsPattern	= Pattern.compile(ParametersDefault.gff3CDSTagExpression());
	Hashtable<String, GFF3Record>				genes		= new Hashtable<String, GFF3Record>();
	Hashtable<String, GFF3Record>				mrnas		= new Hashtable<String, GFF3Record>();
	Hashtable<String, ArrayList<GFF3Record>>	cdss		= new Hashtable<String, ArrayList<GFF3Record>>();
	Hashtable<String, ArrayList<GFF3Record>>	exons		= new Hashtable<String, ArrayList<GFF3Record>>();
	ArrayList<GFF3Record>						lastCdsList;
	ArrayList<GFF3Record>						lastExonList;
	GFF3Record									lastMrnaCdsList, lastMrnaExonList;
	Sequence									lastSequence;
	String										lastSeqAccession;

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
	public void recordLine(GFF3Record record) throws InvalidSequence
	{
		Sequence seq = getSequence(record.getSequenceID());
		String type = record.getType();
		String method = record.getSource();
		if (genePattern.matcher(type).matches())
		{
			Location loc;
			if (record.getStrand() < 0)
			{
				loc = seq.getOrCreateLocation(record.getEnd(), record.getStart(), null);
			}
			else
			{
				loc = seq.getOrCreateLocation(record.getStart(), record.getEnd(), null);
			}
			Gene gene = loc.getGene(method);
			if (gene==null)
			{
				gene=loc.createGene(method);
			}
			// add score as note
			gene.addNote(NumberFormat.getInstance().format(record.getScore()),
				ParametersDefault.annotationNoteTypeScore());
			Note nameNote = record.getNotes(ParametersDefault.gff3NoteTypeName());
			gene.addNote(, type)
		}
		else if (mRNAPattern.matcher(type).matches())
		{

		}

		// TODO Auto-generated method stub

	}

	private Sequence getSequence(String sequenceID) throws InvalidSequence
	{
		Sequence seq;
		if (ParametersDefault.getNameSpaceDefault().equals(seqDatabase))
		{
			// sequence accession in the database
			return organism.getOrCreateSequence(sequenceID, seqVersionDefault);
		}
		else
		{
			// sequence accession is external
			Collection<DBLink< ? , ? extends Sequence, ? , ? >> dbLinks = organism.getSequenceDBLinks(seqDatabase,
				sequenceID);
			if (dbLinks.size() != 1)
			{
				throw new InvalidSequence(seqDatabase, sequenceID);
			}
			else
			{
				// get the only one
				return dbLinks.iterator().next().getSource();
			}
		}
	}

	@Override
	public void startDocument(String locator)
	{
		// Do nothing
	}

}
