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

import org.cycads.entities.note.Type;
import org.cycads.general.ParametersDefault;

public class TypeSQL implements Type
{
	public final static int	INVALID_ID					= -1;

	public final static int	NOTE_TYPE_PARENT_ID			= ParametersDefault.getNoteTypeId();
	public final static int	ANNOTATION_TYPE_PARENT_ID	= ParametersDefault.getAnnotationTypeId();

	int						id;
	int						idParent;
	String					name, description;
	Connection				con;

	public TypeSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT term_type_parent, name, description from term_type WHERE term_type_id=" + id);
			if (rs.next()) {
				idParent = rs.getInt("term_type_parent");
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

	public TypeSQL(int idParent, String name, String description, Connection con) throws SQLException {
		this.idParent = idParent;
		this.name = name;
		this.description = description;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT term_type_id, description from term_type WHERE term_type_parent=" + idParent
				+ " AND name='" + name + "'");
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
					stmt.executeUpdate("INSERT INTO term_type (term_type_parent, name) VALUES (" + idParent + ",'"
						+ name + "')");
				}
				else {
					stmt.executeUpdate("INSERT INTO term_type (term_type_parent, name, description) VALUES ("
						+ idParent + ",'" + name + "','" + description + "')");
				}
				id = getId(idParent, name, con);
				if (id == INVALID_ID) {
					throw new SQLException("Error creating type:" + idParent + ", " + name + ", " + description);
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

	public static int getId(int idParent, String name, Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT term_type_id from term_type WHERE term_type_parent=" + idParent
				+ " AND name='" + name + "'");
			int id = INVALID_ID;
			if (rs.next()) {
				id = rs.getInt("term_type_id");
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
	public Collection<Type> getChildren() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT term_type_id WHERE term_type_parent=" + id);
			ArrayList<Type> children = new ArrayList<Type>();
			while (rs.next()) {
				children.add(new TypeSQL(rs.getInt("term_type_id"), con));
			}
			return children;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error getting children of type " + id);
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
