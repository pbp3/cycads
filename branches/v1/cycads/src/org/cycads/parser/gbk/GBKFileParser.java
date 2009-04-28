/*
 * Created on 24/04/2009
 */
package org.cycads.parser.gbk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.biojava.bio.BioException;
import org.biojavax.Comment;
import org.biojavax.RichObjectFactory;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;
import org.cycads.entities.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class GBKFileParser {

	EntityFactory		factory;
	private String		seqDBName;
	private Progress	progress;

	private Type		cdsAnnotationType;
	private Type		mrnaAnnotationType;
	private Type		geneAnnotationType;

	public GBKFileParser(EntityFactory factory, String seqDBName, Progress progress) {
		this.factory = factory;
		this.seqDBName = seqDBName;
		this.progress = progress;
		cdsAnnotationType = factory.getAnnotationType(ParametersDefault.getCDSAnnotationTypeName());
		mrnaAnnotationType = factory.getAnnotationType(ParametersDefault.getMRNAAnnotationTypeName());
		geneAnnotationType = factory.getAnnotationType(ParametersDefault.getGeneAnnotationTypeName());
	}

	public void parse(File f) throws IOException {
		parse(new BufferedReader(new FileReader(f)));
	}

	public void parse(String fileName) throws IOException {
		parse(new BufferedReader(new FileReader(fileName)));
	}

	public void parse(BufferedReader br) throws IOException {
		Sequence sequence;
		RichSequenceIterator seqs = RichSequence.IOTools.readGenbankDNA(br, RichObjectFactory.getDefaultNamespace());
		while (seqs.hasNext()) {
			RichSequence richSeq;
			try {
				richSeq = seqs.nextRichSequence();
				sequence = getSequence(richSeq);
				sequence.createSubsequence(start, end, introns);
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
}
