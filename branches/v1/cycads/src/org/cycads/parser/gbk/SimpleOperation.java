/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;

public abstract class SimpleOperation implements Operation
{
	Pattern	tagNameRegex, tagValueRegex;

	protected SimpleOperation(Pattern tagNameRegex, Pattern tagValueRegex) {
		this.tagNameRegex = tagNameRegex;
		this.tagValueRegex = tagValueRegex;
	}

	@Override
	public boolean match(Note note) {
		return (tagNameRegex == null || tagNameRegex.matcher(note.getTerm().getName()).matches())
			&& (tagValueRegex == null || tagValueRegex.matcher(note.getValue()).matches());
	}

	@Override
	public Collection<Note> transform(Note note) {
		if (match(note)) {
			return execute(note);
		}
		Collection<Note> ret = new ArrayList<Note>();
		ret.add(note);
		return ret;
	}

	protected abstract Collection<Note> execute(Note note);

}
