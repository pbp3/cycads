/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

import java.util.ArrayList;
import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.biojavax.ontology.ComparableTerm;

public class MiscRNARecord extends SimpleGeneRecord
{

	public MiscRNARecord(RichFeature miscRNA) throws Exception {
		super(miscRNA);
	}

	@Override
	public String getType() {
		return "MISC-RNA";
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
