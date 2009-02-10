/*
 * Created on 15/12/2008
 */
package org.cycads.entities.annotation.BJ;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.sequence.BJ.SubsequenceBJ;

public class SimpleFeatureBJ extends AnnotationRichFeatureBJ<SimpleFeatureBJ, SimpleFeatureBJ, SimpleFeatureBJ>
		implements AnnotFeature<SimpleFeatureBJ, SubsequenceBJ, AnnotationMethodBJ>
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
