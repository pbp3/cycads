/*
 * Created on 26/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

import org.cycads.entities.change.Changeable;

public interface NotesContainer<N extends Note< ? >> extends Changeable<N>
{
	public Collection<N> getNotes();

	public Collection<N> getNotes(String noteTypeName);

	public Collection<String> getNotesValues(String noteTypeName);

	public N getNote(String type, String value);

	public N addNote(Note< ? > note);

}
