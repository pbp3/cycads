/*
 * Created on 11/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.NoteSource;

public interface AnnotationMethod extends NoteSource
{
	public String getName();

	public int getWeight();

}