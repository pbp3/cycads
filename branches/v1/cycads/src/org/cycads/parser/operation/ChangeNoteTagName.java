/*
 * Created on 14/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.regex.Pattern;

public class ChangeNoteTagName extends SimpleNoteOperation implements NoteOperation
{
	String	newTagName;

	protected ChangeNoteTagName(Pattern tagNameRegex, Pattern tagValueRegex, String newTagName) {
		super(tagNameRegex, tagValueRegex);
		this.newTagName = newTagName;
	}

	@Override
	protected Note execute(Note note, Collection<Note> newNotes) {
		note.setType(newTagName);
		return note;
	}
}
