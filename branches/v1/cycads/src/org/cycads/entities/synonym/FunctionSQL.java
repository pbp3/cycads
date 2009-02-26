/*
 * Created on 25/02/2009
 */
package org.cycads.entities.synonym;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

public class FunctionSQL implements Function, Comparable<Function>
{
	public final static int	INVALID_ID	= -1;
	int						id;

	String					name;
	String					description;

	Connection				con;

	public FunctionSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT name, description from biofunction WHERE function_id=" + id);
		if (rs.next()) {
			name = rs.getString("name");
			description = rs.getString("description");
		}
		else {
			throw new SQLException("Dbxref does not exist:" + id);
		}
		stmt.close();
	}

	public FunctionSQL(String name, String description, Connection con) throws SQLException {
		this.name = name;
		this.description = description;
		this.con = con;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT function_id, description from biofunction WHERE name='" + name + "'");
		if (rs.next()) {
			id = rs.getInt("function_id");
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
				stmt.executeUpdate("INSERT INTO biofunction (name) VALUES ('" + name + "')");
			}
			else {
				stmt.executeUpdate("INSERT INTO biofunction (name, description) VALUES ('" + name + "','" + description
					+ "')");
			}
			id = getId(name, con);
			if (id == INVALID_ID) {
				throw new SQLException("Error creating function:" + name + ", " + description);
			}
		}
		stmt.close();
	}

	public static int getId(String name, Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT function_id from biofunction WHERE name='" + name + "'");
		int id = INVALID_ID;
		if (rs.next()) {
			id = rs.getInt("function_id");
		}
		stmt.close();
		return id;
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

	@Override
	public Collection<Function> getSynonyms() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT function_id2 from function_synonym WHERE function_id1=" + getId());
			HashSet<Function> functions = new HashSet<Function>();
			while (rs.next()) {
				functions.add(new FunctionSQL(rs.getInt("function_id2"), con));
			}
			stmt.close();
			return functions;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Function getSynonym(String name) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT F.function_id2 from function_synonym S, biofunction F WHERE S.function_id1="
				+ getId() + " AND S.function_id2=F.function_id AND F.name='" + name + "'");
			if (rs.next()) {
				return new FunctionSQL(rs.getInt("function_id2"), con);
			}
			stmt.close();
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Function addSynonym(String name, String description) {
		try {
			if (getSynonym(name) != null) {
				throw new SQLException("Synonym already exists: (" + getId() + "," + name + ")");
			}
			FunctionSQL function = new FunctionSQL(name, description, con);
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO function_synonym (function_id1, function_id2) VALUES(" + this.getId() + ","
				+ function.getId() + ")");
			stmt.executeUpdate("INSERT INTO function_synonym (function_id2, function_id1) VALUES(" + this.getId() + ","
				+ function.getId() + ")");
			stmt.close();
			return function;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int compareTo(Function o) {
		return name.compareTo(getDescription());
	}

}
