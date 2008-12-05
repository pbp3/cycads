/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

public interface Annotation<SourceType, M extends AnnotationMethod> extends NoteSource,
		NotesContainer<Note< ? extends Annotation<SourceType, M>>>
{

	public M getAnnotationMethod();

	public SourceType getSource();

}