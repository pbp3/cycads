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

public class RNABJ extends FeatureBJ<RNABJ>
		implements RNA<RNABJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, GeneBJ, CDSBJ>
{
	GeneBJ				gene;
	Collection<CDSBJ>	CDSs	= new ArrayList<CDSBJ>();

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
		ArrayList<GeneBJ> genes = new ArrayList<GeneBJ>();
		Collection<RichFeature> features = BioSql.getFeatureContains(getRichFeature());
		for (RichFeature feature : features) {
			genes.add(new GeneBJ(feature));
		}
		if (genes.size() > 0 && getGeneParent() == null) {
			setGeneParent(genes.get(0));
		}
		return genes;
	}

	@Override
	public void setGeneParent(GeneBJ gene) {
		// TODO Auto-generated method stub

	}

}
