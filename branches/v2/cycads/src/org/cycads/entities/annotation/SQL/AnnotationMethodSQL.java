/*
 * Created on 03/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.SQL.NotebleSQL;

public class AnnotationMethodSQL extends NotebleSQL implements AnnotationMethod
{
	public final static int		INVALID_ID		= -1;
	public final static int		WEIGHT_DEAFULT	= 1;

	private int					id;
	private double				weight;
	private String				name;
	private final Connection	con;

	public AnnotationMethodSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT name, last_weight from annotation_method WHERE annotation_method_id=" + id);
			if (rs.next()) {
				name = rs.getString("name");
				weight = rs.getDouble("last_weight");
			}
			else {
				throw new SQLException("Method does not exist:" + id);
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

	public AnnotationMethodSQL(String name, Connection con) throws SQLException {
		this.name = name;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT annotation_method_id,last_weight from annotation_method WHERE name='" + name
				+ "'");
			if (rs.next()) {
				// if already exists, just get the id and the weight
				this.id = rs.getInt("annotation_method_id");
				this.weight = rs.getDouble("last_weight");
			}
			else {
				stmt.executeUpdate("INSERT INTO annotation_method (name, last_weight) VALUES ('" + name + "',"
					+ WEIGHT_DEAFULT + ")");
				this.id = getId(name, con);
				if (this.id == INVALID_ID) {
					throw new SQLException("Error creating annotation_method:" + name);
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

	public AnnotationMethodSQL(String name, int weight, Connection con) throws SQLException {
		this.name = name;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT annotation_method_id,last_weight from annotation_method WHERE name='" + name
				+ "'");
			if (rs.next()) {
				// if already exists, just update the weight and get the id
				id = rs.getInt("annotation_method_id");
				this.weight = rs.getDouble("last_weight");
				setWeight(weight);
			}
			else {
				stmt.executeUpdate("INSERT INTO annotation_method (name, last_weight) VALUES ('" + name + "'," + weight
					+ ")");
				this.weight = weight;
				this.id = getId(name, con);
				if (this.id == INVALID_ID) {
					throw new SQLException("Error creating annotation_method:" + name);
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

			rs = stmt.executeQuery("SELECT annotation_method_id from annotation_method WHERE name='" + name + "'");
			int id = INVALID_ID;
			if (rs.next()) {
				id = rs.getInt("annotation_method_id");
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
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		if (weight != this.weight) {
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("UPDATE annotation_method SET last_weight=" + weight
					+ " WHERE annotation_method_id=" + getId());
				this.weight = weight;
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
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public String getIdFieldName() {
		return "annotation_method_id";
	}

	@Override
	public String getNoteTableName() {
		return "annotation_method_note";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AnnotationMethodSQL) {
			return getId() == ((AnnotationMethodSQL) obj).getId();
		}
		if (obj instanceof AnnotationMethod) {
			return getName().equals(((AnnotationMethod) obj).getName());
		}
		return false;
	}

	@Override
	public int compareTo(AnnotationMethod o) {
		return getName().compareTo(o.getName());
	}

}
