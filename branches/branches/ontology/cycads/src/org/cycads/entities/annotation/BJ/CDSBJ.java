/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.BJ;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.annotation.CDS;
import org.cycads.entities.sequence.BJ.SubsequenceBJ;

public class CDSBJ extends AnnotationRichFeatureBJ<CDSBJ, SimpleFeatureBJ, RNABJ> implements
		CDS<CDSBJ, SubsequenceBJ, AnnotationMethodBJ> {

	public CDSBJ(RichFeature feature) {
		super(feature);
		if (!isCDS(feature)) {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	public static boolean isCDS(RichFeature feature) {
		return isAnnotation(feature) && feature.getType().equals(AnnotFeature.CDS_TYPE);
	}

	@Override
	public SubsequenceBJ getParent() {
		return getSubsequenceParent();
	}

	@Override
	public void setParent(SubsequenceBJ rnaParent) {
		if (rnaParent.getRNAs().isEmpty()) {
			throw new IllegalArgumentException();
		}
		setSubsequenceParent(rnaParent);
	}

	// @Override
	// public Collection<RNABJ> getRNAsContains() {
	// return getFeaturesContainers(this);
	// }
	//
	// @Override
	// public RNABJ createObjectContainer(RichFeature feature) {
	// return new RNABJ(feature);
	// }
	//
	// @Override
	// public SimpleFeatureBJ createObjectContains(RichFeature feature) {
	// return new SimpleFeatureBJ(feature);
	// }
	//
	// @Override
	// public boolean isObjectContainer(RichFeature feature) {
	// return RNABJ.isRNA(feature);
	// }
	//
	// @Override
	// public boolean isObjectContains(RichFeature feature) {
	// return SimpleFeatureBJ.isSimpleFeature(feature);
	// }

}
