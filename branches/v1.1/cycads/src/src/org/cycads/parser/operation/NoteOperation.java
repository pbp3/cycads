/*
 * Created on 14/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;

public interface NoteOperation extends Operation
{
	public Note transform(Note note, Collection<Note> newNotes);
}
