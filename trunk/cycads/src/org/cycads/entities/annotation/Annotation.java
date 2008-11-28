/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;
import org.cycads.entities.note.NoteSource;

public interface Annotation<SourceType> extends NoteSource, NotesContainer<Note< ? extends Annotation<SourceType>>>
{
	public AnnotationMethod getAnnotationMethod();

	public SourceType getSource();

}