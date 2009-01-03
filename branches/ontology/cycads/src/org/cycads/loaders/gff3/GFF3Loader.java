/*
 * Created on 16/12/2008
 */
package org.cycads.loaders.gff3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Gene;
import org.cycads.entities.annotation.RNA;
import org.cycads.entities.note.Note;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.general.ParametersDefault;

public class GFF3Loader implements GFF3DocumentHandler
{
	Pattern								genePattern					= Pattern.compile(ParametersDefault.gff3GeneTagExpression());
	Pattern								mRNAPattern					= Pattern.compile(ParametersDefault.gff3MRNATagExpression());
	Pattern								exonPattern					= Pattern.compile(ParametersDefault.gff3ExonTagExpression());
	Pattern								cdsPattern					= Pattern.compile(ParametersDefault.gff3CDSTagExpression());

	Pattern								dbXREFPattern				= Pattern.compile(ParametersDefault.gff3NoteDBXRefExpression());

	Pattern								geneFunctionPattern			= Pattern.compile(ParametersDefault.gff3GeneFunctionExpression());
	Pattern								geneExcludePattern			= Pattern.compile(ParametersDefault.gff3GeneExcludeExpression());
	Pattern[]							geneDBXREFPatterns			= ParametersDefault.gff3GeneDBXRefPatterns();
	String[]							geneDBXREFNames				= ParametersDefault.gff3GeneDBXRefNames();

	Pattern								mRNAFunctionPattern			= Pattern.compile(ParametersDefault.gff3MRNAFunctionExpression());
	Pattern								mRNAExcludePattern			= Pattern.compile(ParametersDefault.gff3MRNAExcludeExpression());
	Pattern[]							mRNADBXREFPatterns			= ParametersDefault.gff3MRNADBXRefPatterns();
	String[]							mRNADBXREFNames				= ParametersDefault.gff3MRNADBXRefNames();

	Pattern								cdsFunctionPattern			= Pattern.compile(ParametersDefault.gff3CDSFunctionExpression());
	Pattern								cdsExcludePattern			= Pattern.compile(ParametersDefault.gff3CDSExcludeExpression());
	Pattern[]							cdsDBXREFPatterns			= ParametersDefault.gff3CDSDBXRefPatterns();
	String[]							cdsDBXREFNames				= ParametersDefault.gff3CDSDBXRefNames();

	//Id that will be used by the exons
	Pattern								mRNAIdPattern				= Pattern.compile(ParametersDefault.gff3MRNAIdExpression());
	Pattern								exonParentAccesionPattern	= Pattern.compile(ParametersDefault.gff3ExonParentAccesionExpression());

	Pattern								mRNAParentAccesionPattern	= Pattern.compile(ParametersDefault.gff3MRNAParentAccesionExpression());
	String								mRNAParentDB				= ParametersDefault.gff3MRNAParentDB();

	Pattern								cdsParentAccesionPattern	= Pattern.compile(ParametersDefault.gff3CDSParentAccesionExpression());
	String								cdsParentDB					= ParametersDefault.gff3CDSParentDB();

	String								seqDatabase;
	int									seqVersionDefault;
	Organism							organism;

	ArrayList<GFF3Record>				mrnas						= new ArrayList<GFF3Record>();
	ArrayList<GFF3Record>				cdss						= new ArrayList<GFF3Record>();
	Hashtable<String, ArrayList<Exon>>	exons						= new Hashtable<String, ArrayList<Exon>>();

	Sequence							lastSequence;
	String								lastSeqAccession;

	//	String										methodForDBLink	= Config.gff3MethodForDBLink();

	@Override
	public void commentLine(String comment) {
		// Do nothing
	}

	@Override
	public void endDocument() {
		handleMRNAs();
		handleCDSs();
	}

