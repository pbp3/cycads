/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class TypeSQL extends HasSynonymsNotebleSQL implements Type
{
	public static final int						INVALID_ID		= -1;

	public static Hashtable<String, TypeSQL>	typesHashByName	= new Hashtable<String, TypeSQL>();
	public static Hashtable<Integer, TypeSQL>	typesHashById	= new Hashtable<Integer, TypeSQL>();
	public static final String					ANNOTATION		= "Annotation";
	public static final String					ASSOCIATION		= "Association";
	public static final String					DBXREF			= "Dbxref";
	public static final String					FUNCTION		= "Function";
	public static final String					SUBSEQUENCE		= "Subsequence";
	public static final String					SEQUENCE		= "Sequence";
	public static final String					ORGANISM		= "Organism";
	public static final String					REACTION		= "Reaction";
	public static final String					PATHWAY			= "Pathway";
	public static final String					COMPOUND		= "Compound";
	public static final String					FEATURE			= "Feature";
	public static final String					TERM_TYPE		= "Term_type";
	public static final String					METHOD			= "Method";

	private int									id;
	private String								name, description;
	private Connection							con;

	protected TypeSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT name, description from Term_type WHERE type_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				name = rs.getString("name");
				description = rs.getString("description");
			}
			else {
				throw new SQLException("Type does not exist:" + id);
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

	protected TypeSQL(String name, Connection con) throws SQLException {
		this.name = name;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT type_id, description from Term_type WHERE name=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("type_id");
				description = rs.getString("description");
			}
			else {
				stmt = con.prepareStatement("INSERT INTO Term_type (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
				else {
					throw new SQLException("Error creating type:" + name);
				}
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

	public static int getId(String name, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT type_id from Term_type WHERE name=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			int id = INVALID_ID;
			if (rs.next()) {
				id = rs.getInt("type_id");
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

	public static TypeSQL getType(String name, Connection con) {
		TypeSQL type = typesHashByName.get(name);
		if (type == null) {
			try {
				type = new TypeSQL(name, con);
			}
			catch (SQLException e) {
				throw new RuntimeException("Can not create the type:" + name, e);
			}
			typesHashByName.put(name, type);
		}
		return type;
	}

	public static TypeSQL getType(int id, Connection con) throws SQLException {
		TypeSQL type = typesHashById.get(id);
		if (type == null) {
			type = new TypeSQL(id, con);
			typesHashById.put(id, type);
		}
		return type;
	}

	public static TypeSQL getType(Type type, Connection con) {
		if (type instanceof TypeSQL) {
			return (TypeSQL) type;
		}
		else {
			return getType(type.getName(), con);
		}
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Connection getConnection() {
		return con;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Type)) {
			return false;
		}
		Type type = (Type) obj;

		return this.getName().equals(type.getName());
	}

	@Override
	public int compareTo(Type o) {
		return getName().compareTo(o.getName());
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement("UPDATE Term_type SET description=? WHERE type_id=?");
			stmt.setString(1, description);
			stmt.setInt(2, getId());
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException("Can't set description value of the Type:" + getId());
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
	public TypeSQL getEntityType() {
		return getEntityType(getConnection());
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(TERM_TYPE, con);
	}

}
