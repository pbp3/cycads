/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteHolder;

public interface Annotation extends NoteHolder<Annotation>
{
	public AnnotationMethod getAnnotationMethod();

	public AnnotationSource getSource();

}