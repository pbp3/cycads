/*
 * Created on 22/10/2009
 */
package org.cycads.entities.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.annotation.SQL.AnnotationSQL;
import org.cycads.entities.annotation.SQL.AssociationSQL;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class SimpleEntitySQL implements EntitySQL
{

	int			id;
	TypeSQL		type;
	Connection	con;

	public Collection<Note> getNotes() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT note_type_id, value from Note where source_id=? and source_type_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			rs = stmt.executeQuery();
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(rs.getInt("note_type_id")), rs.getString("value")));
			}
			return notes;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	@Override
	public void addNote(String noteType, String value) {
		addNote(getNoteType(noteType).getId(), value);
	}

	@Override
	public Note getNote(String noteType, String value) {
		return getNote(getNoteType(noteType).getId(), value);
	}

	@Override
	public Collection<Note> getNotes(String noteType) {
		return getNotes(getNoteType(noteType).getId());
	}

	@Override
	public Collection<String> getNotesValues(String noteType) {
		return getNotesValues(getNoteType(noteType).getId());
	}

	@Override
	public String getNoteValue(String noteType) {
		return getNoteValue(getNoteType(noteType).getId());
	}

	@Override
	public void setNoteValue(String noteType, String value) {
		setNoteValue(getNoteType(noteType).getId(), value);
	}

	public Collection<Note> getNotes(int noteTypeId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT value from Note where source_id=? and source_type_id=? and note_type_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, noteTypeId);
			rs = stmt.executeQuery();
			ArrayList<Note> notes = new ArrayList<Note>();
			while (rs.next()) {
				notes.add(new SimpleNote(getNoteType(noteTypeId), rs.getString("value")));
			}
			return notes;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public Collection<String> getNotesValues(int noteTypeId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT value from Note where source_id=? and source_type_id=? and note_type_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, noteTypeId);
			rs = stmt.executeQuery();
			ArrayList<String> values = new ArrayList<String>();
			while (rs.next()) {
				values.add(rs.getString("value"));
			}
			return values;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public String getNoteValue(int noteTypeId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT value from Note where source_id=? and source_type_id=? and note_type_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, noteTypeId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("value");
			}
			return null;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public void setNoteValue(int noteTypeId, String value) {
		if (getNoteValue(noteTypeId) == null) {
			addNote(noteTypeId, value);
		}
		else {
			PreparedStatement stmt = null;
			try {
				stmt = getConnection().prepareStatement(
					"UPDATE Note SET value=? WHERE source_id=? and source_type_id=? and note_type_id=?");
				stmt.setString(1, value);
				stmt.setInt(2, getId());
				stmt.setInt(3, getTypeId());
				stmt.setInt(4, noteTypeId);
				stmt.executeUpdate();
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
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

	public Note getNote(int noteTypeId, String value) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT * from Note where source_id=? and source_type_id=? and note_type_id=? and value=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, noteTypeId);
			stmt.setString(4, value);
			rs = stmt.executeQuery();
			SimpleNote note = null;
			if (rs.next()) {
				note = new SimpleNote(getNoteType(noteTypeId), value);
			}
			return note;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public Note addNote(int noteTypeId, String value) {
		if (getNote(noteTypeId, value) != null) {
			return null;
		}
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement(
				"INSERT INTO Note (source_id, source_type_id, note_type_id, value) VALUES(?,?,?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, noteTypeId);
			stmt.setString(4, value);
			stmt.executeUpdate();
			return getNote(noteTypeId, value);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public TypeSQL getNoteType(int noteTypeId) throws SQLException {
		return TypeSQL.getType(noteTypeId, getConnection());
	}

	@Override
	public TypeSQL getNoteType(String noteType) {
		return TypeSQL.getType(noteType, getConnection());
	}

	//Synonyms methods

	public Collection<DbxrefSQL> getSynonyms() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT dbxref_id from Synonym where source_id=? and source_type_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			rs = stmt.executeQuery();
			ArrayList<DbxrefSQL> dbxrefs = new ArrayList<DbxrefSQL>();
			while (rs.next()) {
				dbxrefs.add(new DbxrefSQL(rs.getInt("dbxref_id"), getConnection()));
			}
			return dbxrefs;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public Collection<DbxrefSQL> getSynonyms(String dbName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT X.dbxref_id from Synonym S ,dbxref X where S.source_id=? and S.source_type_id=?"
					+ " AND S.dbxref_id=X.dbxref_id AND X.dbname=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setString(3, dbName);
			rs = stmt.executeQuery();
			ArrayList<DbxrefSQL> dbxrefs = new ArrayList<DbxrefSQL>();
			while (rs.next()) {
				dbxrefs.add(new DbxrefSQL(rs.getInt("dbxref_id"), getConnection()));
			}
			return dbxrefs;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public DbxrefSQL getSynonym(String dbName, String accession) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			DbxrefSQL dbxref = DbxrefSQL.getDbxref(dbName, accession, getConnection());
			stmt = getConnection().prepareStatement(
				"SELECT * from Synonym where source_id=? and source_type_id=? AND dbxref_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, dbxref.getId());
			rs = stmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return dbxref;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public DbxrefSQL addSynonym(String dbName, String accession) {
		try {
			return addSynonym(DbxrefSQL.getDbxref(dbName, accession, getConnection()));
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DbxrefSQL addSynonym(Dbxref dbxref) {
		DbxrefSQL dbxrefSQL;
		if (dbxref instanceof DbxrefSQL) {
			dbxrefSQL = (DbxrefSQL) dbxref;
		}
		else {
			dbxrefSQL = getSynonym(dbxref.getDbName(), dbxref.getAccession());
		}
		if (isSynonym(dbxref)) {
			return null;
		}
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement(
				"INSERT INTO Synonym (source_id, source_type_id, dbxref_id) VALUES(?,?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, dbxrefSQL.getId());
			stmt.executeUpdate();
			return dbxrefSQL;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public boolean isSynonym(Dbxref dbxref) {
		DbxrefSQL dbxrefSQL;
		if (dbxref instanceof DbxrefSQL) {
			dbxrefSQL = (DbxrefSQL) dbxref;
		}
		else {
			dbxrefSQL = getSynonym(dbxref.getDbName(), dbxref.getAccession());
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT * from Synonym where source_id=? AND source_type_id=? AND dbxref_id=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, getTypeId());
			stmt.setInt(3, dbxrefSQL.getId());
			rs = stmt.executeQuery();
			return rs.next();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
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

	public boolean isSynonym(String dbName, String accession) {
		return (getSynonym(dbName, accession) != null);
	}

	public static Collection<EntitySQL> getEntities(Type type, String dbName, String accession, Connection con)
			throws SQLException {
		return getEntities(type, DbxrefSQL.getDbxref(dbName, accession, con), con);
	}

	public static Collection<EntitySQL> getEntities(Type type, DbxrefSQL dbxref, Connection con) throws SQLException {
		if (type == null) {
			return getEntities(dbxref, con);
		}
		TypeSQL typeSQL = TypeSQL.getType(type, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT source_id from Synonym where source_type_id=? AND dbxref_id=?");
			stmt.setInt(1, typeSQL.getId());
			stmt.setInt(2, dbxref.getId());
			rs = stmt.executeQuery();
			Collection<EntitySQL> ret = new ArrayList<EntitySQL>();
			while (rs.next()) {
				ret.add(EntityFactorySQL.createObject(rs.getInt("source_id"), typeSQL, con));
			}
			return ret;
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

	public static Collection<EntitySQL> getEntities(String dbName, String accession, Connection con)
			throws SQLException {
		return getEntities(DbxrefSQL.getDbxref(dbName, accession, con), con);
	}

	public static Collection<EntitySQL> getEntities(DbxrefSQL dbxref, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT source_id, source_type_id from Synonym where dbxref_id=?");
			stmt.setInt(1, dbxref.getId());
			rs = stmt.executeQuery();
			Collection<EntitySQL> ret = new ArrayList<EntitySQL>();
			while (rs.next()) {
				ret.add(EntityFactorySQL.createObject(rs.getInt("source_id"), TypeSQL.getType(
					rs.getInt("source_type_id"), con), con));
			}
			return ret;
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

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getTypeId() {
		return type.getId();
	}

	@Override
	public TypeSQL getEntityType() {
		return type;
	}

	@Override
	public <TA extends EntityObject> Association< ? , TA> addAssociation(TA target, Collection<Type> associationTypes) {
		try {
			return (Association< ? , TA>) AssociationSQL.createAssociationSQL(this, (EntitySQL) target,
				associationTypes, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <TA extends EntityObject> Collection< ? extends Association< ? , TA>> getAssociations(TA target,
			Collection<Type> associationTypes) {
		try {
			return (Collection< ? extends Association< ? , TA>>) AssociationSQL.getAssociations(this,
				(EntitySQL) target, associationTypes, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <TA extends EntityObject> Annotation< ? , TA> addAnnotation(TA target, AnnotationMethod method,
			String score, Collection<Type> annotationTypes) {
		try {
			return (Annotation< ? , TA>) AnnotationSQL.createAnnotationSQL(this, (EntitySQL) target, annotationTypes,
				method, score, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <TA extends EntityObject> Collection< ? extends Annotation< ? , TA>> getAnnotations(TA target,
			AnnotationMethod method, Collection<Type> annotationTypes) {
		try {
			return (Collection< ? extends Annotation< ? , TA>>) AnnotationSQL.getAnnotations(this, (EntitySQL) target,
				method, annotationTypes, getConnection());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EntitySQL)) {
			return false;
		}
		EntitySQL o1 = (EntitySQL) obj;

		return (this.getId() == o1.getId()) && (this.getTypeId() == o1.getTypeId());
	}

}
