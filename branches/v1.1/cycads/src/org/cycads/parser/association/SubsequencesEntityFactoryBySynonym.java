/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class SubsequencesEntityFactoryBySynonym implements ObjectFactory<Collection<Subsequence>>
{
	Organism							organism;
	ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;

	public SubsequencesEntityFactoryBySynonym(Organism organism, ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.organism = organism;
		this.dbxrefsFactory = dbxrefsFactory;
	}

	//Search by synonym and annotation synonym
	@Override
	public Collection<Subsequence> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return new ArrayList<Subsequence>();
		}
		Collection<Subsequence> ret = null;
		for (Dbxref dbxref : dbxrefs) {
			if (ret == null) {
				ret = organism.getSubsequences(dbxref);
			}
			else {
				ret.addAll(organism.getSubsequences(dbxref));
			}
		}
		return ret;
	}

}
