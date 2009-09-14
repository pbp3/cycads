/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.ArrayList;
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
	public Collection<Note> execute(Note note) {
		String value = note.getValue();
		String[] values = value.split(separator);

		Collection<Note> ret = new ArrayList<Note>();
		if (values.length == 1) {
			ret.add(note);
		}
		else {
			for (String newValue : values) {
				ret.add(new SimpleNote(note.getTerm(), newValue, 0));
			}
		}
		return ret;
	}

}
