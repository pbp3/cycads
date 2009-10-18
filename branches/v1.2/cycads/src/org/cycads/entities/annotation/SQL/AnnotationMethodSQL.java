/*
 * Created on 03/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.SQL.NotebleSQL;

public class AnnotationMethodSQL extends NotebleSQL implements AnnotationMethod
{
	private static Hashtable<String, AnnotationMethodSQL>	hashByString	= new Hastable<String, AnnotationMethodSQL>();

	public final static int									INVALID_ID		= -1;
	//	public final static int		WEIGHT_DEAFULT	= 1;

	private int												id;
	private double											weight;
	private String											name;
	private final Connection								con;

	public AnnotationMethodSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT name from annotation_method WHERE annotation_method_id=" + id);
			if (rs.next()) {
				name = rs.getString("name");
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
		this.id = getId(name, con);
		if (this.id == INVALID_ID) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.prepareStatement("INSERT INTO annotation_method (name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
				else {
					throw new SQLException("Error creating method:" + name);
				}
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

	public static int getId(String name, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT annotation_method_id from annotation_method WHERE name=?");
			rs = stmt.executeQuery();
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
		this.weight = weight;
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
