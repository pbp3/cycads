/*
 * Created on 05/01/2009
 */
package org.cycads.parser.gff3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

import old.org.cycads.loaders.gff3.Exon;
import old.org.cycads.loaders.gff3.GFF3Record;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class GeneralGFF3Handler implements GFF3DocumentHandler
{
	EntityFactory	factory;
	Organism		organism;
	String			seqDBName;
	String			seqVersion;
	Progress		progress;

	Pattern			cdsPattern	= Pattern.compile(Config.gff3CDSTagExpression());
	Pattern			mRNAPattern	= Pattern.compile(Config.gff3MRNATagExpression());
	Pattern			genePattern	= Pattern.compile(Config.gff3GeneTagExpression());

	//	Hashtable<String, GFF3Record>	mrnas;
	//	Hashtable<String, GFF3Record>	genes;

	public GeneralGFF3Handler(EntityFactory factory, Organism organism, String seqDBName, String seqVersion,
			Progress progress) {
		this.factory = factory;
		this.organism = organism;
		this.seqDBName = seqDBName;
		this.progress = progress;
		this.seqVersion = seqVersion;
	}

	@Override
	public void commentLine(String comment) {

	}

	@Override
	public void startDocument() {
		//			mrnas = new Hashtable<String, GFF3Record>();
		//			genes = new Hashtable<String, GFF3Record>();
	}

	@Override
	public void recordLine(GFF3Record record) {
		try {
			if (genePattern.matcher(record.getType()).matches()) {
				handleGene(record);
				genes.put(record.getNoteValue("ID"), record);
			}
			else if (mRNAPattern.matcher(record.getType()).matches()) {
				mrnas.put(record.getNoteValue("ID"), record);
			}
			else if (cdsPattern.matcher(record.getType()).matches()) {
				//Get a Statement object
				stmt = con.createStatement();
				String acypi = record.getNoteValue("ID");
				String extDB = record.getNoteValue("Name");
				String mrnaID = record.getNoteValue("Derives_from");
				GFF3Record mRNA = mrnas.get(mrnaID);
				String name = "", geneComment = "";
				String dbXRefGeneId = "", locGene = "";
				if (mRNA != null) {
					Collection<String> products = mRNA.getNoteValues("product");
					if (products != null && products.size() > 0) {
						name = products.iterator().next();
					}
					String geneId = mRNA.getNoteValue("Parent");
					GFF3Record gene = genes.get(geneId);
					if (gene != null) {
						Collection<String> dbXRefGeneIds = gene.getNoteValues("db_xref");
						if (dbXRefGeneIds != null && dbXRefGeneIds.size() > 0) {
							dbXRefGeneId = dbXRefGeneIds.iterator().next();
						}
						Collection<String> locGenes = gene.getNoteValues("Name");
						if (locGenes != null && locGenes.size() > 0) {
							locGene = locGenes.iterator().next();
						}
						Collection<String> geneComments = gene.getNoteValues("note");
						if (geneComments != null && geneComments.size() > 0) {
							geneComment = geneComments.iterator().next();
						}
					}
				}
				if (record.getSource().equals("GLEAN")) {
					stmt.executeUpdate("INSERT INTO CDS(ACYPI, NAME, GLEAN, DBXREF_GENEID, GENE_COMMENT) VALUES('"
						+ acypi + "','" + name + "','" + extDB + "','" + dbXRefGeneId + "','" + geneComment + "')");
				}
				else {
					stmt.executeUpdate("INSERT INTO CDS(ACYPI, NAME, XP,DBXREF_GENEID,LOC_GENE,GENE_COMMENT) VALUES('"
						+ acypi + "','" + name + "','" + extDB + "','" + dbXRefGeneId + "','" + locGene + "','"
						+ geneComment + "')");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(record.getSequenceID());
		}
	}

	@Override
	public void endDocument() {
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
		// add score as note
		gene.addNote(ParametersDefault.annotationNoteTypeScore(), NumberFormat.getInstance().format(record.getScore()));
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
					for (String dbName : Config.gff3GeneDBXRefDBName(noteType, record.getSource())) {
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
		// add score as note
		mRNA.addNote(ParametersDefault.annotationNoteTypeScore(), NumberFormat.getInstance().format(record.getScore()));
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
						for (String dbName : Config.gff3MRNADBXRefDBName(noteType, record.getSource())) {
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
		CDS cds = null;
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
					for (String dbName : Config.gff3CDSDBXRefDBName(noteType, record.getSource())) {
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

	private Sequence	lastSequence		= null;
	private String		lastSeqAccession	= "";

	private Sequence getSequence(String sequenceID) {
		if (!sequenceID.equals(lastSeqAccession)) {
			Dbxref seqSynonym = factory.getDbxref(seqDBName, sequenceID);
			Collection<Sequence> seqs = organism.getSequences(seqSynonym);
			if (seqs.isEmpty()) {
				lastSequence = organism.createNewSequence(seqVersion);
				lastSequence.addSynonym(seqSynonym);
				lastSeqAccession = sequenceID;
			}
			else if (seqs.size() == 1) {
				lastSequence = seqs.iterator().next();
				lastSeqAccession = sequenceID;
			}
			else {
				return null;
			}

		}
		return lastSequence;
	}

}
