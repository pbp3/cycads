/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;

public abstract class SimpleNoteOperation implements NoteOperation
{
	Pattern	tagNameRegex, tagValueRegex;

	protected SimpleNoteOperation(Pattern tagNameRegex, Pattern tagValueRegex) {
		this.tagNameRegex = tagNameRegex;
		this.tagValueRegex = tagValueRegex;
	}

	@Override
	public boolean match(Note note) {
		return (tagNameRegex == null || tagNameRegex.matcher(note.getTerm().getName()).matches())
			&& (tagValueRegex == null || tagValueRegex.matcher(note.getValue()).matches());
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
