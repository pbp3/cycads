/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.BJ;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.annotation.RNA;
import org.cycads.entities.sequence.BJ.SubsequenceBJ;

public class RNABJ extends AnnotationRichFeatureBJ<RNABJ, CDSBJ, GeneBJ> implements
		RNA<RNABJ, SubsequenceBJ, AnnotationMethodBJ> {
	public RNABJ(RichFeature feature) {
		super(feature);
		if (!isRNA(feature)) {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	public static boolean isRNA(RichFeature feature, String type) {
		return isRNA(feature) && feature.getType().equals(type);
	}

	public static boolean isRNA(RichFeature feature) {
		return isAnnotation(feature) && AnnotFeature.RNA_EXPRESSION.matcher(feature.getType()).matches();
	}

	public static boolean isMRNA(RichFeature feature) {
		return isRNA(feature, AnnotFeature.MRNA_TYPE);
	}

	// @Override
	// public Collection<CDSBJ> getCDSProducts() {
	// return getFeaturesContains(this);
	// }
	//
	@Override
	public SubsequenceBJ getParent() {
		return getSubsequenceParent();
	}

	// @Override
	// public Collection<GeneBJ> getGenesContains() {
	// return getFeaturesContainers(this);
	// }
	//
	@Override
	public void setParent(SubsequenceBJ geneSubseq) {
		if (geneSubseq.getGenes().isEmpty()) {
			throw new IllegalArgumentException();
		}
		setSubsequenceParent(geneSubseq);
	}

	// public void addCDS(CDSBJ cds) {
	// addRichFeature(cds.getRichFeature());
	// }
	//
	// @Override
	// public GeneBJ createObjectContainer(RichFeature feature) {
	// return new GeneBJ(feature);
	// }
	//
	// @Override
	// public CDSBJ createObjectContains(RichFeature feature) {
	// return new CDSBJ(feature);
	// }
	//
	// @Override
	// public boolean isObjectContainer(RichFeature feature) {
	// return GeneBJ.isGene(feature);
	// }
	//
	// @Override
	// public boolean isObjectContains(RichFeature feature) {
	// return CDSBJ.isCDS(feature);
	// }
	//
	@Override
	public boolean isMRNA() {
		return getType().equals(AnnotFeature.MRNA_TYPE);
	}

}