	private void handleMRNAs() {
		for (GFF3Record record : mrnas) {
			String mrnaID = null;
			Collection<Note> notes = record.getNotes();
			for (Note note : notes) {
				if (!mRNAExcludePattern.matcher(note.getType()).matches()
					&& mRNAIdPattern.matcher(note.getType()).matches()) {
					mrnaID = note.getValue();
				}
			}
			ArrayList<Exon> exonsMRNA = exons.get(mrnaID);
			Collections.sort(exonsMRNA);
			Collection<Intron> introns = new ArrayList<Intron>(exonsMRNA.size() - 1);
			if (exonsMRNA.size() > 0) {
				if (record.getStart() < exonsMRNA.get(0).getMin()) {
					introns.add(new SimpleIntron(record.getStart(), exonsMRNA.get(0).getMin() - 1));
				}
				Exon exon1, exon2 = exonsMRNA.get(0);
				for (int i = 1; i < exonsMRNA.size(); i++) {
					exon1 = exon2;
					exon2 = exonsMRNA.get(i);
					if (exon1.getMax() < (exon2.getMin() - 1)) {
						introns.add(new SimpleIntron(exon1.getMax() + 1, exon2.getMin() - 1));
					}
				}
				if (exon2.getEnd() < record.getEnd()) {
					introns.add(new SimpleIntron(exon2.getMax() + 1, record.getEnd()));
				}
			}
			else {
				introns.add(new SimpleIntron(record.getStart(), record.getEnd()));
			}
			Sequence seq = getSequence(record.getSequenceID());
			Subsequence subseq;
			if (record.getStrand() < 0) {
				subseq = seq.getOrCreateSubsequence(record.getEnd(), record.getStart(), introns);
			}
			else {
				subseq = seq.getOrCreateSubsequence(record.getStart(), record.getEnd(), introns);
			}
			AnnotationMethod annotationMethod = subseq.getMethodInstance(record.getSource());
			RNA mRNA = subseq.getOrCreateMRNA(annotationMethod);
			for (Note note : notes) {
				String noteType = note.getType();
				String noteValue = note.getValue();
				if (!mRNAExcludePattern.matcher(noteType).matches()) {
					if (mRNAParentAccesionPattern.matcher(noteType).matches()) {
						Collection<Subsequence> subSeqs = seq.getSubSeqs(mRNAParentDB, noteValue);
						for (Subsequence subseq1 : subSeqs) {
							if (subseq1.contains(subseq) && (subseq.getGenes().size() > 0)) {
								subseq1.generateMRNA(subseq);
							}
						}
					}
					else {
						boolean isMRNANote = true;
						if (dbXREFPattern.matcher(noteType).matches()) {
							String[] strs = noteValue.split(ParametersDefault.gff3NoteDBXRefSplit());
							subseq.addDBRecord(strs[0], strs[1]);
							isMRNANote = false;
						}
						else {
							for (int i = 0; i < mRNADBXREFPatterns.length; i++) {
								if (mRNADBXREFPatterns[i].matcher(noteType).matches()) {
									subseq.addDBRecord(mRNADBXREFNames[i], noteValue);
									isMRNANote = false;
								}
							}
						}
						if (mRNAFunctionPattern.matcher(noteType).matches()) {
							mRNA.addFunction(noteValue);
						}
						if (isMRNANote) {
							mRNA.addNote(note);
						}
					}
				}
			}
		}
	}

	private void handleCDSs() {
		for (GFF3Record record : cdss) {
			//get MRNA Parent SubSequence
			//get Introns of MRNA parent subsequence
			//get or Create subsequence of this CDS
			//associate subsequences as MRNA parent and CDS child
			//handle Notes of this CDS
			//exclude notes for exclusion and parent note
			//handle dbxrefs notes (general and for CDSs) in the subsequence
			//handle functions notes
			//handle other notes
			//add score as note
			String cdsID = null;
			Collection<Note> notes = record.getNotes();
			for (Note note : notes) {
				if (!cdsExcludePattern.matcher(note.getType()).matches()
					&& cdsIdPattern.matcher(note.getType()).matches()) {
					cdsID = note.getValue();
				}
			}
			ArrayList<Exon> exonsMRNA = exons.get(cdsID);
			Collections.sort(exonsMRNA);
			Collection<Intron> introns = new ArrayList<Intron>(exonsMRNA.size() - 1);
			if (exonsMRNA.size() > 0) {
				if (record.getStart() < exonsMRNA.get(0).getMin()) {
					introns.add(new SimpleIntron(record.getStart(), exonsMRNA.get(0).getMin() - 1));
				}
				Exon exon1, exon2 = exonsMRNA.get(0);
				for (int i = 1; i < exonsMRNA.size(); i++) {
					exon1 = exon2;
					exon2 = exonsMRNA.get(i);
					if (exon1.getMax() < (exon2.getMin() - 1)) {
						introns.add(new SimpleIntron(exon1.getMax() + 1, exon2.getMin() - 1));
					}
				}
				if (exon2.getEnd() < record.getEnd()) {
					introns.add(new SimpleIntron(exon2.getMax() + 1, record.getEnd()));
				}
			}
			else {
				introns.add(new SimpleIntron(record.getStart(), record.getEnd()));
			}
			Sequence seq = getSequence(record.getSequenceID());
			Subsequence subseq;
			if (record.getStrand() < 0) {
				subseq = seq.getOrCreateSubsequence(record.getEnd(), record.getStart(), introns);
			}
			else {
				subseq = seq.getOrCreateSubsequence(record.getStart(), record.getEnd(), introns);
			}
			AnnotationMethod annotationMethod = subseq.getMethodInstance(record.getSource());
			RNA mRNA = subseq.getOrCreateMRNA(annotationMethod);
			for (Note note : notes) {
				String noteType = note.getType();
				String noteValue = note.getValue();
				if (!mRNAExcludePattern.matcher(noteType).matches()) {
					if (mRNAParentAccesionPattern.matcher(noteType).matches()) {
						Collection<Subsequence> subSeqs = seq.getSubSeqs(mRNAParentDB, noteValue);
						for (Subsequence subseq1 : subSeqs) {
							if (subseq1.contains(subseq) && (subseq.getGenes().size() > 0)) {
								subseq1.generateMRNA(subseq);
							}
						}
					}
					else {
						boolean isMRNANote = true;
						if (dbXREFPattern.matcher(noteType).matches()) {
							String[] strs = noteValue.split(ParametersDefault.gff3NoteDBXRefSplit());
							subseq.addDBRecord(strs[0], strs[1]);
							isMRNANote = false;
						}
						else {
							for (int i = 0; i < mRNADBXREFPatterns.length; i++) {
								if (mRNADBXREFPatterns[i].matcher(noteType).matches()) {
									subseq.addDBRecord(mRNADBXREFNames[i], noteValue);
									isMRNANote = false;
								}
							}
						}
						if (mRNAFunctionPattern.matcher(noteType).matches()) {
							mRNA.addFunction(noteValue);
						}
						if (isMRNANote) {
							mRNA.addNote(note);
						}
					}
				}
			}
		}
		// add score as note
		gene.addNote(ParametersDefault.annotationNoteTypeScore(), NumberFormat.getInstance().format(record.getScore()));
		Note nameNote = record.getNotes(ParametersDefault.gff3NoteTypeName());
	}

