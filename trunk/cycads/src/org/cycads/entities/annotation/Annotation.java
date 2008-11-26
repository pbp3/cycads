/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteSource;

public interface Annotation<SourceType> extends NoteSource
{
	public AnnotationMethod getAnnotationMethod();

	public SourceType getSource();

}