/*
 * Created on 03/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.SQL.TypeSQL;

public class AnnotationMethodSQL extends TypeSQL implements AnnotationMethod
{
	private static Hashtable<String, AnnotationMethodSQL>	hashByName	= new Hashtable<String, AnnotationMethodSQL>();
	private static Hashtable<Integer, AnnotationMethodSQL>	hashById	= new Hashtable<Integer, AnnotationMethodSQL>();

	//	private double											weight		= ParametersDefault.getMethodWeightDefault();

	private AnnotationMethodSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!isMethod(this, con)) {
			makeMethod(this, con);
		}
	}

	private AnnotationMethodSQL(String name, Connection con) throws SQLException {
		super(name, con);
		if (!isMethod(this, con)) {
			makeMethod(this, con);
		}
	}

	public static boolean isMethod(String typeName, Connection con) {
		return isMethod(TypeSQL.getType(typeName, con), con);
	}

	public static boolean isMethod(int typeId, Connection con) throws SQLException {
		return isMethod(TypeSQL.getType(typeId, con), con);
	}

	public static boolean isMethod(TypeSQL type, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * FROM Method WHERE method_id=?");
			stmt.setInt(1, type.getId());
			rs = stmt.executeQuery();
			return rs.next();
		}
		catch (SQLException e) {
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

	public static void makeMethod(TypeSQL type, Connection con) {
		if (!isMethod(type, con)) {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO Method (method_id) VALUES (?)");
				stmt.setInt(1, type.getId());
				stmt.executeUpdate();
			}
			catch (SQLException e) {
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

	public static AnnotationMethodSQL getMethod(AnnotationMethod method, Connection con) throws SQLException {
		if (method == null) {
			return null;
		}
		if (method instanceof AnnotationMethodSQL) {
			return (AnnotationMethodSQL) method;
		}
		else {
			return getMethod(method.getName(), con);
		}
	}

	//	@Override
	//	public double getWeight() {
	//		return weight;
	//	}
	//
	//	@Override
	//	public void setWeight(double weight) {
	//		this.weight = weight;
	//	}
	//
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
	public String getEntityTypeName() {
		return AnnotationMethod.ENTITY_TYPE_NAME;
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(AnnotationMethod.ENTITY_TYPE_NAME, con);
	}

}
