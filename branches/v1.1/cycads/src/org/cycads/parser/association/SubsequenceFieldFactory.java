/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Messages;
import org.cycads.parser.FileParserError;

public class SubsequenceFieldFactory implements FieldFactory<Subsequence>
{
	Organism		organism;
	DbxrefFieldFactory	dbxrefFieldFactory;

	public SubsequenceFieldFactory(Organism organism, DbxrefFieldFactory dbxrefFieldFactory) {
		this.organism = organism;
		this.dbxrefFieldFactory = dbxrefFieldFactory;
	}

	@Override
	public Subsequence create(String value) throws FileParserError {
		Dbxref dbxref = dbxrefFieldFactory.create(value);
		Collection<Subsequence> subsequences = organism.getSubsequences(dbxref);
		Subsequence ret;
		if (!subsequences.isEmpty()) {
			return subsequences.iterator().next();
		}
		Collection<SubseqAnnotation> annots = organism.getDbxrefAnnotations(value);
		for (SubseqAnnotation annot : annots) {
			return ((SubseqAnnotation) annot).getSubsequence();
		}
		throw new FileParserError(Messages.subsequenceNotExists(value));
	}

}
