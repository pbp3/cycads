/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJFactory;
import org.cycads.entities.sequence.SubsequenceBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class GeneBJ extends AnnotationRichFeatureBJ<GeneBJ, RNABJ, SimpleFeatureBJ>
		implements Gene<GeneBJ, SubsequenceBJ, ThinSequenceBJ, AnnotationMethodBJ, RNABJ>,
		AnnotationRichFeatureBJFactory<RNABJ, SimpleFeatureBJ>
{

	public GeneBJ(RichFeature feature) {
		super(feature);
		if (!isGene(feature)) {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	public static boolean isGene(RichFeature feature) {
		return isAnnotation(feature) && feature.getType().equals(Feature.GENE_TYPE);
	}

	@Override
	public Collection<RNABJ> getRNAProducts() {
		return getFeaturesContains(this);
	}

	public void addRNA(RNABJ rna) {
		addRichFeature(rna.getRichFeature());
	}

	@Override
	public SimpleFeatureBJ createObjectContainer(RichFeature feature) {
		return new SimpleFeatureBJ(feature);
	}

	@Override
	public RNABJ createObjectContains(RichFeature feature) {
		return new RNABJ(feature);
	}

	@Override
	public boolean isObjectContainer(RichFeature feature) {
		return SimpleFeatureBJ.isSimpleFeature(feature);
	}

	@Override
	public boolean isObjectContains(RichFeature feature) {
		return RNABJ.isRNA(feature);
	}
}
