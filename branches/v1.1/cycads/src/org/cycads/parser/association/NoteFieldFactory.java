/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.SimpleType;
import org.cycads.general.Messages;
import org.cycads.parser.FileParserError;

public class NoteFieldFactory implements FieldFactory<Note>
{
	private String				noteSeparator;
	private TypeNameTransformer	typeNameTransformer;

	public NoteFieldFactory(String noteSeparator, TypeNameTransformer typeNameTransformer) {
		this.noteSeparator = noteSeparator;
		this.typeNameTransformer = typeNameTransformer;
	}

	@Override
	public Note create(String typeAndValue) throws FileParserError {
		if (typeAndValue == null || typeAndValue.length() == 0) {
			throw new FileParserError(Messages.invalidTypeNameException(typeAndValue));
		}
		String[] strs = typeAndValue.split(noteSeparator);
		String typeName = null, value;
		if (strs.length == 1) {
			value = strs[0];
		}
		else if (strs.length == 2) {
			typeName = strs[0];
			value = strs[1];
		}
		else {
			throw new FileParserError(Messages.invalidNoteException(typeAndValue));
		}
		typeName = typeNameTransformer.getTypeName(typeName, value);
		return new SimpleNote(new SimpleType(typeName, null), value);
	}

}
