/*
 * Created on 25/02/2009
 */
package org.cycads.entities.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.cycads.entities.Feature;
import org.cycads.entities.note.SQL.TypeSQL;

public class FeatureSQL extends TypeSQL implements Feature
{

	private static Hashtable<String, FeatureSQL>	hashByName	= new Hashtable<String, FeatureSQL>();
	private static Hashtable<Integer, FeatureSQL>	hashById	= new Hashtable<Integer, FeatureSQL>();

	protected FeatureSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!isFeature(this, con)) {
			makeFeature(this, con);
		}
	}

	protected FeatureSQL(String name, Connection con) throws SQLException {
		super(name, con);
		if (!isFeature(this, con)) {
			makeFeature(this, con);
		}
	}

	public static boolean isFeature(String typeName, Connection con) {
		return isFeature(TypeSQL.getType(typeName, con), con);
	}

	public static boolean isFeature(int typeId, Connection con) throws SQLException {
		return isFeature(TypeSQL.getType(typeId, con), con);
	}

	public static boolean isFeature(TypeSQL type, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from Feature WHERE feature_id=?");
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

	public static void makeFeature(TypeSQL type, Connection con) {
		if (!isFeature(type, con)) {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO Feature (feature_id) VALUES (?)");
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

	public static FeatureSQL getFeature(String name, Connection con) throws SQLException {
		FeatureSQL ret = hashByName.get(name);
		if (ret == null) {
			ret = new FeatureSQL(name, con);
			hashByName.put(name, ret);
		}
		return ret;
	}

	public static FeatureSQL getFeature(int id, Connection con) throws SQLException {
		FeatureSQL ret = hashById.get(id);
		if (ret == null) {
			ret = new FeatureSQL(id, con);
			hashById.put(id, ret);
		}
		return ret;
	}

	@Override
	public String getEntityTypeName() {
		return Feature.ENTITY_TYPE_NAME;
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(Feature.ENTITY_TYPE_NAME, con);
	}

}
