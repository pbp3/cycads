/*
 * Created on 08/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.AnnotationMethodNote;

public class AnnotationMethod implements AnnotationMethod
{
	private String								name;
	private Collection<AnnotationMethodNote>	notes;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethod#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethod#getNotes()
	 */
	public Collection<AnnotationMethodNote> getNotes() {
		return notes;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IAnnotationMethod#addNote(org.cycads.entities.refact.AnnotationMethodNote)
	 */
	public void addNote(AnnotationMethodNote note) {
		notes.add(note);
	}

	public AnnotationMethodNote addNote(String value, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
