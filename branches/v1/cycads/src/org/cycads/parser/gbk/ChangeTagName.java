/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;
import org.biojavax.RichObjectFactory;

public class ChangeTagName extends SimpleOperation implements Operation
{
	String	newTagName;

	protected ChangeTagName(Pattern tagNameRegex, Pattern tagValueRegex, String newTagName) {
		super(tagNameRegex, tagValueRegex);
		this.newTagName = newTagName;
	}

	@Override
	protected Collection<Note> execute(Note note) {
		note.setTerm(RichObjectFactory.getDefaultOntology().getOrCreateTerm(newTagName));
		Collection<Note> ret = new ArrayList<Note>();
		ret.add(note);
		return ret;
	}
}
