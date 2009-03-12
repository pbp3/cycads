/*
 * Created on 04/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public abstract class AnnotationSQL extends HasSynonymsNotebleSQL
		implements Annotation<DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	public final static int		INVALID_PARENT_ID	= 0;

	private int					id;
	private int					methodId;

	/*The types are not synchonized*/
	private Collection<TypeSQL>	types;
	private AnnotationMethodSQL	method;

	private Connection			con;

	public AnnotationSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT method from Annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				methodId = rs.getInt("method");
			}
			else {
				throw new SQLException("Annotation does not exist:" + id);
			}
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

	public static int createAnnotationSQL(AnnotationMethodSQL method, Connection con) throws SQLException {
		int id = 0;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Annotation (method) VALUES (" + method.getId() + ")",
				Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Annotation insert didn't return the annotation id.");
			}
			return id;
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
	public AnnotationMethodSQL getAnnotationMethod() {
		if (method == null) {
			try {
				method = new AnnotationMethodSQL(getMethodId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return method;
	}

	@Override
	public Collection<TypeSQL> getTypes() {
		if (types == null) {
			Statement stmt = null;
			ResultSet rs = null;
			types = new ArrayList<TypeSQL>();
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT type_id from Annotation_type WHERE annotation_id=" + getId());
				while (rs.next()) {
					types.add(new TypeSQL(rs.getInt("type_id"), getConnection()));
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
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
		return types;
	}

	@Override
	public boolean hasType(String typeStr) {
		for (TypeSQL type : getTypes()) {
			if (type.getName().equals(typeStr)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TypeSQL addType(String typeStr) {
		try {
			TypeSQL type = new TypeSQL(typeStr, null, getConnection());
			return addType(type);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL addType(TypeSQL type) {
		Statement stmt = null;
		try {
			if (!hasType(type.getName())) {
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO Annotation_type (annotation_id, type_id) VALUES (" + getId() + ","
					+ type.getId() + ")");
			}
			if (types != null) {
				types.add(type);
			}
			return type;
		}
		catch (SQLException e) {
			e.printStackTrace();
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

	@Override
	public int getId() {
		return id;
	}

	public int getMethodId() {
		return methodId;
	}

	@Override
	public String getSynonymTableName() {
		return "Annotation_synonym";
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public String getIdFieldName() {
		return "annotation_id";
	}

	@Override
	public String getNoteTableName() {
		return "Annotation_note";
	}

}
