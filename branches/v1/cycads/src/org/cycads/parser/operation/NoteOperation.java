/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;

import org.biojavax.Note;

public interface NoteOperation extends Operation
{
	public Note transform(Note note, Collection<Note> newNotes);
}
