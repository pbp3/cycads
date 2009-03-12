/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.note.Type;
import org.cycads.general.ParametersDefault;

public class TypeSQL implements Type
{
	public static final int	INVALID_ID					= -1;
	private static TypeSQL	subseqAnnotationType		= null;
	private static TypeSQL	dbxrefAnnotationType		= null;
	private static TypeSQL	functionAnnotationType		= null;
	private static TypeSQL	dbxrefSourceAnnotationType	= null;

	private int				id;
	private String			name, description;
	private Connection		con;

	public TypeSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT name, description from term_type WHERE type_id=" + id);
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

	public TypeSQL(String name, String description, Connection con) throws SQLException {
		this.name = name;
		this.description = description;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT type_id, description from term_type WHERE name='" + name + "'");
			if (rs.next()) {
				id = rs.getInt("type_id");
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
					stmt.executeUpdate("INSERT INTO term_type (name) VALUES ('" + name + "')");
				}
				else {
					stmt.executeUpdate("INSERT INTO term_type (name, description) VALUES ('" + name + "','"
						+ description + "')");
				}
				id = getId(name, con);
				if (id == INVALID_ID) {
					throw new SQLException("Error creating type:" + name + ", " + description);
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
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT type_id from term_type WHERE name='" + name + "'");
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

	public static TypeSQL getSubseqAnnotationType(Connection con) throws SQLException {
		if (subseqAnnotationType == null) {
			subseqAnnotationType = new TypeSQL(ParametersDefault.getSubseqAnnotationTypeName(), null, con);
		}
		return subseqAnnotationType;
	}

	public static TypeSQL getDbxrefAnnotationType(Connection con) throws SQLException {
		if (dbxrefAnnotationType == null) {
			dbxrefAnnotationType = new TypeSQL(ParametersDefault.getDbxrefAnnotationTypeName(), null, con);
		}
		return dbxrefAnnotationType;
	}

	public static TypeSQL getFunctionAnnotationType(Connection con) throws SQLException {
		if (functionAnnotationType == null) {
			functionAnnotationType = new TypeSQL(ParametersDefault.getFunctionAnnotationTypeName(), null, con);
		}
		return functionAnnotationType;
	}

	public static TypeSQL getDbxrefSourceAnnotationType(Connection con) throws SQLException {
		if (dbxrefSourceAnnotationType == null) {
			dbxrefSourceAnnotationType = new TypeSQL(ParametersDefault.getDbxrefSourceAnnotationTypeName(), null, con);
		}
		return dbxrefSourceAnnotationType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TypeSQL)) {
			return false;
		}
		TypeSQL type = (TypeSQL) obj;

		return this.getName().equals(type.getName());
	}

}
