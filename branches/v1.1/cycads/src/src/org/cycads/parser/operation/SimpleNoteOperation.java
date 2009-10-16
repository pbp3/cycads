/*
 * Created on 14/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.regex.Pattern;

public abstract class SimpleNoteOperation extends SimpleOperation implements NoteOperation
{

	protected SimpleNoteOperation(Pattern tagNameRegex, Pattern tagValueRegex) {
		super(tagNameRegex, tagValueRegex);
	}

	@Override
	public Note transform(Note note, Collection<Note> newNotes) {
		if (match(note)) {
			return execute(note, newNotes);
		}
		return note;
	}

	protected abstract Note execute(Note note, Collection<Note> newNotes);

}
