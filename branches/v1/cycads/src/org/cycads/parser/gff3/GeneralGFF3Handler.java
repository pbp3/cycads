/*
 * Created on 05/01/2009
 */
package org.cycads.parser.gff3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleSubsequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class GeneralGFF3Handler implements GFF3DocumentHandler
{
	private EntityFactory							factory;
	private Organism								organism;
	private String									seqDBName;
	private String									seqVersion;
	private Progress								progress;

	private Type									cdsAnnotationType;
	private Type									mrnaAnnotationType;
	private Type									geneAnnotationType;

	Hashtable<String, ArrayList<SubseqAnnotation>>	mrnasHash;
	ArrayList<GFF3Record>							cdss;
	ArrayList<SubseqAnnotation>						mrnas;

	public GeneralGFF3Handler(EntityFactory factory, Organism organism, String seqDBName, String seqVersion,
			Progress progress) {
		this.factory = factory;
		this.organism = organism;
		this.seqDBName = seqDBName;
		this.progress = progress;
		this.seqVersion = seqVersion;
		cdsAnnotationType = factory.getAnnotationType(ParametersDefault.getCDSAnnotationTypeName());
		mrnaAnnotationType = factory.getAnnotationType(ParametersDefault.getMRNAAnnotationTypeName());
		geneAnnotationType = factory.getAnnotationType(ParametersDefault.getGeneAnnotationTypeName());
	}

	@Override
	public void commentLine(String comment) {

	}

	@Override
	public void startDocument() {
		cdss = new ArrayList<GFF3Record>();
		mrnas = new ArrayList<SubseqAnnotation>();
		mrnasHash = new Hashtable<String, ArrayList<SubseqAnnotation>>();
	}

	@Override
	public void recordLine(GFF3Record record) {
		if (GFF3FileConfig.isGene(record.getType(), record.getSource())) {
			record.setType(GFF3FileConfig.geneType);
		}
		else if (GFF3FileConfig.isMRNA(record.getType(), record.getSource())) {
			record.setType(GFF3FileConfig.mrnaType);
		}
		else if (GFF3FileConfig.isCDS(record.getType(), record.getSource())) {
			record.setType(GFF3FileConfig.cdsType);
		}
		else if (GFF3FileConfig.isExon(record.getType(), record.getSource())) {
			record.setType(GFF3FileConfig.exonType);
		}

		removeTags(record);
		changeTags(record);

		if (record.getType().equals(GFF3FileConfig.geneType)) {
			handleGene(record);
		}
		if (record.getType().equals(GFF3FileConfig.mrnaType)) {
			handleMRNA(record);
		}
		if (record.getType().equals(GFF3FileConfig.exonType)) {
			handleExon(record);
		}
		if (record.getType().equals(GFF3FileConfig.geneType)) {
			handleGene(record);
		}
	}

	@Override
	public void endDocument() {
		persistMrnas();
		for (GFF3Record record : cdss) {
			handleCDS2(record);
		}
	}

	private void handleGene(GFF3Record record) {
		Sequence seq = getSequence(record.getSequenceID());
		Subsequence subseq;
		if (record.getStrand() < 0) {
			subseq = seq.getSubsequence(record.getEnd(), record.getStart(), null);
			if (subseq == null) {
				subseq = seq.createSubsequence(record.getEnd(), record.getStart(), null);
			}
		}
		else {
			subseq = seq.getSubsequence(record.getStart(), record.getEnd(), null);
			if (subseq == null) {
				subseq = seq.createSubsequence(record.getStart(), record.getEnd(), null);
			}
		}
		AnnotationMethod annotationMethod = GFF3FileConfig.getAnnotationMethod(factory, record);
		SubseqAnnotation annot = subseq.createAnnotation(geneAnnotationType, annotationMethod);
		// add score as note
		annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), NumberFormat.getInstance().format(
			record.getScore()));
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			handleAnnotSynonym(note, annot, record);
			handleSubseqSynonym(note, subseq, record);
			handleAnnotParent(note, annot, record);
			handleAnnotNote(note, annot, record);
			handleFunctionAnnot(note, subseq, record);
		}
		progress.completeStep();
	}

	private void handleMRNA(GFF3Record record) {
		Sequence seq = getSequence(record.getSequenceID());
		SimpleSubsequence subseq;
		if (record.getStrand() < 0) {
			subseq = new SimpleSubsequence(factory, seq, record.getEnd(), record.getStart());
		}
		else {
			subseq = new SimpleSubsequence(factory, seq, record.getStart(), record.getEnd());
		}
		AnnotationMethod annotationMethod = GFF3FileConfig.getAnnotationMethod(factory, record);
		SubseqAnnotation annot = subseq.createAnnotation(mrnaAnnotationType, annotationMethod);
		// add score as note
		annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), NumberFormat.getInstance().format(
			record.getScore()));
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			handleAnnotSynonym(note, annot, record);
			handleSubseqSynonym(note, subseq, record);
			handleAnnotParent(note, annot, record);
			handleAnnotNote(note, annot, record);
			handleFunctionAnnot(note, subseq, record);
		}
		Collection<Dbxref> syns = annot.getSynonyms();
		for (Dbxref syn : syns) {
			ArrayList<SubseqAnnotation> annots = mrnasHash.get(syn.toString());
			if (annots == null) {
				annots = new ArrayList<SubseqAnnotation>();
				mrnasHash.put(syn.toString(), annots);
			}
			annots.add(annot);
		}
		mrnas.add(annot);
		progress.completeStep();

	}

	private void handleExon(GFF3Record record) {
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			ArrayList<Dbxref> parentDbxrefs = getParentDbxrefs(note, record.getType(), record.getSource());
			for (Dbxref parentDbxref : parentDbxrefs) {
				Collection<SubseqAnnotation> annotsParent = mrnasHash.get(parentDbxref.toString());
				if (annotsParent != null && !annotsParent.isEmpty()) {
					for (SubseqAnnotation annotParent : annotsParent) {
						((SimpleSubsequence) annotParent.getSubsequence()).addExon(record.getStart(), record.getEnd());
					}
				}
			}
		}
	}

	private void handleCDS(GFF3Record record) {
		cdss.add(record);
	}

	private void handleCDS2(GFF3Record record) {
		Sequence seq = getSequence(record.getSequenceID());
		ArrayList<Type> mrnaTypeCollection = new ArrayList<Type>(1);
		mrnaTypeCollection.add(mrnaAnnotationType);
		Collection<Intron> intronsParent = null;
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			ArrayList<Dbxref> parentDbxrefs = getParentDbxrefs(note, record.getType(), record.getSource());
			for (Dbxref parentDbxref : parentDbxrefs) {
				Collection<SubseqAnnotation> annotsParent = organism.getAnnotations(null, mrnaTypeCollection,
					parentDbxref);
				if (!annotsParent.isEmpty()) {
					intronsParent = annotsParent.iterator().next().getSubsequence().getIntrons();
				}
			}
		}

		Subsequence subseq;
		if (record.getStrand() < 0) {
			subseq = seq.getSubsequence(record.getEnd(), record.getStart(), intronsParent);
			if (subseq == null) {
				subseq = seq.createSubsequence(record.getEnd(), record.getStart(), intronsParent);
			}
		}
		else {
			subseq = seq.getSubsequence(record.getStart(), record.getEnd(), intronsParent);
			if (subseq == null) {
				subseq = seq.createSubsequence(record.getStart(), record.getEnd(), intronsParent);
			}
		}
		AnnotationMethod annotationMethod = GFF3FileConfig.getAnnotationMethod(factory, record);
		SubseqAnnotation annot = subseq.createAnnotation(cdsAnnotationType, annotationMethod);
		// add score as note
		annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), NumberFormat.getInstance().format(
			record.getScore()));
		for (Note note : notes) {
			handleAnnotSynonym(note, annot, record);
			handleSubseqSynonym(note, subseq, record);
			handleAnnotParent(note, annot, record);
			handleAnnotNote(note, annot, record);
			handleFunctionAnnot(note, subseq, record);
		}
		progress.completeStep();
	}

	private ArrayList<Dbxref> getParentDbxrefs(Note note, String type, String source) {
		ArrayList<Dbxref> ret = new ArrayList<Dbxref>();
		ArrayList<Pattern> patterns = GFF3FileConfig.getParentAccessionTagPatterns(type, source);
		ArrayList<String> dbNames = GFF3FileConfig.getParentDbNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(note.getType().getName()).matches()) {
				ret.add(factory.getDbxref(dbNames.get(i), note.getValue()));
			}
		}
		return ret;
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
