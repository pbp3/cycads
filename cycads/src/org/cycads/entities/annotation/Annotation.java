/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

public interface Annotation<ANNOTATION_TYPE extends Annotation< ? , ? , ? >, SourceType, M extends AnnotationMethod>
		extends NoteSource, NotesContainer<Note<ANNOTATION_TYPE>>
{

	public M getAnnotationMethod();

	public SourceType getSource();

}