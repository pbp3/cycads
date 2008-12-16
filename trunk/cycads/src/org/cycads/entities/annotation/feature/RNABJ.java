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

public class RNABJ extends AnnotationRichFeatureBJ<RNABJ, CDSBJ, GeneBJ>
		implements RNA<RNABJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, GeneBJ, CDSBJ>,
		AnnotationRichFeatureBJFactory<CDSBJ, GeneBJ>
{
	public RNABJ(RichFeature feature) {
		super(feature);
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
		addRichFeature(cds);
	}

	@Override
	public GeneBJ createObjectContainer(RichFeature feature) {
		return new GeneBJ(feature);
	}

	@Override
	public CDSBJ createObjectContains(RichFeature feature) {
		return new CDSBJ(feature);
	}

}
