/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class TypeSQL extends HasSynonymsNotebleSQL implements Type
{
	public static final int						INVALID_ID		= -1;

	public static Hashtable<String, TypeSQL>	typesHashByName	= new Hashtable<String, TypeSQL>();
	public static Hashtable<Integer, TypeSQL>	typesHashById	= new Hashtable<Integer, TypeSQL>();

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
		if (type == null) {
			return null;
		}
		if (type instanceof TypeSQL) {
			return (TypeSQL) type;
		}
		else {
			return getType(type.getName(), con);
		}
	}

	public static Collection<TypeSQL> getTypesSQL(Collection<Type> types, Connection con) {
		if (types == null) {
			return null;
		}
		Collection<TypeSQL> ret = new ArrayList<TypeSQL>(types.size());
		for (Type type : types) {
			ret.add(TypeSQL.getType(type, con));
		}
		return ret;
	}

	public static TypeSQL[] getTypes(Connection con, Type... types) {
		if (types == null) {
			return null;
		}
		TypeSQL[] ret = new TypeSQL[types.length];
		int i = 0;
		for (Type type : types) {
			ret[i++] = TypeSQL.getType(type, con);
		}
		return ret;
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
		return TypeSQL.getType(ENTITY_TYPE_NAME, con);
	}

}
