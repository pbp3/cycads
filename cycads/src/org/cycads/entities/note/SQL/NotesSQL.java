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
	private int			sourceId;
	private int			sourceTypeId;
	private Connection	con;

	public NotesSQL(int sourceId, Type sourceType, Connection con) {
		this.sourceId = sourceId;
		this.con = con;
		if (sourceType instanceof TypeSQL) {
			this.sourceTypeId = ((TypeSQL) sourceType).getId();
		}
		else {
			this.sourceTypeId = TypeSQL.getType(sourceType.getName(), con).getId();
		}
	}

	public Collection<Note> getNotes() throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT note_type_id, value from Note where source_id=? and source_type_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			rs = stmt.executeQuery();
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(rs.getInt("note_type_id")), rs.getString("value")));
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

	public Collection<Note> getNotes(int noteTypeId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT value from Note where source_id=? and source_type_id=? and note_type_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, noteTypeId);
			rs = stmt.executeQuery();
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(noteTypeId), rs.getString("value")));
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

	public Collection<String> getNotesValues(int noteTypeId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT value from Note where source_id=? and source_type_id=? and note_type_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, noteTypeId);
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

	public String getNoteValue(int noteTypeId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT value from Note where source_id=? and source_type_id=? and note_type_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, noteTypeId);
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

	public void setNoteValue(int noteTypeId, String value) throws SQLException {
		if (getNoteValue(noteTypeId) == null) {
			addNote(noteTypeId, value);
		}
		else {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("UPDATE Note SET value=? WHERE source_id=? and source_type_id=? and note_type_id=?");
				stmt.setString(1, value);
				stmt.setInt(2, sourceId);
				stmt.setInt(3, sourceTypeId);
				stmt.setInt(4, noteTypeId);
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

	public Note getNote(int noteTypeId, String value) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from Note where source_id=? and source_type_id=? and note_type_id=? and value=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, noteTypeId);
			stmt.setString(4, value);
			rs = stmt.executeQuery();
			SimpleNote note = null;
			if (rs.next()) {
				note = new SimpleNote(getNoteType(noteTypeId), value);
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

	public Note addNote(int noteTypeId, String value) throws SQLException {
		if (getNote(noteTypeId, value) != null) {
			return null;
		}
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Note (source_id, source_type_id, note_type_id, value) VALUES(?,?,?,?)");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, noteTypeId);
			stmt.setString(4, value);
			stmt.executeUpdate();
			return getNote(noteTypeId, value);
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

	public Type getNoteType(int noteTypeId) throws SQLException {
		return TypeSQL.getType(noteTypeId, con);
	}

}
