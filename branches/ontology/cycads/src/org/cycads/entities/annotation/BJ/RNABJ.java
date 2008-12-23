/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.BJ;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.annotation.RNA;
import org.cycads.entities.sequence.BJ.SubsequenceBJ;

public class RNABJ extends AnnotationRichFeatureBJ<RNABJ, CDSBJ, GeneBJ>
		implements RNA<RNABJ, SubsequenceBJ, AnnotationMethodBJ, GeneBJ, CDSBJ>,
		AnnotationRichFeatureBJFactory<CDSBJ, GeneBJ>
{
	public RNABJ(RichFeature feature) {
		super(feature);
		if (!isRNA(feature)) {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	public static boolean isRNA(RichFeature feature) {
		return isAnnotation(feature);
	}

	@Override
	public Collection<CDSBJ> getCDSProducts() {
		return getFeaturesContains(this);
	}

	@Override
	public GeneBJ getGeneParent() {
		Collection<GeneBJ> genes = getGenesContains();
		if (genes.size() > 0) {
			return genes.iterator().next();
		}
		return null;
	}

	@Override
	public Collection<GeneBJ> getGenesContains() {
		return getFeaturesContainers(this);
	}

	@Override
	public void setGeneParent(GeneBJ gene) {
		if (gene == null) {
			throw new IllegalArgumentException();
		}
		Collection<GeneBJ> genes = getGenesContains();
		if (!genes.contains(gene)) {
			gene.addRNA(this);
		}
	}

	public void addCDS(CDSBJ cds) {
		addRichFeature(cds.getRichFeature());
	}

	@Override
	public GeneBJ createObjectContainer(RichFeature feature) {
		return new GeneBJ(feature);
	}

	@Override
	public CDSBJ createObjectContains(RichFeature feature) {
		return new CDSBJ(feature);
	}

	@Override
	public boolean isObjectContainer(RichFeature feature) {
		return GeneBJ.isGene(feature);
	}

	@Override
	public boolean isObjectContains(RichFeature feature) {
		return CDSBJ.isCDS(feature);
	}

	@Override
	public boolean isMRNA() {
		return getType().equals(AnnotFeature.MRNA_TYPE);
	}

}
