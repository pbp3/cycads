/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT type_id, value from " + tableName + " where " + idFieldName + "=?");
			stmt.setInt(1, idNoteSource);
			rs = stmt.executeQuery();
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(rs.getInt("type_id")), rs.getString("value")));
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT value from " + tableName + " where " + idFieldName + "=? and type_id=?");
			stmt.setInt(1, idNoteSource);
			stmt.setInt(2, idNoteType);
			rs = stmt.executeQuery();
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT value from " + tableName + " where " + idFieldName + "=? and type_id=?");
			stmt.setInt(1, idNoteSource);
			stmt.setInt(2, idNoteType);
			rs = stmt.executeQuery();
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

	public String getNoteValue(int idNoteType) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT value from " + tableName + " where " + idFieldName + "=? and type_id=?");
			stmt.setInt(1, idNoteSource);
			stmt.setInt(2, idNoteType);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("value");
			}
			return null;
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

	public void setNoteValue(int idNoteType, String value) throws SQLException {
		if (getNoteValue(idNoteType) == null) {
			addNote(idNoteType, value);
		}
		else {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("UPDATE " + tableName + " SET value=? WHERE " + idFieldName
					+ "=? and type_id=?");
				stmt.setString(1, value);
				stmt.setInt(2, idNoteSource);
				stmt.setInt(3, idNoteType);
				stmt.executeUpdate();
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
	}

	public Note getNote(int idNoteType, String value) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from " + tableName + " where " + idFieldName
				+ "=? and type_id=? and value=?");
			stmt.setInt(1, idNoteSource);
			stmt.setInt(2, idNoteType);
			stmt.setString(3, value);
			rs = stmt.executeQuery();
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
			return null;
		}
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO " + tableName + " (" + idFieldName
				+ ", type_id, value) VALUES(?,?,?)");
			stmt.setInt(1, idNoteSource);
			stmt.setInt(2, idNoteType);
			stmt.setString(3, value);
			stmt.executeUpdate();
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
