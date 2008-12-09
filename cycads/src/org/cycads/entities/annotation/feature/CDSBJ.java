/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class CDSBJ extends FeatureBJ implements CDS<LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, RNABJ>
{

	public CDSBJ(RichFeature feature) {
		super(feature);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RNABJ getRNAParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RNABJ> getRNAsContains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRNAParent(RNABJ rna) {
		// TODO Auto-generated method stub

	}

}
