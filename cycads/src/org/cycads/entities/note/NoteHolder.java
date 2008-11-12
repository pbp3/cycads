/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.Collection;

public interface NoteHolder<H extends NoteHolder<H>>
{
	public Collection<Note<H>> getNotes();

	public Collection<Note<H>> getNotes(String noteTypeName);

	public Note<H> addNote(Note<H> note);

	public Note<H> addNote(String value, String noteTypeName);

	public Note<H> getOrCreateNote(String value, String noteTypeName);

	public Note<H> getNote(String value, String noteTypeName);

	public Note<H> createNote(String value, String noteTypeName);

}
