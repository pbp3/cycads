/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

public class NoteBJ<H extends NoteHolder<H>> implements Note<H>
{
	String		value;
	NoteType	noteType;
	H			holder;

	public NoteBJ(H holder, String value, String noteTypeName) {
		this.value = value;
		this.noteType = NoteTypeBJ.getNoteTypeInstance(noteTypeName);
		this.holder = holder;
	}

	public NoteType getType() {
		return noteType;
	}

	public String getTypeName() {
		return noteType.getName();
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Note)) {
			return false;
		}
		Note o = (Note) obj;
		return (o.getValue().equals(this.getValue()) && o.getType().equals(this.getType()));
	}

	public H getHolder() {
		return holder;
	}

}
