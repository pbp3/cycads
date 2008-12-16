/*
 * Created on 15/12/2008
 */
package org.cycads.entities.annotation.feature;

import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class SimpleFeatureBJ extends AnnotationRichFeatureBJ<SimpleFeatureBJ, SimpleFeatureBJ, SimpleFeatureBJ>
		implements Feature<SimpleFeatureBJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ>
{

	public SimpleFeatureBJ(int featureId) {
		super(featureId);
	}

	public SimpleFeatureBJ(LocationBJ location) {
		super(location);
	}

	public SimpleFeatureBJ(RichFeature feature) {
		super(feature);
	}

	public SimpleFeatureBJ(RichLocation location) {
		super(location);
	}

}
