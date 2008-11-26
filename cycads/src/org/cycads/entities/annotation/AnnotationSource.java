/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface AnnotationSource<A extends Annotation<A>>
{
	public Collection<A> getAnnotations(AnnotationFilter<A> filter);

	public void addAnnotation(A annotation);

}
