/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

public class NoteBJ implements Note
{
	String		value;
	NoteType	noteType;

	public NoteBJ(String value, String noteTypeName) {
		this.value = value;
		this.noteType = NoteTypeBJ.getNoteTypeInstance(noteTypeName);
	}

	public NoteType getNoteType() {
		return noteType;
	}

	public String getTypeName() {
		return noteType.getName();
	}

	public String getValue() {
		return value;
	}

}
