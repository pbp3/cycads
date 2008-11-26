/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteCollection;
import org.cycads.entities.note.NoteSource;

public interface Annotation<SourceType> extends NoteSource, NoteCollection<Note<Annotation<SourceType>>>
{
	public AnnotationMethod getAnnotationMethod();

	public SourceType getSource();

}