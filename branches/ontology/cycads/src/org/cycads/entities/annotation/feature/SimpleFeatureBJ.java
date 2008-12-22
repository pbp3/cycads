/*
 * Created on 15/12/2008
 */
package org.cycads.entities.annotation.feature;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJ;
import org.cycads.entities.sequence.SubsequenceBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;

public class SimpleFeatureBJ extends AnnotationRichFeatureBJ<SimpleFeatureBJ, SimpleFeatureBJ, SimpleFeatureBJ>
		implements Feature<SimpleFeatureBJ, SubsequenceBJ, ThinSequenceBJ, AnnotationMethodBJ>
{

	public SimpleFeatureBJ(int featureId) {
		super(featureId);
	}

	public SimpleFeatureBJ(RichFeature feature) {
		super(feature);
	}

	public static boolean isSimpleFeature(RichFeature feature) {
		return isAnnotation(feature);
	}

}
