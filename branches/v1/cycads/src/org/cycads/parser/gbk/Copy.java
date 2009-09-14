/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleNote;
import org.cycads.general.Messages;

public class Copy extends SimpleOperation implements Operation
{

	String	newTagName;

	protected Copy(Pattern tagNameRegex, Pattern tagValueRegex, String newTagName) {
		super(tagNameRegex, tagValueRegex);
		this.newTagName = newTagName;
		if (newTagName == null || newTagName.length() == 0 || tagNameRegex.matcher(newTagName).matches()) {
			throw new RuntimeException(Messages.copyException());
		}
	}

	@Override
	protected Collection<Note> execute(Note note) {
		Collection<Note> ret = new ArrayList<Note>();
		ret.add(note);
		ret.add(new SimpleNote(RichObjectFactory.getDefaultOntology().getOrCreateTerm(newTagName), note.getValue(), 0));
		return ret;
	}
}
