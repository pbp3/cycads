/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;

public class OperationRemove extends SimpleOperation implements Operation
{

	protected OperationRemove(Pattern tagNameRegex, Pattern tagValueRegex) {
		super(tagNameRegex, tagValueRegex);
	}

	@Override
	public Collection<Note> execute(Note note) {
		return new ArrayList<Note>();
	}

}
