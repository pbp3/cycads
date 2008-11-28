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
	ChangeSupport	changeSupport	= new ChangeSupport();

	public NotesArrayList() {
		super();
	}

	public NotesArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public N addNote(N note) {
		ChangeEvent ce = new ChangeEvent(this, ChangeType.NOTE, note, null);
		changeSupport.firePreChangeEvent(ce);
		N note1 = getNote(note.getValue(), note.getType());
		if (note1 != null) {
			return note1;
		}
		add(note);
		changeSupport.firePostChangeEvent(ce);
		return note;
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

	public N getNote(String value, String noteTypeName) {
		for (N note : this) {
			if (note.getType().equals(noteTypeName) && note.getValue().equals(value)) {
				return note;
			}
		}
		return null;
	}

	public void addChangeListener(ChangeListener cl, ChangeType ct) {
		changeSupport.addChangeListener(cl, ct);
	}

	public boolean isUnchanging(ChangeType ct) {
		return changeSupport.isUnchanging(ct);
	}

	public void removeChangeListener(ChangeListener cl, ChangeType ct) {
		changeSupport.removeChangeListener(cl, ct);
	}

}
