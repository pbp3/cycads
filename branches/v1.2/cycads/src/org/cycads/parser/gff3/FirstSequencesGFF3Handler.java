/*
 * Created on 05/01/2009
 */
package org.cycads.parser.gff3;

import java.io.PrintStream;
import java.util.ArrayList;

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
