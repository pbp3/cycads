/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.SimpleType;
import org.cycads.general.Messages;
import org.cycads.parser.ParserException;

public class IndependentNoteFactory implements IndependentObjectFactory<Note>
{
	private String				noteSeparator;
	private TypeNameTransformer	typeNameTransformer;

	public IndependentNoteFactory(String noteSeparator, TypeNameTransformer typeNameTransformer) {
		this.noteSeparator = noteSeparator;
		this.typeNameTransformer = typeNameTransformer;
	}

	@Override
	public Note create(String typeAndValue) throws ParserException {
		String[] strs = Tools.split(typeAndValue, noteSeparator);
		if (strs == null || strs.length == 0) {
			return null;
		}
		String typeName = null, value;
		if (strs.length == 1) {
			value = strs[0];
		}
		else if (strs.length == 2) {
			typeName = strs[0];
			value = strs[1];
		}
		else {
			throw new ParserException(Messages.invalidNoteException(typeAndValue));
		}
		typeName = typeNameTransformer.getTypeName(typeName, value);
		return new SimpleNote(new SimpleType(typeName, null), value);
	}

}
