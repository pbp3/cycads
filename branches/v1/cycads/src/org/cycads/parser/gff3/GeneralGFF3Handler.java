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
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.annotation.SubseqFunctionAnnotation;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.SimpleSubsequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.Function;
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

		removeAndChangeNotesTag(record);

		if (record.getType().equals(GFF3FileConfig.geneType)) {
			handleGene(record);
		}
		if (record.getType().equals(GFF3FileConfig.mrnaType)) {
			handleMRNA(record);
		}
		if (record.getType().equals(GFF3FileConfig.exonType)) {
			handleExon(record);
		}
		if (record.getType().equals(GFF3FileConfig.cdsType)) {
			handleCDS(record);
		}
	}

	private void removeAndChangeNotesTag(GFF3Record record) {
		Collection<Pattern> removePatterns = GFF3FileConfig.getRemoveTagPatterns(record.getType(), record.getSource());
		ArrayList<Pattern> replacePatterns = GFF3FileConfig.getReplaceTagPatterns(record.getType(), record.getSource());
		ArrayList<String> replaceNewValues = GFF3FileConfig.getReplaceNewValues(record.getType(), record.getSource());
		Collection<Note> notes = record.getNotes();
		for (Note note : new ArrayList<Note>(notes)) {
			for (Pattern pattern : removePatterns) {
				if (pattern.matcher(note.getType().getName()).matches()) {
					record.removeNote(note);
					note = null;
				}
			}
			if (note != null) {
				for (int i = 0; i < replacePatterns.size(); i++) {
					Pattern pattern = replacePatterns.get(i);
					if (pattern.matcher(note.getType().getName()).matches()) {
						record.removeNote(note);
						note = record.addNote(replaceNewValues.get(i), note.getValue());
					}
				}
			}
		}
	}

	@Override
	public void endDocument() {
		persistMrnas();
		for (GFF3Record record : cdss) {
			handleCDS2(record);
		}
	}

	private void persistMrnas() {
		for (SubseqAnnotation mrna : mrnas) {
			Subsequence subseq = mrna.getSubsequence();
			Subsequence subseqPersisted = subseq.getSequence().getSubsequence(subseq.getStart(), subseq.getEnd(),
				subseq.getIntrons());
			if (subseqPersisted == null) {
				subseqPersisted = subseq.getSequence().createSubsequence(subseq.getStart(), subseq.getEnd(),
					subseq.getIntrons());
			}
			Collection<Note> notes = subseq.getNotes();
			for (Note note : notes) {
				subseqPersisted.addNote(note.getType().getName(), note.getValue());
			}
			Collection<Dbxref> synonyms = subseq.getSynonyms();
			for (Dbxref synonym : synonyms) {
				subseqPersisted.addSynonym(synonym);
			}
			Collection<SubseqFunctionAnnotation> functions = subseq.getFunctionAnnotations(null, null);
			for (SubseqFunctionAnnotation functionAnnot : functions) {
				subseqPersisted.addFunctionAnnotation(functionAnnot.getAnnotationMethod(), functionAnnot.getFunction());
			}

			Collection<Type> types = new ArrayList<Type>();
			types.add(mrnaAnnotationType);
			Collection<SubseqAnnotation> mrnasPersisted = subseqPersisted.getAnnotations(mrna.getAnnotationMethod(),
				types, null);
			SubseqAnnotation mrnaPersisted = null;
			if (mrnasPersisted != null && !mrnasPersisted.isEmpty()) {
				mrnaPersisted = mrnasPersisted.iterator().next();
			}
			if (mrnaPersisted == null) {
				mrnaPersisted = subseqPersisted.addAnnotation(mrnaAnnotationType, mrna.getAnnotationMethod());
			}
			notes = mrna.getNotes();
			for (Note note : notes) {
				mrnaPersisted.addNote(note.getType().getName(), note.getValue());
			}
			types = mrna.getTypes();
			for (Type type : types) {
				mrnaPersisted.addType(type.getName());
			}
			Collection<Annotation> parents = mrna.getParents();
			for (Annotation parent : parents) {
				mrnaPersisted.addParent(parent);
			}
			synonyms = mrna.getSynonyms();
			for (Dbxref synonym : synonyms) {
				mrnaPersisted.addSynonym(synonym);
			}
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
		AnnotationMethod annotationMethod = factory.getAnnotationMethod(GFF3FileConfig.getAnnotationMethod(record));
		SubseqAnnotation annot = subseq.addAnnotation(geneAnnotationType, annotationMethod);
		// add score as note
		double score = record.getScore();
		if (score != GFF3Record.NO_SCORE) {
			annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), NumberFormat.getInstance().format(score));
		}
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
			subseq.addIntron(new SimpleIntron(record.getEnd(), record.getStart()));
		}
		else {
			subseq = new SimpleSubsequence(factory, seq, record.getStart(), record.getEnd());
			subseq.addIntron(new SimpleIntron(record.getStart(), record.getEnd()));
		}
		AnnotationMethod annotationMethod = factory.getAnnotationMethod(GFF3FileConfig.getAnnotationMethod(record));
		SubseqAnnotation annot = subseq.addAnnotation(mrnaAnnotationType, annotationMethod);
		// add score as note
		double score = record.getScore();
		if (score != GFF3Record.NO_SCORE) {
			annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), NumberFormat.getInstance().format(score));
		}
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
		AnnotationMethod annotationMethod = factory.getAnnotationMethod(GFF3FileConfig.getAnnotationMethod(record));
		SubseqAnnotation annot = subseq.addAnnotation(cdsAnnotationType, annotationMethod);
		// add score as note
		double score = record.getScore();
		if (score != GFF3Record.NO_SCORE) {
			annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), NumberFormat.getInstance().format(score));
		}
		for (Note note : notes) {
			handleAnnotSynonym(note, annot, record);
			handleSubseqSynonym(note, subseq, record);
			handleAnnotParent(note, annot, record);
			handleAnnotNote(note, annot, record);
			handleFunctionAnnot(note, subseq, record);
		}
		progress.completeStep();
	}

	private void handleFunctionAnnot(Note note, Subsequence subseq, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getFunctionTagPatterns(type, source);
		ArrayList<String> methodNames = GFF3FileConfig.getFunctionMethodNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				Function function = factory.getFunction(note.getValue(), null);
				AnnotationMethod annotationMethod = factory.getAnnotationMethod(methodNames.get(i));
				subseq.addFunctionAnnotation(annotationMethod, function);
			}
		}
	}

	private void handleAnnotParent(Note note, SubseqAnnotation annot, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getParentAccessionTagPatterns(type, source);
		ArrayList<String> dbNames = GFF3FileConfig.getParentDbNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				Collection<Annotation> parents = organism.getAnnotations(null, null, factory.getDbxref(dbNames.get(i),
					note.getValue()));
				for (Annotation parent : parents) {
					annot.addParent(parent);
				}
			}
		}
	}

	private void handleAnnotNote(Note note, SubseqAnnotation annot, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getNoteTypeTagPatterns(type, source);
		ArrayList<String> typeValues = GFF3FileConfig.getNoteTypeValues(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				annot.addNote(typeValues.get(i), note.getValue());
			}
		}
	}

	private void handleAnnotSynonym(Note note, SubseqAnnotation annot, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getCompletSynonymTagPatterns(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				ArrayList<String> separators = GFF3FileConfig.getCompletSynonymSeparators(type, source);
				String[] strs = note.getValue().split(separators.get(i));
				if (strs.length == 2) {
					annot.addSynonym(strs[0], strs[1]);
					return;
				}
			}
		}
		patterns = GFF3FileConfig.getAnnotationSynonymAccessionTagPatterns(type, source);
		ArrayList<String> dbNames = GFF3FileConfig.getAnnotationSynonymDbNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				annot.addSynonym(dbNames.get(i), note.getValue());
			}
		}
	}

	private void handleSubseqSynonym(Note note, Subsequence subseq, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getSubsequenceSynonymAccessionTagPatterns(type, source);
		ArrayList<String> dbNames = GFF3FileConfig.getSubsequenceSynonymDbNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				subseq.addSynonym(dbNames.get(i), note.getValue());
			}
		}
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
