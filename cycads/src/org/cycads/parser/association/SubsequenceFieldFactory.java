/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class SubsequenceFieldFactory implements IndependentObjectFactory<Collection<Subsequence>>
{
	Organism					organism;
	IndependentDbxrefFactory	independentDbxrefFactory;

	public SubsequenceFieldFactory(Organism organism, IndependentDbxrefFactory independentDbxrefFactory) {
		this.organism = organism;
		this.independentDbxrefFactory = independentDbxrefFactory;
	}

	//Search by synonym and annotation synonym
	@Override
	public Collection<Subsequence> create(String value) throws ParserException {
		Dbxref dbxref = independentDbxrefFactory.create(value);
		Collection<Subsequence> ret = organism.getSubsequences(dbxref);
		if (ret.isEmpty()) {
			Collection<SubseqAnnotation> annots = organism.getDbxrefAnnotations(value);
			ret = new ArrayList<Subsequence>();
			for (SubseqAnnotation annot : annots) {
				ret.add(annot.getSubsequence());
			}
		}
		return ret;
		//		throw new ParserException(Messages.subsequenceNotExists(value));
	}

}
