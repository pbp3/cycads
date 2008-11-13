/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteHolder;

public interface Annotation<S extends AnnotationSource, T extends AnnotationTarget> extends
		NoteHolder<Annotation<S, T>>
{
	public AnnotationMethod<S, T> getAnnotationMethod();

	public S getSourceRecord();

	public T getTargetRecord();

}