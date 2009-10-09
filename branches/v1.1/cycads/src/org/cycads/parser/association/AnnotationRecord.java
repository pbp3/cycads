/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;

public interface AnnotationRecord<S, T> extends AssociationRecord<S, T>
{
	public String getScore();

	public AnnotationMethod getMethod();

	public Collection<Note> getNotes();

}
