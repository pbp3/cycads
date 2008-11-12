/*
 * Created on 11/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public interface IAnnotationMethod
{
	public String getName();

	public Collection<AnnotationMethodNote> getNotes();

	public void addNote(AnnotationMethodNote note);

	public AnnotationMethodNote addNote(String value, String type);

}