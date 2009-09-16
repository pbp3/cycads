/*
 * Created on 14/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.general.Messages;

public class CopyNote extends SimpleNoteOperation implements NoteOperation
{

	String	newTagName;

	public CopyNote(Pattern tagNameRegex, Pattern tagValueRegex, String newTagName) {
		super(tagNameRegex, tagValueRegex);
		this.newTagName = newTagName;
		if (newTagName == null || newTagName.length() == 0 || tagNameRegex.matcher(newTagName).matches()) {
			throw new RuntimeException(Messages.copyException());
		}
	}

	@Override
	protected Note execute(Note note, Collection<Note> newNotes) {
		newNotes.add(new SimpleNote(newTagName, note.getValue()));
		return note;
	}
}
