/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;

import org.biojavax.Note;

public interface Operation
{
	public boolean match(Note note);

	public Note transform(Note note, Collection<Note> newNotes);
}
