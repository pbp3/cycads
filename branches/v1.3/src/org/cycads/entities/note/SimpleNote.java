/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import org.cycads.general.ParametersDefault;

public class SimpleNote implements Note
{
	String	value;
	Type	noteType;

	public SimpleNote(Type type, String value) {
		this.value = value;
		this.noteType = type;
	}

	public Type getType() {
		return noteType;
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

	public String toString() {
		return getType().getName() + ParametersDefault.getNoteToStringSeparator() + getValue();
	}

}
