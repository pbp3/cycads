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
import org.cycads.entities.note.SQL.TypeSQL;

public class AnnotationMethodSQL extends NotebleSQL implements AnnotationMethod, EntitySQL
{
	private static Hashtable<String, AnnotationMethodSQL>	hashByName	= new Hashtable<String, AnnotationMethodSQL>();
	private static Hashtable<Integer, AnnotationMethodSQL>	hashById	= new Hashtable<Integer, AnnotationMethodSQL>();

	public final static int									INVALID_ID	= -1;
	//	public final static int		WEIGHT_DEAFULT	= 1;

	private int												id;
	private double											weight;
	private String											name;
	private final Connection								con;

	private AnnotationMethodSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT name from Method WHERE method_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
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

	private AnnotationMethodSQL(String name, Connection con) throws SQLException {
		this.name = name;
		this.con = con;
		this.id = getId(name, con);
		if (this.id == INVALID_ID) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.prepareStatement("INSERT INTO Method (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
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
			stmt = con.prepareStatement("SELECT method_id from Method WHERE name=?");
			rs = stmt.executeQuery();
			int id = INVALID_ID;
			if (rs.next()) {
				id = rs.getInt("method_id");
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

	public static AnnotationMethodSQL getMethod(String name, Connection con) throws SQLException {
		AnnotationMethodSQL ret = hashByName.get(name);
		if (ret == null) {
			ret = new AnnotationMethodSQL(name, con);
			hashByName.put(name, ret);
		}
		return ret;
	}

	public static AnnotationMethodSQL getMethod(int id, Connection con) throws SQLException {
		AnnotationMethodSQL ret = hashById.get(id);
		if (ret == null) {
			ret = new AnnotationMethodSQL(id, con);
			hashById.put(id, ret);
		}
		return ret;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	//	@Override
	//	public String getIdFieldName() {
	//		return "method_id";
	//	}

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

	@Override
	public TypeSQL getEntityType() {
		return TypeSQL.getType(OBJECT_TYPE_NAME, con);
	}

}
