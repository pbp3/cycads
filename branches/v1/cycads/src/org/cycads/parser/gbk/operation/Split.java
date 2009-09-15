/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;
import org.biojavax.SimpleNote;

public class Split extends SimpleOperation implements Operation
{
	String	separator;

	protected Split(Pattern tagNameRegex, Pattern tagValueRegex, String separator) {
		super(tagNameRegex, tagValueRegex);
		this.separator = separator;
	}

	@Override
	public Note execute(Note note, Collection<Note> newNotes) {
		String value = note.getValue();
		String[] values = value.split(separator);

		if (values.length > 1) {
			for (String newValue : values) {
				newNotes.add(new SimpleNote(note.getTerm(), newValue, 0));
			}
			note = null;
		}
		return note;
	}

}
