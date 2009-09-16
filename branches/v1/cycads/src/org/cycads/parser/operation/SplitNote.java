/*
 * Created on 14/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.regex.Pattern;

public class SplitNote extends SimpleNoteOperation implements NoteOperation
{
	String	separator;

	protected SplitNote(Pattern tagNameRegex, Pattern tagValueRegex, String separator) {
		super(tagNameRegex, tagValueRegex);
		this.separator = separator;
	}

	@Override
	public Note execute(Note note, Collection<Note> newNotes) {
		String value = note.getValue();
		String[] values = value.split(separator);

		if (values.length > 1) {
			for (String newValue : values) {
				newNotes.add(new SimpleNote(note.getType(), newValue));
			}
			note = null;
		}
		return note;
	}

}
