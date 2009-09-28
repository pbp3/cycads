/*
 * Created on 05/01/2009
 */
package org.cycads.parser.gff3;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.annotation.SubseqFunctionAnnotation;
import org.cycads.entities.factory.EntityFactory;
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

public class FirstSequencesGFF3Handler implements GFF3DocumentHandler
{
	PrintStream			out;
	int					i;
	ArrayList<String>	seqIds;

	public FirstSequencesGFF3Handler(PrintStream out, int i) {
		this.out = out;
		this.i = i;
		seqIds = new ArrayList<String>(i);
	}

	@Override
	public void commentLine(String comment) {

	}

	@Override
	public void startDocument() {
	}

	@Override
	public void recordLine(GFF3Record record) {
		if (seqIds.size() < i && GFF3FileConfig.isGene(record.getType(), record.getSource())
			&& !seqIds.contains(record.getSequenceID())) {
			seqIds.add(record.getSequenceID());
			out.println(record);
		}
		else if (isSeqValid(record.getSequenceID())) {
			out.println(record);
		}
	}

	@Override
	public void endDocument() {
	}

	private boolean isSeqValid(String sequenceID) {
		for (String seqId : seqIds) {
			if (seqId.equals(sequenceID)) {
				return true;
			}
		}
		return false;
	}

}
