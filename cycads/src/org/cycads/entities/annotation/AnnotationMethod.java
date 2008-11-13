/*
 * Created on 11/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteHolder;

public interface AnnotationMethod<S extends AnnotationSource, T extends AnnotationTarget> extends
		NoteHolder<AnnotationMethod<S, T>>
{
	public String getName();

	public Annotation<S, T> getOrCreateAnnotation(S source, T target);

}