/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface AnnotationTarget extends AnnotationRecord
{
	public Collection<Annotation< ? extends AnnotationSource, ? extends AnnotationTarget>> getAnnotationsAsTarget(
			AnnotationFilter filter);

}
