/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.ArrayList;
import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;
import org.cycads.general.biojava.BioSql;

public class CDSBJ extends FeatureBJ<CDSBJ>
		implements CDS<CDSBJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, RNABJ>
{
	RNABJ	rnaParent;

	public CDSBJ(RichFeature feature) {
		super(feature);
	}

	@Override
	public RNABJ getRNAParent() {
		return rnaParent;
	}

	@Override
	public void setRNAParent(RNABJ rnaParent) {
		this.rnaParent = rnaParent;
	}

	@Override
	public Collection<RNABJ> getRNAsContains() {
		ArrayList<RNABJ> rnas = new ArrayList<RNABJ>();
		Collection<RichFeature> features = BioSql.getFeatureContains(getRichFeature());
		for (RichFeature feature : features) {
			rnas.add(new RNABJ(feature));
		}
		if (rnas.size() > 0 && getRNAParent() == null) {
			setRNAParent(rnas.get(0));
		}
		return rnas;
	}

}
