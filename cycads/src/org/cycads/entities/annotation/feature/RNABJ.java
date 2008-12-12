/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class RNABJ extends FeatureBJ<RNABJ>
		implements RNA<RNABJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, GeneBJ, CDSBJ>
{

	public RNABJ(RichFeature feature) {
		super(feature);
	}

	@Override
	public Collection<CDSBJ> getCDSProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneBJ getGeneParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<GeneBJ> getGenesContains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeneParent(GeneBJ gene) {
		// TODO Auto-generated method stub

	}

}
