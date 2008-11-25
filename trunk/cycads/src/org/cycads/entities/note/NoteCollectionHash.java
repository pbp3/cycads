/*
 * Created on 12/11/2008
 */
package org.cycads.entities.note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;

public class NoteCollectionHash<H extends NoteHolder<H>>
{

	//verificar comment e dblink
	Hashtable<String, Collection<Note<H>>>	hash	= new Hashtable<String, Collection<Note<H>>>();
	H										holder;

	public NoteCollectionHash(H holder) {
		this.holder = holder;
	}

	public Note<H> addNote(Note<H> note) {
		Note<H> note1 = getNote(note.getValue(), note.getType().getName());
		if (note1 != null) {
			return note1;
		}
		Collection<Note<H>> notes = getNotes(note.getType().getName());
		if (notes == null) {
			notes = createNotes();
			hash.put(note.getType().getName(), notes);
		}
		notes.add(note);
		return note;
	}

	private Collection<Note<H>> createNotes() {
		return new ArrayList<Note<H>>();
	}

	public Collection<Note<H>> getNotes() {
		Collection<Note<H>> notes = new LinkedList<Note<H>>();
		for (Collection<Note<H>> notes1 : hash.values()) {
			notes.addAll(notes1);
		}
		return notes;
	}

	public Collection<Note<H>> getNotes(String noteTypeName) {
		return hash.get(noteTypeName);
	}

	public Note<H> getNote(String value, String noteTypeName) {
		Collection<Note<H>> notes = getNotes(noteTypeName);
		if (notes == null || notes.size() == 0) {
			return null;
		}
		for (Note<H> note : notes) {
			if (note.getValue().equals(value)) {
				return note;
			}
		}
		return null;
	}

	public Note<H> addNote(String value, String noteTypeName) {
		return addNote(holder.createNote(value, noteTypeName));
	}

	public Note<H> getOrCreateNote(String value, String noteTypeName) {
		Note<H> note = getNote(value, noteTypeName);
		if (note == null) {
			note = holder.createNote(value, noteTypeName);
		}
		return note;
	}

}
