/*
 * Created on 21/12/2008
 */
package org.cycads.entities.note;

public interface NotesSourceContainer<N extends Note< ? >> extends NoteSource<N>, NotesContainer<N>
{
	public N addNote(String type, String value);

}
