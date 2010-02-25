/*
 * Created on 05/01/2009
 */
package org.cycads.parser.gff3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.Feature;
import org.cycads.entities.Function;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.factory.SimpleAnnotationFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.SimpleSubsequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class GeneralGFF3Handler implements GFF3DocumentHandler
{
	private final EntityFactory												factory;
	private final Organism													organism;
	private final String													seqDBName;
	private final String													seqVersion;
	private final Progress													progress;

	private final Feature													cdsFeature;
	private final Feature													mrnaFeature;
	private final Feature													geneFeature;
	private final Collection<Type>											functionalTypes;

	Hashtable<String, ArrayList<Annotation<SimpleSubsequence, Feature>>>	mrnasHash;
	Hashtable<String, ArrayList<GFF3Record>>								cdss;
	ArrayList<Annotation<SimpleSubsequence, Feature>>						mrnas;

	public GeneralGFF3Handler(EntityFactory factory, Organism organism, String seqDBName, String seqVersion,
			Progress progress) {
		this.factory = factory;
		this.organism = organism;
		this.seqDBName = seqDBName;
		this.progress = progress;
		this.seqVersion = seqVersion;
		cdsFeature = factory.getFeature(ParametersDefault.getCDSAnnotationTypeName());
		mrnaFeature = factory.getFeature(ParametersDefault.getMRNAAnnotationTypeName());
		geneFeature = factory.getFeature(ParametersDefault.getGeneAnnotationTypeName());
		functionalTypes = new ArrayList<Type>(1);
		functionalTypes.add(factory.getType(ParametersDefault.getFunctionalAnnotationTypeName()));
	}

	@Override
	public void commentLine(String comment) {

	}

	@Override
	public void startDocument() {
		cdss = new Hashtable<String, ArrayList<GFF3Record>>();
		mrnas = new ArrayList<Annotation<SimpleSubsequence, Feature>>();
		mrnasHash = new Hashtable<String, ArrayList<Annotation<SimpleSubsequence, Feature>>>();
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
			if (GFF3FileConfig.matches(note.getType().getName(), removePatterns)) {
				record.removeNote(note);
			}
			else {
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
		double score = record.getScore();
		String scoreStr = null;
		if (score != GFF3Record.NO_SCORE) {
			scoreStr = NumberFormat.getInstance().format(score);
		}
		Annotation<Subsequence, Feature> annot = (Annotation<Subsequence, Feature>) subseq.addAnnotation(geneFeature,
			annotationMethod, scoreStr, functionalTypes);
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
			subseq = new SimpleSubsequence(seq, record.getEnd(), record.getStart(), factory, factory,
				new SimpleAnnotationFactory(factory, factory));
			subseq.addIntron(new SimpleIntron(record.getEnd(), record.getStart()));
		}
		else {
			subseq = new SimpleSubsequence(seq, record.getStart(), record.getEnd(), factory, factory,
				new SimpleAnnotationFactory(factory, factory));
			subseq.addIntron(new SimpleIntron(record.getStart(), record.getEnd()));
		}
		AnnotationMethod annotationMethod = factory.getAnnotationMethod(GFF3FileConfig.getAnnotationMethod(record));
		double score = record.getScore();
		String scoreStr = null;
		if (score != GFF3Record.NO_SCORE) {
			scoreStr = NumberFormat.getInstance().format(score);
		}
		Annotation<SimpleSubsequence, Feature> annot = (Annotation<SimpleSubsequence, Feature>) subseq.addAnnotation(
			mrnaFeature, annotationMethod, scoreStr, functionalTypes);

		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			handleAnnotSynonym(note, annot, record);
			handleSubseqSynonym(note, subseq, record);
			handleAnnotParent(note, annot, record);
			handleAnnotNote(note, annot, record);
			handleFunctionAnnot(note, subseq, record);
		}
		Collection< ? extends Dbxref> syns = annot.getSynonyms();
		for (Dbxref syn : syns) {
			ArrayList<Annotation<SimpleSubsequence, Feature>> annots = mrnasHash.get(syn.toString());
			if (annots == null) {
				annots = new ArrayList<Annotation<SimpleSubsequence, Feature>>();
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
				Collection<Annotation<SimpleSubsequence, Feature>> annotsParent = mrnasHash.get(parentDbxref.toString());
				if (annotsParent != null && !annotsParent.isEmpty()) {
					for (Annotation<SimpleSubsequence, Feature> annotParent : annotsParent) {
						annotParent.getSource().addExon(record.getStart(), record.getEnd());
					}
				}
			}
		}
	}

	private void handleCDS(GFF3Record record) {
		ArrayList<Dbxref> synonyms = getSynonyms(record);
		ArrayList<GFF3Record> cdsRecords = null;
		for (Dbxref dbxref : synonyms) {
			cdsRecords = cdss.get(dbxref.toString());
			if (cdsRecords != null) {
				break;
			}
		}
		if (cdsRecords == null) {
			cdsRecords = new ArrayList<GFF3Record>(1);
		}
		cdsRecords.add(record);
		for (Dbxref dbxref : synonyms) {
			cdss.put(dbxref.toString(), cdsRecords);
		}
	}

	private void handleFunctionAnnot(Note note, Subsequence subseq, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getFunctionTagPatterns(type, source);
		ArrayList<String> methodNames = GFF3FileConfig.getFunctionMethodNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				Function function = factory.getFunction(note.getValue());
				AnnotationMethod annotationMethod = factory.getAnnotationMethod(methodNames.get(i));
				subseq.addAnnotation(function, annotationMethod, null, functionalTypes);
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

	private void handleAnnotParent(Note note, Annotation< ? extends Subsequence, Feature> annot, GFF3Record record) {
		String type = record.getType();
		String source = record.getSource();
		ArrayList<Dbxref> parentsDbxref = getParentDbxrefs(note, type, source);
		for (Dbxref parentDbxref : parentsDbxref) {
			Collection<Annotation> parents = factory.getAnnotationsBySynonym(parentDbxref);
			for (Annotation parent : parents) {
				annot.addParent(parent);
			}
		}
	}

	private ArrayList<Dbxref> getParentDbxrefs(Note note, String type, String source) {
		ArrayList<Dbxref> ret = new ArrayList<Dbxref>(1);
		String accession = note.getValue();
		if (accession != null && accession.length() > 0) {
			ArrayList<Pattern> patterns = GFF3FileConfig.getParentAccessionTagPatterns(type, source);
			ArrayList<String> dbNames = GFF3FileConfig.getParentDbNames(type, source);
			for (int i = 0; i < patterns.size(); i++) {
				if (patterns.get(i).matcher(note.getType().getName()).matches()) {
					ret.add(factory.getDbxref(dbNames.get(i), note.getValue()));
				}
			}
		}
		return ret;
	}

	private void handleAnnotNote(Note note, Annotation< ? extends Subsequence, Feature> annot, GFF3Record record) {
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

	private void handleAnnotSynonym(Note note, Annotation< ? extends Subsequence, Feature> annot, GFF3Record record) {
		ArrayList<Dbxref> synonyms = getSynonyms(note, record.getType(), record.getSource());
		for (Dbxref dbxref : synonyms) {
			annot.addSynonym(dbxref);
		}
	}

	private ArrayList<Dbxref> getSynonyms(Note note, String type, String source) {
		ArrayList<Dbxref> synonyms = new ArrayList<Dbxref>(1);
		String noteType = note.getType().getName();
		ArrayList<Pattern> patterns = GFF3FileConfig.getCompletSynonymTagPatterns(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				ArrayList<String> separators = GFF3FileConfig.getCompletSynonymSeparators(type, source);
				String[] strs = note.getValue().split(separators.get(i));
				if (strs.length == 2) {
					synonyms.add(factory.getDbxref(strs[0], strs[1]));
					return synonyms;
				}
			}
		}
		patterns = GFF3FileConfig.getAnnotationSynonymAccessionTagPatterns(type, source);
		ArrayList<String> dbNames = GFF3FileConfig.getAnnotationSynonymDbNames(type, source);
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(noteType).matches()) {
				synonyms.add(factory.getDbxref(dbNames.get(i), note.getValue()));
			}
		}
		return synonyms;
	}

	private ArrayList<Dbxref> getSynonyms(GFF3Record record) {
		ArrayList<Dbxref> synonyms = new ArrayList<Dbxref>(1);
		String type = record.getType();
		String source = record.getSource();
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			synonyms.addAll(getSynonyms(note, type, source));
		}
		return synonyms;
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

	@Override
	public void endDocument() {
		persistMrnas();
		for (ArrayList<GFF3Record> records : cdss.values()) {
			handleCDS2(records);
		}
	}

	private void persistMrnas() {
		for (Annotation<SimpleSubsequence, Feature> mrna : mrnas) {
			SimpleSubsequence subseq = mrna.getSource();
			Subsequence subseqPersisted = subseq.getSequence().getSubsequence(subseq.getStart(), subseq.getEnd(),
				subseq.getIntrons());
			if (subseqPersisted == null) {
				subseqPersisted = subseq.getSequence().createSubsequence(subseq.getStart(), subseq.getEnd(),
					subseq.getIntrons());
			}
			for (Note note : subseq.getNotes()) {
				subseqPersisted.addNote(note.getType(), note.getValue());
			}
			for (Dbxref synonym : subseq.getSynonyms()) {
				subseqPersisted.addSynonym(synonym);
			}
			Collection< ? extends Annotation< ? , ? >> annots = subseq.getAnnotations(null, null, null);
			for (Annotation< ? , ? > annot : annots) {
				Annotation< ? , ? > annotPersisted = subseqPersisted.addAnnotation(annot.getTarget(),
					annot.getAnnotationMethod(), annot.getScore(), (Collection<Type>) annot.getTypes());
				for (Note note : annot.getNotes()) {
					annotPersisted.addNote(note.getType(), note.getValue());
				}
				for (BasicEntity parent : annot.getParents()) {
					annotPersisted.addParent(parent);
				}
				for (Dbxref synonym : annot.getSynonyms()) {
					annotPersisted.addSynonym(synonym);
				}
			}
		}
	}

	private void handleCDS2(ArrayList<GFF3Record> records) {
		GFF3Record record = records.get(0);
		Sequence seq = getSequence(record.getSequenceID());

		SimpleSubsequence simpleSubsequence;
		if (record.getStrand() < 0) {
			simpleSubsequence = new SimpleSubsequence(seq, record.getEnd(), record.getStart(), factory, factory,
				factory);
			//			subseq = seq.getSubsequence(record.getEnd(), record.getStart(), intronsParent);
			//			if (subseq == null) {
			//				subseq = seq.createSubsequence(record.getEnd(), record.getStart(), intronsParent);
			//			}
		}
		else {
			simpleSubsequence = new SimpleSubsequence(seq, record.getStart(), record.getEnd(), factory, factory,
				factory);
			//			subseq = seq.getSubsequence(record.getStart(), record.getEnd(), intronsParent);
			//			if (subseq == null) {
			//				subseq = seq.createSubsequence(record.getStart(), record.getEnd(), intronsParent);
			//			}
		}
		Collection<Intron> intronsParent = getIntronsParent(record);
		simpleSubsequence.addIntrons(SimpleIntron.getIntrons(intronsParent, record.getStart(), record.getEnd()));

		for (int i = 1; i < records.size(); i++) {
			record = records.get(i);
			simpleSubsequence.addExon(record.getStart(), record.getEnd());
			intronsParent = getIntronsParent(record);
			//getIntrons intersection
			simpleSubsequence.addIntrons(SimpleIntron.getIntrons(intronsParent, record.getStart(), record.getEnd()));
		}

		Subsequence subseq = seq.getSubsequence(simpleSubsequence.getStart(), simpleSubsequence.getEnd(),
			simpleSubsequence.getIntrons());
		if (subseq == null) {
			subseq = seq.createSubsequence(simpleSubsequence.getStart(), simpleSubsequence.getEnd(), intronsParent);
		}

		AnnotationMethod annotationMethod = factory.getAnnotationMethod(GFF3FileConfig.getAnnotationMethod(record));
		double score = record.getScore();
		String scoreStr = null;
		if (score != GFF3Record.NO_SCORE) {
			scoreStr = NumberFormat.getInstance().format(score);
		}
		Annotation<Subsequence, Feature> annot = (Annotation<Subsequence, Feature>) subseq.addAnnotation(cdsFeature,
			annotationMethod, scoreStr, functionalTypes);

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

	private Collection<Intron> getIntronsParent(GFF3Record record) {
		Collection<Intron> intronsParent = new ArrayList<Intron>();
		Collection<Note> notes = record.getNotes();
		for (Note note : notes) {
			ArrayList<Dbxref> parentDbxrefs = getParentDbxrefs(note, record.getType(), record.getSource());
			for (Dbxref parentDbxref : parentDbxrefs) {
				Collection<Annotation< ? , ? >> annotsParent = factory.getAnnotationsBySynonym(parentDbxref);
				for (Annotation< ? , ? > parent : annotsParent) {
					if (parent.getTarget() instanceof Feature && parent.getTarget().equals(mrnaFeature)
						&& parent.getSource() instanceof Subsequence) {
						intronsParent = ((Subsequence) parent.getSource()).getIntrons();
					}
				}
			}
		}
		return intronsParent;
	}
}
