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

public class SubsequencesEntityFactoryByAnnotationSynonym implements ObjectFactory<Collection<Subsequence>>
{
	Organism							organism;
	ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;

	public SubsequencesEntityFactoryByAnnotationSynonym(Organism organism,
			ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.organism = organism;
		this.dbxrefsFactory = dbxrefsFactory;
	}

	//Search by synonym and annotation synonym
	@Override
	public Collection<Subsequence> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		Collection<Subsequence> ret = new ArrayList<Subsequence>();
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return ret;
		}
		for (Dbxref dbxref : dbxrefs) {
			Collection<SubseqAnnotation> annots = organism.getDbxrefAnnotations(null, dbxref);
			for (SubseqAnnotation annot : annots) {
				ret.add(annot.getSubsequence());
			}
		}
		return ret;
	}

}
