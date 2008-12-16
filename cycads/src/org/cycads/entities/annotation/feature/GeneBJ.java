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

public class GeneBJ extends AnnotationRichFeatureBJ<GeneBJ, RNABJ, SimpleFeatureBJ>
		implements Gene<GeneBJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, RNABJ>,
		AnnotationRichFeatureBJFactory<RNABJ, SimpleFeatureBJ>
{

	public GeneBJ(RichFeature feature) {
		super(feature);
	}

	@Override
	public Collection<RNABJ> getRNAProducts() {
		return getFeaturesContains(this);
	}

	public void addRNA(RNABJ rna) {
		addRichFeature(rna);
	}

	@Override
	public SimpleFeatureBJ createObjectContainer(RichFeature feature) {
		return new SimpleFeatureBJ(feature);
	}

	@Override
	public RNABJ createObjectContains(RichFeature feature) {
		return new RNABJ(feature);
	}
}
