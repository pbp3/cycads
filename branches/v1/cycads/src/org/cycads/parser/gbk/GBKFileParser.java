/*
 * Created on 24/04/2009
 */
package org.cycads.parser.gbk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.biojava.bio.BioException;
import org.biojava.bio.seq.Feature;
import org.biojava.bio.symbol.Location;
import org.biojavax.Comment;
import org.biojavax.Note;
import org.biojavax.RankedCrossRef;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleNote;
import org.biojavax.SimpleRichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;
import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.operation.NoteBiojava;
import org.cycads.parser.operation.NoteOperation;
import org.cycads.ui.progress.Progress;

public class GBKFileParser
{

	private EntityFactory	factory;
	private String			seqDBName;
	private Progress		progress;

	public GBKFileParser(EntityFactory factory, String seqDBName, Progress progress) {
		this.factory = factory;
		this.seqDBName = seqDBName;
		this.progress = progress;
	}

	public void parse(File f) throws IOException {
		parse(new BufferedReader(new FileReader(f)));
	}

	public void parse(String fileName) throws IOException {
		parse(new BufferedReader(new FileReader(fileName)));
	}

	public void parse(BufferedReader br) throws IOException {
		Sequence sequence;
		RichSequence richSeq;
		RichSequenceIterator seqs = RichSequence.IOTools.readGenbankDNA(br, RichObjectFactory.getDefaultNamespace());
		while (seqs.hasNext()) {

			try {
				richSeq = seqs.nextRichSequence();
				sequence = getSequence(richSeq);
				for (Feature feature : richSeq.getFeatureSet()) {
					String type = GBKFileConfig.getType(feature.getType());
					if (type != null && type.length() > 0) {
						feature.setType(type);
						handleFeature((RichFeature) feature, sequence);
					}
					else {
						richSeq.removeFeature(feature);
					}
				}
				String outputFileName = GBKFileConfig.getOutputFile();
				if (outputFileName != null) {
					RichSequence.IOTools.writeGenbank(new FileOutputStream(outputFileName, false), richSeq,
						RichObjectFactory.getDefaultNamespace());
				}
			}
			catch (BioException e) {
				e.printStackTrace();
				throw new IOException(e.getMessage());
			}
		}
	}

	public Sequence getSequence(RichSequence richSeq) {
		Sequence sequence;
		Organism organism = factory.getOrganism(richSeq.getTaxon().getNCBITaxID());
		if (organism == null) {
			organism = factory.createOrganism(richSeq.getTaxon().getNCBITaxID(), richSeq.getTaxon().getDisplayName());
		}
		Dbxref seqSynonym = factory.getDbxref(seqDBName, richSeq.getAccession());
		Collection<Sequence> sequences = organism.getSequences(seqSynonym);
		if (sequences != null && !sequences.isEmpty()) {
			sequence = sequences.iterator().next();
		}
		else {
			sequence = organism.createNewSequence("" + richSeq.getVersion());
			sequence.addSynonym(seqSynonym);
			sequence.setSequenceString(richSeq.seqString());
		}
		sequence.addNote(GBKFileConfig.getSeqDescriptionNoteType(), richSeq.getDescription());
		for (Comment comment : (Set<Comment>) richSeq.getComments()) {
			sequence.addNote(GBKFileConfig.getSeqCommentNoteType(), comment.getComment());
		}
		return sequence;
	}

