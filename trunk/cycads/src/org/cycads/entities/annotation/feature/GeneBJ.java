/*
 * Created on 09/12/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class GeneBJ extends FeatureBJ implements Gene<LocationBJ, ThinSequenceBJ, AnnotationMethodBJ, RNABJ>
{

	public GeneBJ(RichFeature feature) {
		super(feature);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<RNABJ> getRNAProducts() {
		// TODO Auto-generated method stub
		return null;
	}

}
