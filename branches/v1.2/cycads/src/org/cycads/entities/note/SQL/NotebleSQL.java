/*
 * Created on 03/03/2009
 */
package org.cycads.entities.note.SQL;

import java.sql.SQLException;
import java.util.Collection;

import org.cycads.entities.annotation.SQL.EntitySQL;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Noteble;

public abstract class NotebleSQL implements Noteble, EntitySQL
{
	protected NotesSQL	notes	= null;

	public NotesSQL getNotesSQL() {
		if (notes == null) {
			notes = new NotesSQL(getId(), getEntityType(), getConnection());
		}
		return notes;
	}

	//	public abstract String getIdFieldName();

	@Override
	public void addNote(String noteType, String value) {
		try {
			getNotesSQL().addNote(getNoteType(noteType).getId(), value);
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
	public String getNoteValue(String noteType) {
		try {
			return getNotesSQL().getNoteValue(getNoteType(noteType).getId());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setNoteValue(String noteType, String value) {
		try {
			getNotesSQL().setNoteValue(getNoteType(noteType).getId(), value);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getNoteType(String noteType) {
		return TypeSQL.getType(noteType, getConnection());
	}

}