	public void handleFeature(RichFeature feature, Sequence sequence) {
		String type = feature.getType();
		Subsequence subseq = getSubsequence(feature, sequence);
		SubseqAnnotation annot = subseq.addAnnotation(factory.getAnnotationType(type),
			factory.getAnnotationMethod(GBKFileConfig.getAnnotationMethodName(type)));

		for (Object o : feature.getRankedCrossRefs()) {
			RankedCrossRef rankedCrossRef = (RankedCrossRef) o;
			annot.addSynonym(rankedCrossRef.getCrossRef().getDbname(), rankedCrossRef.getCrossRef().getAccession());
		}

		SimpleRichAnnotation annots = ((SimpleRichAnnotation) feature.getAnnotation());
		ArrayList<Note> notes = new ArrayList<Note>(annots.getNoteSet());
		List<NoteOperation> noteOperations = GBKFileConfig.getNoteOperations(type);
		Collection<org.cycads.parser.operation.Note> newNotes = new ArrayList<org.cycads.parser.operation.Note>();
		for (int i = 0; i < notes.size(); i++) {
			Note note = notes.get(i);
			annots.removeNote(note);

			org.cycads.parser.operation.Note noteForOperation = new NoteBiojava(note);
			//transform and get new notes for each note (Operations)
			newNotes.clear();
			int operationIndex = 0;
			while (noteForOperation != null && i < noteOperations.size()) {
				noteForOperation = noteOperations.get(operationIndex++).transform(noteForOperation, newNotes);
			}

			//put new notes to analyze and to feature
			for (org.cycads.parser.operation.Note newNote : newNotes) {
				Note newBiojavaNote = new SimpleNote(RichObjectFactory.getDefaultOntology().getOrCreateTerm(
					newNote.getType()), newNote.getValue(), 0);
				notes.add(newBiojavaNote);
				annots.addNote(newBiojavaNote);
			}

			//analyse transformed note
			if (noteForOperation != null) {
				annots.addNote(note);
				// AnnotationSynonym, parent, EC and function
				boolean parentNote = false, synonymNote = false, dbxrefAnnotNote = false, functionAnnotNote = false;
				String tag = GBKFileConfig.getTag(note.getTerm().getName(), type);
				ArrayList<String> parentDBNames = GBKFileConfig.getParentDBNames(tag, type);
				ArrayList<String> synonymDBNames = GBKFileConfig.getSynonymDBNames(tag, type);
				ArrayList<String> ecMethodNames = GBKFileConfig.getECMethodNames(tag, type);
				ArrayList<String> functionMethodNames = GBKFileConfig.getFunctionMethodNames(tag, type);

				if (parentDBNames != null) {
					for (String parentDBName : parentDBNames) {
						parentNote = true;
						Dbxref parentSynonym = factory.getDbxref(parentDBName, note.getValue());
						Collection<Annotation> parents = annot.getSubsequence().getSequence().getOrganism().getAnnotations(
							null, null, parentSynonym);
						for (Annotation parent : parents) {
							annot.addParent(parent);
						}
					}
				}
				if (synonymDBNames != null) {
					for (String synonymDBName : synonymDBNames) {
						synonymNote = true;
						Dbxref synonym = factory.getDbxref(synonymDBName, note.getValue());
						annot.addSynonym(synonym);
					}
				}
				if (ecMethodNames != null) {
					for (String ecMethodName : ecMethodNames) {
						dbxrefAnnotNote = true;
						annot.getSubsequence().addDbxrefAnnotation(factory.getAnnotationMethod(ecMethodName),
							factory.getEC(note.getValue()));
					}
				}
				if (functionMethodNames != null) {
					for (String functionMethodName : functionMethodNames) {
						functionAnnotNote = true;
						annot.getSubsequence().addFunctionAnnotation(factory.getAnnotationMethod(functionMethodName),
							factory.getFunction(note.getValue(), null));
					}
				}
				// add as Note
				if (!parentNote && !synonymNote && !dbxrefAnnotNote && !functionAnnotNote) {
					annot.addNote(tag, note.getValue());
				}
			}
			progress.completeStep();
		}
	}

	public Subsequence getSubsequence(RichFeature feature, Sequence sequence) {
		int start, end;
		Location location = feature.getLocation();
		if (feature.getStrand().equals(feature.POSITIVE)) {
			start = location.getMin();
			end = location.getMax();
		}
		else {
			start = location.getMax();
			end = location.getMin();
		}
		ArrayList<Intron> introns = new ArrayList<Intron>();
		if (!location.isContiguous()) {
			Iterator it = location.blockIterator();
			Location loc1 = (Location) it.next();
			while (it.hasNext()) {
				Location loc2 = (Location) it.next();
				if (loc1.getMax() < loc2.getMin()) {
					introns.add(new SimpleIntron(loc1.getMax() + 1, loc2.getMin() - 1));
				}
				else if (loc2.getMax() < loc1.getMin()) {
					introns.add(new SimpleIntron(loc2.getMax() + 1, loc1.getMin() - 1));
				}
				loc1 = loc2;
			}
		}
		Subsequence subseq = sequence.getSubsequence(start, end, introns);
		if (subseq == null) {
			subseq = sequence.createSubsequence(start, end, introns);
		}
		return subseq;
	}
}
