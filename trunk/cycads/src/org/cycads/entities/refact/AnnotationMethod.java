/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public class AnnotationMethod implements IAnnotationMethod
{
	private String								name;
	private Collection<AnnotationMethodNote>	notes;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethod#getName()
	 */
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethod#getNotes()
	 */
	public Collection<AnnotationMethodNote> getNotes()
	{
		return notes;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethod#addNote(org.cycads.entities.refact.AnnotationMethodNote)
	 */
	public void addNote(AnnotationMethodNote note)
	{
		notes.add(note);
	}

}
