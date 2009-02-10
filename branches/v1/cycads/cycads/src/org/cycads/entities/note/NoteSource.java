/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

public interface NoteSource<N extends Note< ? >>
{
	public N createNote(String type, String value);

	public N createNote(Note< ? > note);

}