	@Override
	public void recordLine(GFF3Record record) throws InvalidSequence {
		String type = record.getType();
		if (genePattern.matcher(type).matches()) {
			// record is a gene
			Sequence seq = getSequence(record.getSequenceID());
			Subsequence subseq;
			if (record.getStrand() < 0) {
				subseq = seq.getOrCreateSubsequence(record.getEnd(), record.getStart(), null);
			}
			else {
				subseq = seq.getOrCreateSubsequence(record.getStart(), record.getEnd(), null);
			}
			AnnotationMethod annotationMethod = subseq.getMethodInstance(record.getSource());
			Gene gene = subseq.getOrCreateGene(annotationMethod);
			Collection<Note> notes = record.getNotes();
			for (Note note : notes) {
				String noteType = note.getType();
				String noteValue = note.getValue();
				if (!geneExcludePattern.matcher(noteType).matches()) {
					boolean isGeneNote = true;
					if (dbXREFPattern.matcher(noteType).matches()) {
						String[] strs = noteValue.split(ParametersDefault.gff3NoteDBXRefSplit());
						subseq.addDBRecord(strs[0], strs[1]);
						isGeneNote = false;
					}
					else {
						for (int i = 0; i < geneDBXREFPatterns.length; i++) {
							if (geneDBXREFPatterns[i].matcher(noteType).matches()) {
								subseq.addDBRecord(geneDBXREFNames[i], noteValue);
								isGeneNote = false;
							}
						}
					}
					if (geneFunctionPattern.matcher(noteType).matches()) {
						gene.addFunction(noteValue);
					}
					if (isGeneNote) {
						gene.addNote(note);
					}
				}
			}
		}
		else if (mRNAPattern.matcher(type).matches()) {
			mrnas.add(record);
		}
		else if (cdsPattern.matcher(type).matches()) {
			cdss.add(record);
		}
		else if (exonPattern.matcher(type).matches()) {
			Collection<Note> notes = record.getNotes();
			for (Note note : notes) {
				String noteType = note.getType();
				String noteValue = note.getValue();
				if (exonParentAccesionPattern.matcher(noteType).matches()) {
					ArrayList<Exon> exons1 = exons.get(noteValue);
					if (exons1 == null) {
						exons1 = new ArrayList<Exon>();
						exons.put(noteValue, exons1);
					}
					exons1.add(new Exon(record.getStart(), record.getEnd()));
				}
			}
		}
	}

	private Sequence getSequence(String sequenceID) throws InvalidSequence {
		if (sequenceID.equals(lastSeqAccession)) {
			return lastSequence;
		}
		Sequence seq;
		if (ParametersDefault.getNameSpaceDefault().equals(seqDatabase)) {
			// sequence accession in the database
			return organism.getOrCreateSequence(sequenceID, seqVersionDefault);
		}
		else {
			// sequence accession is external
			Collection<Sequence> seqs = organism.getSequences(seqDatabase, sequenceID);
			if (seqs.size() != 1) {
				throw new InvalidSequence(seqDatabase, sequenceID);
			}
			else {
				// get the only one
				return seqs.iterator().next();
			}
		}
	}

	@Override
	public void startDocument(String locator) {
		// Do nothing
	}

}
