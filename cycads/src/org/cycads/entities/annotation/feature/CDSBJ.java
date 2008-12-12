/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

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
		// TODO Auto-generated method stub
		return null;
	}

}
