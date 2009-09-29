/*
 * Created on 14/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class ChangeTagValueNote extends SimpleNoteOperation implements NoteOperation
{

	List<String>	substSourceTagValues;
	List<String>	substTargetTagValues;

	public ChangeTagValueNote(Pattern tagNameRegex, Pattern tagValueRegex, List<String> substSourceTagValues,
			List<String> substTargetTagValues) {
		super(tagNameRegex, tagValueRegex);
		this.substSourceTagValues = substSourceTagValues;
		this.substTargetTagValues = substTargetTagValues;
	}

	@Override
	protected Note execute(Note note, Collection<Note> newNotes) {
		for (int i = 0; i < substSourceTagValues.size(); i++) {
			note.setValue(note.getValue().replaceAll(substSourceTagValues.get(i), substTargetTagValues.get(i)));
		}
		return note;
	}
}
