/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteHolder;

public interface Annotation<A extends Annotation<A>> extends NoteHolder<Annotation<A>>
{
	public AnnotationMethod getAnnotationMethod();

	public AnnotationSource<A> getSource();

	public void setSource(AnnotationSource<A> source);

}