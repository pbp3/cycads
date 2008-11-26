/*
 * Created on 11/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteCollection;
import org.cycads.entities.note.NoteSource;

public interface AnnotationMethod extends NoteSource, NoteCollection<Note<AnnotationMethod>>
{
	public String getName();

	public int getWeight();

}