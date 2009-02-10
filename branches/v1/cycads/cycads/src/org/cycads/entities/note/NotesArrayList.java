/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.change.ChangeEvent;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeSupport;
import org.cycads.entities.change.ChangeType;

public class NotesArrayList<N extends Note< ? extends NoteSource>> extends ArrayList<N> implements NotesContainer<N>
{
	ChangeSupport<N>	changeSupport	= new ChangeSupport<N>();

	public NotesArrayList() {
		super();
	}

	public NotesArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public N addNote(Note< ? > note) {
		N note2 = (N) note;
		ChangeEvent<N> ce = new ChangeEvent<N>(this, ChangeType.NOTE, note2, null);
		changeSupport.firePreChangeEvent(ce);
		N note1 = getNote(note2.getType(), note2.getValue());
		if (note1 != null) {
			return note1;
		}
		add(note2);
		changeSupport.firePostChangeEvent(ce);
		return note2;
	}

	public Collection<N> getNotes() {
		return this;
	}

	public Collection<N> getNotes(String noteTypeName) {
		ArrayList<N> notes = new ArrayList<N>();
		for (N note : this) {
			if (note.getType().equals(noteTypeName)) {
				notes.add(note);
			}
		}
		return notes;
	}

	public N getNote(String type, String value) {
		for (N note : this) {
			if (note.getType().equals(type) && note.getValue().equals(value)) {
				return note;
			}
		}
		return null;
	}

	public void addChangeListener(ChangeListener<N> cl, ChangeType ct) {
		changeSupport.addChangeListener(cl, ct);
	}

	public boolean isUnchanging(ChangeType ct) {
		return changeSupport.isUnchanging(ct);
	}

	public void removeChangeListener(ChangeListener<N> cl, ChangeType ct) {
		changeSupport.removeChangeListener(cl, ct);
	}

	@Override
	public Collection<String> getNotesValues(String noteTypeName) {
		Collection<N> notes = getNotes(noteTypeName);
		ArrayList<String> values = new ArrayList<String>(notes.size());
		for (N note : notes) {
			values.add(note.getValue());
		}
		return values;
	}

}
