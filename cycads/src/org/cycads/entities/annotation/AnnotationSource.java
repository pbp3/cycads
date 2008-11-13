/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface AnnotationSource extends AnnotationRecord
{
	public Collection<Annotation< ? extends AnnotationSource, ? extends AnnotationTarget>> getAnnotationsAsSource(
			AnnotationFilter filter);

	public <S extends AnnotationSource, T extends AnnotationTarget> Annotation<S, T> getOrCreateAnnotation(T target,
			AnnotationMethod<S, T> method);

}
