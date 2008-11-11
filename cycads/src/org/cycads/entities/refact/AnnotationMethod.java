/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public class AnnotationMethod
{
	private String								name;
	private Collection<AnnotationMethodNote>	notes;

	public String getName()
	{
		return name;
	}

	public Collection<AnnotationMethodNote> getNotes()
	{
		return notes;
	}

	public void addNote(AnnotationMethodNote note)
	{
		notes.add(note);
	}

}
