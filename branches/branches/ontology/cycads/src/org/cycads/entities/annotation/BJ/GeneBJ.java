/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.BJ;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.annotation.Gene;
import org.cycads.entities.sequence.BJ.SubsequenceBJ;

public class GeneBJ extends AnnotationRichFeatureBJ<GeneBJ, RNABJ, SimpleFeatureBJ> implements
		Gene<GeneBJ, SubsequenceBJ, AnnotationMethodBJ> {

	public GeneBJ(RichFeature feature) {
		super(feature);
		if (!isGene(feature)) {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	public static boolean isGene(RichFeature feature) {
		return isAnnotation(feature) && feature.getType().equals(AnnotFeature.GENE_TYPE);
	}

	// @Override
	// public Collection<RNABJ> getRNAProducts() {
	// return getFeaturesContains(this);
	// }
	//
	// public void addRNA(RNABJ rna) {
	// addRichFeature(rna.getRichFeature());
	// }
	//
	// @Override
	// public SimpleFeatureBJ createObjectContainer(RichFeature feature) {
	// return new SimpleFeatureBJ(feature);
	// }
	//
	// @Override
	// public RNABJ createObjectContains(RichFeature feature) {
	// return new RNABJ(feature);
	// }
	//
	// @Override
	// public boolean isObjectContainer(RichFeature feature) {
	// return SimpleFeatureBJ.isSimpleFeature(feature);
	// }
	//
	// @Override
	// public boolean isObjectContains(RichFeature feature) {
	// return RNABJ.isRNA(feature);
	// }
}
