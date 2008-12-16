/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJFactory;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class CDSBJ extends AnnotationRichFeatureBJ<CDSBJ, SimpleFeatureBJ, RNABJ>
		implements CDS<CDSBJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, RNABJ>,
		AnnotationRichFeatureBJFactory<SimpleFeatureBJ, RNABJ>
{

	public CDSBJ(RichFeature feature) {
		super(feature);
	}

	@Override
	public RNABJ getRNAParent() {
		Collection<RNABJ> rnas = getRNAsContains();
		if (rnas.size() > 0) {
			return rnas.iterator().next();
		}
		return null;
	}

	@Override
	public void setRNAParent(RNABJ rnaParent) {
		if (rnaParent == null) {
			throw new IllegalArgumentException();
		}
		Collection<RNABJ> rnas = getRNAsContains();
		if (!rnas.contains(rnaParent)) {
			rnaParent.addCDS(this);
		}
	}

	@Override
	public Collection<RNABJ> getRNAsContains() {
		return getFeaturesContainers(this);
	}

	@Override
	public RNABJ createObjectContainer(RichFeature feature) {
		return new RNABJ(feature);
	}

	@Override
	public SimpleFeatureBJ createObjectContains(RichFeature feature) {
		return new SimpleFeatureBJ(feature);
	}

}
