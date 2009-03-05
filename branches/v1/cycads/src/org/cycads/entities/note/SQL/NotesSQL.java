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

public class NotesSQL
{
	private String		tableName, idFieldName;
	private int			idNoteSource;
	private Connection	con;

	public NotesSQL(int idNoteSource, String tableName, String idFieldName, Connection con) {
		this.idNoteSource = idNoteSource;
		this.tableName = tableName;
		this.idFieldName = idFieldName;
		this.con = con;
	}

	public Collection<Note> getNotes() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT note_type, value from " + tableName + " where " + idFieldName + "="
				+ idNoteSource);
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(rs.getInt("note_type")), rs.getString("value")));
			}
			return notes;
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	public Collection<Note> getNotes(int idNoteType) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT value from " + tableName + " where " + idFieldName + "=" + idNoteSource
				+ " and note_type=" + idNoteType);
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(idNoteType), rs.getString("value")));
			}
			return notes;
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	public Collection<String> getNotesValues(int idNoteType) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT value from " + tableName + " where " + idFieldName + "=" + idNoteSource
				+ " and note_type=" + idNoteType);
			ArrayList<String> values = new ArrayList<String>();
			while (rs.next()) {
				values.add(rs.getString("value"));
			}
			return values;
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	public Note getNote(int idNoteType, String value) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from " + tableName + " where " + idFieldName + "=" + idNoteSource
				+ " and note_type=" + idNoteType + " and value='" + value + "'");
			SimpleNote note = null;
			if (rs.next()) {
				note = new SimpleNote(getNoteType(idNoteType), value);
			}
			return note;
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	public Note addNote(int idNoteType, String value) throws SQLException {
		if (getNote(idNoteType, value) != null) {
			throw new SQLException("Note already exists: (" + idNoteSource + "," + idNoteType + "," + value + ")");
		}
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO " + tableName + " (" + idFieldName + ", note_type, value) VALUES("
				+ idNoteSource + "," + idNoteType + ",'" + value + "')");
			return getNote(idNoteType, value);
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	public Type getNoteType(int idNoteType) throws SQLException {
		return new TypeSQL(idNoteType, con);
	}

}
