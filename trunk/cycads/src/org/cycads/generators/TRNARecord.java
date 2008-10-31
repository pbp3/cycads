/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

import java.util.ArrayList;
import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.biojavax.ontology.ComparableTerm;

public class TRNARecord extends SimpleGeneRecord
{

	public TRNARecord(RichFeature tRNA) throws Exception {
		super(tRNA);
	}

	@Override
	public String getType() {
		return "TRNA";
	}

	@Override
	public String getProductIdTerm() {
		return "transcript_id";
	}

	@Override
	public Collection<ComparableTerm> getMethodsToLinkToKO() {
		return new ArrayList<ComparableTerm>();
	}

}
