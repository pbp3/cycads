/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.note.Note;
import org.cycads.parser.FileParserException;

public class NotesFieldFactory implements FieldFactory<Collection<Note>>
{
	private String				notesSeparator;
	private String				delimiter;
	private IndependentNoteFactory	independentNoteFactory;

	public NotesFieldFactory(String notesSeparator, String delimiter, IndependentNoteFactory independentNoteFactory) {
		this.notesSeparator = notesSeparator;
		this.delimiter = delimiter;
		this.independentNoteFactory = independentNoteFactory;
	}

	@Override
	public Collection<Note> create(String value) throws FileParserException {
		value = cleanTextDelimiter(value);
		Collection<Note> ret = new ArrayList<Note>();
		String[] notes = value.split(notesSeparator);
		for (String note : notes) {
			ret.add(independentNoteFactory.create(note));
		}
		return ret;
	}

	private String cleanTextDelimiter(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		int start = 0, end = text.length();
		if (text.startsWith(delimiter)) {
			start = delimiter.length();
		}
		if (text.endsWith(delimiter)) {
			end = end - delimiter.length();
		}
		return text.substring(start, end);
	}

}
