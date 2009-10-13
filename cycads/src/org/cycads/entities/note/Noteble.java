/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

public interface Noteble
{
	public Collection<Note> getNotes();

	public Collection<Note> getNotes(String noteType);

	public Collection<String> getNotesValues(String noteType);

	/* return one note value of this type */
	public String getNoteValue(String noteType);

	/*set all values of one type*/
	public void setNoteValue(String noteType, String value);

	public Note getNote(String noteType, String value);

	public void addNote(String noteType, String value);

	public Type getNoteType(String noteType);

}
