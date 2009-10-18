package org.cycads.entities.note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.cycads.entities.factory.EntityTypeFactory;

public class SimpleNoteble implements Noteble
{
	protected EntityTypeFactory< ? >				typeFactory;

	private Hashtable<String, Collection<String>>	notes	= new Hashtable<String, Collection<String>>();

	public SimpleNoteble(EntityTypeFactory< ? > factory) {
		this.typeFactory = factory;
	}

	@Override
	public void addNote(String noteType, String value) {
		Note note = new SimpleNote(getNoteType(noteType), value);
		Collection<String> values = notes.get(noteType);
		if (values == null) {
			values = new TreeSet<String>();
			notes.put(noteType, values);
		}
		values.add(value);
	}

	@Override
	public Note getNote(String noteType, String value) {
		Collection<String> values = notes.get(noteType);
		if (values == null || !values.contains(value)) {
			return null;
		}
		return new SimpleNote(getNoteType(noteType), value);
	}

	@Override
	public Type getNoteType(String noteType) {
		return typeFactory.getNoteType(noteType);
	}

	@Override
	public String getNoteValue(String noteType) {
		Collection<String> values = notes.get(noteType);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.iterator().next();
	}

	@Override
	public Collection<Note> getNotes() {
		Collection<Note> ret = new ArrayList<Note>();
		Set<Entry<String, Collection<String>>> entries = notes.entrySet();
		for (Entry<String, Collection<String>> entry : entries) {
			Type type = getNoteType(entry.getKey());
			for (String value : entry.getValue()) {
				ret.add(new SimpleNote(type, value));
			}
		}
		return ret;
	}

	@Override
	public Collection<Note> getNotes(String noteType) {
		Collection<Note> ret = new ArrayList<Note>();
		Type type = getNoteType(noteType);
		Collection<String> values = notes.get(noteType);
		if (values != null) {
			for (String value : values) {
				ret.add(new SimpleNote(type, value));
			}
		}
		return ret;
	}

	@Override
	public Collection<String> getNotesValues(String noteType) {
		return notes.get(noteType);
	}

	@Override
	public void setNoteValue(String noteType, String value) {
		Collection<String> values = notes.get(noteType);
		if (values != null) {
			values.clear();
		}
		else {
			values = new TreeSet<String>();
			notes.put(noteType, values);
		}
		values.add(value);
	}

}
