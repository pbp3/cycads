/*
 * Created on 25/02/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import org.cycads.entities.Function;

public class FunctionSQL implements Function, Comparable<Function>
{
	public final static int	INVALID_ID	= -1;
	private int				id;

	private String			name;
	private String			description;

	private Connection		con;

	public FunctionSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT name, description from biofunction WHERE function_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				name = rs.getString("name");
				description = rs.getString("description");
			}
			else {
				throw new SQLException("Dbxref does not exist:" + id);
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

	public FunctionSQL(String name, String description, Connection con) throws SQLException {
		this.name = name;
		this.description = description;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT function_id, description from biofunction WHERE name=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
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
					stmt = con.prepareStatement("INSERT INTO biofunction (name) VALUES (?)",
						Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, name);
				}
				else {
					stmt = con.prepareStatement("INSERT INTO biofunction (name, description) VALUES (?,?)");
					stmt.setString(1, name);
					stmt.setString(2, description);
				}
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
				else {
					throw new SQLException("Function:" + name + ", " + description + " didn't return the id on insert.");
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
			stmt = con.prepareStatement("SELECT function_id from biofunction WHERE name=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			int id = INVALID_ID;
			if (rs.next()) {
				id = rs.getInt("function_id");
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

	@Override
	public Collection<Function> getSynonyms() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT function_id2 from function_synonym WHERE function_id1=?");
			stmt.setInt(1, getId());
			rs = stmt.executeQuery();
			HashSet<Function> functions = new HashSet<Function>();
			while (rs.next()) {
				functions.add(new FunctionSQL(rs.getInt("function_id2"), con));
			}
			return functions;
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

	@Override
	public Function getSynonym(String name) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT F.function_id2 from function_synonym S, biofunction F WHERE S.function_id1=?"
				+ " AND S.function_id2=F.function_id AND F.name=?");
			stmt.setInt(1, getId());
			stmt.setString(2, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new FunctionSQL(rs.getInt("function_id2"), con);
			}
			return null;
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

	@Override
	public Function addSynonym(String name, String description) {
		PreparedStatement stmt = null;
		try {
			if (getSynonym(name) != null) {
				throw new SQLException("Synonym already exists: (" + getId() + "," + name + ")");
			}
			FunctionSQL function = new FunctionSQL(name, description, con);
			stmt = con.prepareStatement("INSERT INTO function_synonym (function_id1, function_id2) VALUES(?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, function.getId());
			stmt.executeUpdate();
			stmt = con.prepareStatement("INSERT INTO function_synonym (function_id2, function_id1) VALUES(?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, function.getId());
			stmt.executeUpdate();
			return function;
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
	public int compareTo(Function o) {
		return name.compareTo(getDescription());
	}

}
