/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.TypeSQL;

public class NotesSQL
{
	String		tableName, idFieldName;
	int			idNoteSource;
	Connection	con;

	public NotesSQL(int idNoteSource, String tableName, String idFieldName, Connection con) {
		this.idNoteSource = idNoteSource;
		this.tableName = tableName;
		this.idFieldName = idFieldName;
		this.con = con;
	}

	public Collection<Note> getNotes() throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT note_type, value from " + tableName + " where " + idFieldName + "="
			+ idNoteSource);
		ArrayList<Note> notes = new ArrayList<Note>();
		while (rs.next()) {
			notes.add(new SimpleNote(getNoteType(rs.getInt("note_type")), rs.getString("value")));
		}
		stmt.close();
		return notes;
	}

	public Collection<Note> getNotes(int idNoteType) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT value from " + tableName + " where " + idFieldName + "="
			+ idNoteSource + " and note_type=" + idNoteType);
		ArrayList<Note> notes = new ArrayList<Note>();
		while (rs.next()) {
			notes.add(new SimpleNote(getNoteType(idNoteType), rs.getString("value")));
		}
		stmt.close();
		return notes;
	}

	public Collection<String> getNotesValues(int idNoteType) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT value from " + tableName + " where " + idFieldName + "="
			+ idNoteSource + " and note_type=" + idNoteType);
		ArrayList<String> values = new ArrayList<String>();
		while (rs.next()) {
			values.add(rs.getString("value"));
		}
		stmt.close();
		return values;
	}

	public Note getNote(int idNoteType, String value) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * from " + tableName + " where " + idFieldName + "=" + idNoteSource
			+ " and note_type=" + idNoteType + " and value='" + value + "'");
		SimpleNote note = null;
		if (rs.next()) {
			note = new SimpleNote(getNoteType(idNoteType), value);
		}
		stmt.close();
		return note;
	}

	public Note addNote(int idNoteType, String value) throws SQLException {
		if (getNote(idNoteType, value) != null) {
			throw new SQLException("Note already exists: (" + idNoteSource + "," + idNoteType + "," + value + ")");
		}
		Statement stmt = con.createStatement();
		stmt.executeUpdate("INSERT INTO " + tableName + " (" + idFieldName + ", note_type, value) VALUES("
			+ idNoteSource + "," + idNoteType + ",'" + value + "')");
		stmt.close();
		return getNote(idNoteType, value);
	}

	public Type getNoteType(int idNoteType) throws SQLException {
		return new TypeSQL(idNoteType, con);
	}

}
