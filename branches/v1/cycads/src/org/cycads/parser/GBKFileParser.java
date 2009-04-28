/*
 * Created on 24/04/2009
 */
package org.cycads.parser;

import java.io.BufferedReader;
import java.io.IOException;

import org.biojava.bio.BioException;
import org.biojavax.RichObjectFactory;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;

public class GBKFileParser {

	public void parse(BufferedReader br) throws IOException, FileParserError {
		RichSequenceIterator seqs = RichSequence.IOTools.readGenbankDNA(br, RichObjectFactory.getDefaultNamespace());
		while (seqs.hasNext()) {
			RichSequence seq;
			try {
				seq = seqs.nextRichSequence();
			}
			catch (BioException e) {
				e.printStackTrace();
				throw new IOException(e.getMessage());
			}
		}
	}

}
