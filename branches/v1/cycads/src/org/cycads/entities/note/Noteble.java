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

	public Note getNote(String noteType, String value);

	public Note addNote(String noteType, String value);

	public Type getNoteType(String noteType);

}
