/*
 * Created on 16/12/2008
 */
package org.cycads.loaders.gff3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Gene;
import org.cycads.entities.annotation.AnnotOntology;
import org.cycads.entities.note.Note;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.sequence.Organism;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;

public class GFF3Loader implements GFF3DocumentHandler
{
	Pattern										genePattern		= Pattern.compile(ParametersDefault.gff3GeneTagExpression());
	Pattern										mRNAPattern		= Pattern.compile(ParametersDefault.gff3MRNATagExpression());
	Pattern										exonPattern		= Pattern.compile(ParametersDefault.gff3ExonTagExpression());
	Pattern										cdsPattern		= Pattern.compile(ParametersDefault.gff3CDSTagExpression());
	Pattern										idPattern		= Pattern.compile(ParametersDefault.gff3NoteIdExpression());
	Pattern										dbXREFPattern	= Pattern.compile(ParametersDefault.gff3NoteDBXRefExpression());

	String										seqDatabase;
	int											seqVersionDefault;
	Organism									organism;
	Hashtable<String, Gene>						genes			= new Hashtable<String, Gene>();
	Hashtable<String, GFF3Record>				mrnas			= new Hashtable<String, GFF3Record>();
	Hashtable<String, ArrayList<GFF3Record>>	cdss			= new Hashtable<String, ArrayList<GFF3Record>>();
	Hashtable<String, ArrayList<GFF3Record>>	exons			= new Hashtable<String, ArrayList<GFF3Record>>();
	ArrayList<GFF3Record>						lastCdsList;
	ArrayList<GFF3Record>						lastExonList;
	GFF3Record									lastMrnaCdsList, lastMrnaExonList;
	Sequence									lastSequence;
	String										lastSeqAccession;

	String										methodForDBLink	= Config.gff3MethodForDBLink();

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
			// record is a gene
			Subsequence loc;
			if (record.getStrand() < 0)
			{
				loc = seq.getOrCreateSubsequence(record.getEnd(), record.getStart(), null);
			}
			else
			{
				loc = seq.getOrCreateSubsequence(record.getStart(), record.getEnd(), null);
			}
			Gene gene = loc.getGene(method);
			if (gene == null)
			{
				gene = loc.createGene(method);
			}
			Collection<Note> notes = record.getNotes();
			for (Note note : notes)
			{
				if (dbXREFPattern.matcher(note.getType()).matches())
				{
					String[] strs = note.getValue().split(ParametersDefault.gff3NoteDBXRefSplit());
					gene.createDBLink(methodForDBLink, strs[1], strs[0]);
				}
				else
				{
					if (idPattern.matcher(note.getType()).matches())
					{
						genes.put(note.getValue(), gene);
					}
					gene.addNote(note);
				}
			}
			// add score as note
			gene.addNote(ParametersDefault.annotationNoteTypeScore(),
				NumberFormat.getInstance().format(record.getScore()));
			Note nameNote = record.getNotes(ParametersDefault.gff3NoteTypeName());
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
			Collection<AnnotOntology< ? , ? extends Sequence, ? , ? >> dbLinks = organism.getSequences(seqDatabase,
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
