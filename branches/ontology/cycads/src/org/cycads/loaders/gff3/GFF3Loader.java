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
import org.cycads.entities.annotation.CDS;
import org.cycads.entities.annotation.Gene;
import org.cycads.entities.annotation.RNA;
import org.cycads.entities.note.Note;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.general.CacheCleanerController;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class GFF3Loader implements GFF3DocumentHandler {
	Pattern								genePattern					= Pattern.compile(Config.gff3GeneTagExpression());
	Pattern								mRNAPattern					= Pattern.compile(Config.gff3MRNATagExpression());
	Pattern								exonPattern					= Pattern.compile(Config.gff3ExonTagExpression());
	Pattern								cdsPattern					= Pattern.compile(Config.gff3CDSTagExpression());

	Pattern								dbXREFPattern				= Pattern.compile(Config.gff3NoteDBXRefExpression());

	Pattern								geneFunctionPattern			= Pattern.compile(Config.gff3GeneFunctionExpression());
	Pattern								geneExcludePattern			= Pattern.compile(Config.gff3GeneExcludeExpression());

	Pattern								mRNAFunctionPattern			= Pattern.compile(Config.gff3MRNAFunctionExpression());
	Pattern								mRNAExcludePattern			= Pattern.compile(Config.gff3MRNAExcludeExpression());

	Pattern								cdsFunctionPattern			= Pattern.compile(Config.gff3CDSFunctionExpression());
	Pattern								cdsExcludePattern			= Pattern.compile(Config.gff3CDSExcludeExpression());

	// Id that will be used by the exons
	Pattern								mRNAIdPattern				= Pattern.compile(Config.gff3MRNAIdExpression());
	Pattern								exonParentAccesionPattern	= Pattern.compile(Config.gff3ExonParentAccesionExpression());

	Pattern								mRNAParentAccesionPattern	= Pattern.compile(Config.gff3MRNAParentAccesionExpression());
	String								mRNAParentDB				= Config.gff3MRNAParentDB();

	Pattern								cdsParentAccesionPattern	= Pattern.compile(Config.gff3CDSParentAccesionExpression());
	String								cdsParentDB					= Config.gff3CDSParentDB();

	Organism							organism;
	String								seqDatabase;
	int									seqVersionDefault			= 0;
	boolean								createSequence;

	ArrayList<GFF3Record>				mrnas						= new ArrayList<GFF3Record>();
	ArrayList<GFF3Record>				cdss						= new ArrayList<GFF3Record>();
	Hashtable<String, ArrayList<Exon>>	exons						= new Hashtable<String, ArrayList<Exon>>();

	Sequence							lastSequence				= null;
	String								lastSeqAccession			= "";

	Progress							progressGene;
	Progress							progressMRNA;
	Progress							progressCDS;
	CacheCleanerController				cacheCleaner;

	public GFF3Loader(Progress progressGene, Progress progressMRNA, Progress progressCDS,
			CacheCleanerController cacheCleaner, Organism organism, String seqDatabase, boolean createSequence) {
		this.organism = organism;
		this.seqDatabase = seqDatabase;
		this.createSequence = createSequence;
		this.progressGene = progressGene;
		this.progressMRNA = progressMRNA;
		this.progressCDS = progressCDS;
		this.cacheCleaner = cacheCleaner;
	}

	@Override
	public void commentLine(String comment) {
		// Do nothing
	}

	@Override
	public void endDocument() {
		progressGene.finish(progressGene.getStep());
		progressMRNA.init();
		for (GFF3Record record : mrnas) {
			handleMRNA(record);
		}
		progressMRNA.finish(progressMRNA.getStep());
		progressCDS.init();
		for (GFF3Record record : cdss) {
			handleCDS(record);
		}
		progressCDS.finish(progressCDS.getStep());
	}

	private void handleGene(GFF3Record record) {
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
					for (String dbName : Config.gff3GeneDBXRefDBName(noteType)) {
						// for (int i = 0; i < geneDBXREFPatterns.length; i++) {
						// if (geneDBXREFPatterns[i].matcher(noteType).matches()) {
						subseq.addDBRecord(dbName, noteValue);
						isGeneNote = false;
						// }
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
		progressGene.completeStep();
		cacheCleaner.incCache();
	}

	private void handleMRNA(GFF3Record record) {
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
					Collection<Subsequence> subSeqs = seq.getSubSeqsByDBRecord(mRNAParentDB, noteValue);
					for (Subsequence subseqGene : subSeqs) {
						if (subseqGene.contains(subseq) && (!subseqGene.getGenes().isEmpty())) {
							mRNA.setParent(subseqGene);
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
						for (String dbName : Config.gff3MRNADBXRefDBName(noteType)) {
							// for (int i = 0; i < mRNADBXREFPatterns.length; i++) {
							// if (mRNADBXREFPatterns[i].matcher(noteType).matches()) {
							subseq.addDBRecord(dbName, noteValue);
							isMRNANote = false;
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
		progressMRNA.completeStep();
		cacheCleaner.incCache();
	}

	private void handleCDS(GFF3Record record) {
		Sequence seq = getSequence(record.getSequenceID());
		CDS cds;
		// get MRNA Parent SubSequence
		Subsequence subseq = null;
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			if (!cdsExcludePattern.matcher(note.getType()).matches()
				&& cdsParentAccesionPattern.matcher(note.getType()).matches()) {
				Collection<Subsequence> subSeqs = seq.getSubSeqsByDBRecord(cdsParentDB, note.getValue());
				for (Subsequence subseqMRNA : subSeqs) {
					if (!subseqMRNA.getMRNAs().isEmpty() && subseqMRNA.getMinPosition() <= record.getStart()
						&& subseqMRNA.getMaxPosition() >= record.getEnd()) {
						// get Introns of MRNA parent subsequence
						// get or Create subsequence of this CDS of the MRNA parent
						if (record.getStrand() < 0) {
							subseq = seq.getOrCreateSubsequence(record.getEnd(), record.getStart(),
								subseqMRNA.getIntrons());
						}
						else {
							subseq = seq.getOrCreateSubsequence(record.getStart(), record.getEnd(),
								subseqMRNA.getIntrons());
						}
						cds = subseq.getOrCreateCDS(subseq.getMethodInstance(record.getSource()));
						// associate subsequences as MRNA parent and CDS child
						cds.setParent(subseqMRNA);
					}
				}
			}
		}

		// add score as note
		cds.addNote(ParametersDefault.annotationNoteTypeScore(), NumberFormat.getInstance().format(record.getScore()));
		// handle Notes of this record
		for (Note note : notes) {
			String noteType = note.getType();
			String noteValue = note.getValue();
			// exclude notes for exclusion and parent note
			if (!cdsExcludePattern.matcher(noteType).matches() && !cdsParentAccesionPattern.matcher(noteType).matches()) {
				boolean isCDSNote = true;
				// handle dbxrefs notes (general and for CDSs) in the subsequence
				if (dbXREFPattern.matcher(noteType).matches()) {
					String[] strs = noteValue.split(ParametersDefault.gff3NoteDBXRefSplit());
					subseq.addDBRecord(strs[0], strs[1]);
					isCDSNote = false;
				}
				else {
					for (String dbName : Config.gff3CDSDBXRefDBName(noteType)) {
						// for (int i = 0; i < cdsDBXREFPatterns.length; i++) {
						// if (cdsDBXREFPatterns[i].matcher(noteType).matches()) {
						subseq.addDBRecord(dbName, noteValue);
						isCDSNote = false;
					}
				}
				// handle functions notes
				if (cdsFunctionPattern.matcher(noteType).matches()) {
					cds.addFunction(noteValue);
				}
				// handle other notes
				if (isCDSNote) {
					cds.addNote(note);
				}
			}
		}
		progressCDS.completeStep();
		cacheCleaner.incCache();
	}

	@Override
	public void recordLine(GFF3Record record) throws InvalidSequence {
		String type = record.getType();
		if (genePattern.matcher(type).matches()) {
			// record is a gene
			handleGene(record);
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
		lastSeqAccession = sequenceID;
		if (ParametersDefault.getNameSpaceDefault().equals(seqDatabase)) {
			// sequence accession in the database
			lastSequence = organism.getOrCreateSequence(sequenceID, seqVersionDefault);
			return lastSequence;
		}
		else {
			// sequence accession is external
			Collection<Sequence> seqs = organism.getSequences(seqDatabase, sequenceID);
			if (seqs.size() != 1) {
				throw new InvalidSequence(seqDatabase, sequenceID);
			}
			else {
				// get the only one
				lastSequence = seqs.iterator().next();
				return lastSequence;
			}
		}
	}

	@Override
	public void startDocument(String locator) {
		progressGene.init();
	}

}
