/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

public interface NoteSource
{
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName);

}
