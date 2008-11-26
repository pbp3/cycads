/*
 * Created on 26/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

public interface NoteCollection<N extends Note< ? extends NoteSource>>
{
	public Collection<N> getNotes();

	public Collection<N> getNotes(String noteTypeName);

	public N getNote(String value, String noteTypeName);

	public N addNote(N note);

}
