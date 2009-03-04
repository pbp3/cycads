/*
 * Created on 03/03/2009
 */
package org.cycads.entities.note;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class NotebleSQL implements Noteble {
	NotesSQL	notes	= null;

	public NotesSQL getNotesSQL() {
		if (notes == null) {
			notes = new NotesSQL(getId(), getNoteTableName(), getIdFieldName(), getConnection());
		}
		return notes;
	}

	public abstract int getId();

	public abstract Connection getConnection();

	public abstract String getNoteTableName();

	public abstract String getIdFieldName();

	@Override
	public Note addNote(String noteType, String value) {
		try {
			return getNotesSQL().addNote(getNoteType(noteType).getId(), value);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Note getNote(String noteType, String value) {
		try {
			return getNotesSQL().getNote(getNoteType(noteType).getId(), value);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Note> getNotes() {
		try {
			return getNotesSQL().getNotes();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Note> getNotes(String noteType) {
		try {
			return getNotesSQL().getNotes(getNoteType(noteType).getId());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<String> getNotesValues(String noteType) {
		try {
			return getNotesSQL().getNotesValues(getNoteType(noteType).getId());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getNoteType(String noteType) {
		try {
			return new TypeSQL(TypeSQL.NOTE_TYPE_PARENT_ID, noteType, null, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
