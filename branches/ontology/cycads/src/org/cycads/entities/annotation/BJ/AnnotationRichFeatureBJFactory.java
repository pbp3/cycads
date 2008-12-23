/*
 * Created on 15/12/2008
 */
package org.cycads.entities.annotation.BJ;

import org.biojavax.bio.seq.RichFeature;

public interface AnnotationRichFeatureBJFactory<ANNOTATION_TYPE_CONTAINS extends AnnotationRichFeatureBJ< ? , ? , ? >, ANNOTATION_TYPE_CONTAINER extends AnnotationRichFeatureBJ< ? , ? , ? >>
{
	public ANNOTATION_TYPE_CONTAINS createObjectContains(RichFeature feature);

	public ANNOTATION_TYPE_CONTAINER createObjectContainer(RichFeature feature);

	public boolean isObjectContainer(RichFeature feature);

	public boolean isObjectContains(RichFeature feature);

}
