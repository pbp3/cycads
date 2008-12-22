/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesSourceContainer;

public interface Annotation<ANNOTATION_TYPE extends Annotation< ? , ? , ? >, SourceType, M extends AnnotationMethod>
		extends NotesSourceContainer<Note<ANNOTATION_TYPE>>
{

	public M getAnnotationMethod();

	public SourceType getSource();

	public Note<ANNOTATION_TYPE> addNote(String value, String type);

}