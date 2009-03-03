/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class TypeSQL implements Type
{
	public final static int	INVALID_ID	= -1;

	public final static TypeSQL NOTE_TYPE_PARENT= new TypeSQL(0, "Note type", )

	int						id;
	int						idParent;
	String					name, description;
	Connection				con;

	public TypeSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT term_type_parent, name, description from term_type WHERE term_type_id="
			+ id);
		if (rs.next()) {
			idParent = rs.getInt("term_type_parent");
			name = rs.getString("name");
			description = rs.getString("description");
		}
		else {
			throw new SQLException("Type does not exist:" + id);
		}
		stmt.close();
	}

	public TypeSQL(int idParent, String name, String description, Connection con) throws SQLException {
		this.idParent = idParent;
		this.name = name;
		this.description = description;
		this.con = con;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT term_type_id, description from term_type WHERE term_type_parent="
			+ idParent + " AND name='" + name + "'");
		if (rs.next()) {
			id = rs.getInt("term_type_id");
			String descriptionDB = rs.getString("description");
			if (description == null) {
				this.description = descriptionDB;
			}
			else if (!description.equals(descriptionDB)) {
				throw new SQLException("Description from DB  (" + descriptionDB
					+ ") different of description supplied:" + description);
			}
		}
		else {
			if (description == null) {
				stmt.executeUpdate("INSERT INTO term_type (term_type_parent, name) VALUES (" + idParent + ",'" + name
					+ "')");
			}
			else {
				stmt.executeUpdate("INSERT INTO term_type (term_type_parent, name, description) VALUES (" + idParent
					+ ",'" + name + "','" + description + "')");
			}
			id = getId(idParent, name, con);
			if (id == INVALID_ID) {
				throw new SQLException("Error creating type:" + idParent + ", " + name + ", " + description);
			}
		}
		stmt.close();
	}

	public static int getId(int idParent, String name, Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT term_type_id from term_type WHERE term_type_parent=" + idParent
			+ " AND name='" + name + "'");
		int id = INVALID_ID;
		if (rs.next()) {
			id = rs.getInt("term_type_id");
		}
		stmt.close();
		return id;
	}

	@Override
	public Collection<Type> getChildren() {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ArrayList<Type> children = new ArrayList<Type>();
			ResultSet rs = stmt.executeQuery("SELECT term_type_id WHERE term_type_parent=" + id);
			while (rs.next()) {
				children.add(new TypeSQL(rs.getInt("term_type_id"), con));
			}
			stmt.close();
			return children;
		}
		catch (SQLException e) {
			e.printStackTrace();
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RuntimeException("Error getting children of type " + id);
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

	@Override
	public Type getParent() {
		try {
			return new TypeSQL(idParent, con);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Type Parent(" + idParent + ") of type (" + id + " doesn't exist.");
		}
	}

	public int getId() {
		return id;
	}
}
