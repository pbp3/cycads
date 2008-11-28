/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

import org.cycads.entities.change.ChangeEvent;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeSupport;
import org.cycads.entities.change.ChangeType;

public class NotesHashTable<N extends Note< ? extends NoteSource>> extends Hashtable<String, Collection<N>>
		implements NotesContainer<N>
{
	ChangeSupport	changeSupport	= new ChangeSupport();

	public NotesHashTable() {
		super();
	}

	public NotesHashTable(int initialCapacity) {
		super(initialCapacity);
	}

	public NotesHashTable(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public NotesHashTable(Map< ? extends String, ? extends Collection<N>> t) {
		super(t);
	}

	public N addNote(N note) {
		ChangeEvent ce = new ChangeEvent(this, ChangeType.NOTE, note, null);
		changeSupport.firePreChangeEvent(ce);
		N note1 = getNote(note.getValue(), note.getType());
		if (note1 != null) {
			return note1;
		}
		Collection<N> notes = getNotes(note.getType());
		if (notes == null) {
			notes = createNotesForEntry();
			put(note.getType(), notes);
		}
		notes.add(note);
		changeSupport.firePostChangeEvent(ce);
		return note;
	}

	private Collection<N> createNotesForEntry() {
		return new ArrayList<N>();
	}

	public Collection<N> getNotes() {
		Collection<N> notes = new LinkedList<N>();
		for (Collection<N> notes1 : values()) {
			notes.addAll(notes1);
		}
		return notes;
	}

	public Collection<N> getNotes(String noteTypeName) {
		return get(noteTypeName);
	}

	public N getNote(String value, String noteTypeName) {
		Collection<N> notes = getNotes(noteTypeName);
		if (notes == null || notes.size() == 0) {
			return null;
		}
		for (N note : notes) {
			if (note.getValue().equals(value)) {
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
