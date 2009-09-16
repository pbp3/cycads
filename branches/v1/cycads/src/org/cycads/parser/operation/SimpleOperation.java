/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.regex.Pattern;

import org.biojavax.Note;

public class SimpleOperation implements Operation
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

}
