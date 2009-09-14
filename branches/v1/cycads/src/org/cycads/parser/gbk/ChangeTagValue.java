/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.biojavax.Note;

public class ChangeTagValue extends SimpleOperation implements Operation
{

	List<String>	substSourceTagValues;
	List<String>	substTargetTagValues;

	protected ChangeTagValue(Pattern tagNameRegex, Pattern tagValueRegex, List<String> substSourceTagValues,
			List<String> substTargetTagValues) {
		super(tagNameRegex, tagValueRegex);
		this.substSourceTagValues = substSourceTagValues;
		this.substTargetTagValues = substTargetTagValues;
	}

	@Override
	protected Collection<Note> execute(Note note) {
		for (int i = 0; i < substSourceTagValues.size(); i++) {
			note.setValue(note.getValue().replaceAll(substSourceTagValues.get(i), substTargetTagValues.get(i)));
		}
		Collection<Note> ret = new ArrayList<Note>();
		ret.add(note);
		return ret;
	}
}
