/*
 * Created on 25/02/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Database;

public class DatabaseSQL extends TypeSQL implements Database
{

	private static Hashtable<String, DatabaseSQL>	hashByName	= new Hashtable<String, DatabaseSQL>();
	private static Hashtable<Integer, DatabaseSQL>	hashById	= new Hashtable<Integer, DatabaseSQL>();

	protected DatabaseSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!isDbname(this, con)) {
			makeDbname(this, con);
		}
	}

	protected DatabaseSQL(String name, Connection con) throws SQLException {
		super(name, con);
		if (!isDbname(this, con)) {
			makeDbname(this, con);
		}
	}

	public static boolean isDbname(String typeName, Connection con) {
		return isDbname(TypeSQL.getType(typeName, con), con);
	}

	public static boolean isDbname(int typeId, Connection con) throws SQLException {
		return isDbname(TypeSQL.getType(typeId, con), con);
	}

	public static boolean isDbname(TypeSQL type, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from External_DB WHERE external_db_id=?");
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

	public static void makeDbname(TypeSQL type, Connection con) {
		if (!isDbname(type, con)) {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO External_DB (external_db_id) VALUES (?)");
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

	public static DatabaseSQL getDB(String name, Connection con) throws SQLException {
		DatabaseSQL ret = hashByName.get(name);
		if (ret == null) {
			ret = new DatabaseSQL(name, con);
			hashByName.put(name, ret);
		}
		return ret;
	}

	public static DatabaseSQL getDB(int id, Connection con) throws SQLException {
		DatabaseSQL ret = hashById.get(id);
		if (ret == null) {
			ret = new DatabaseSQL(id, con);
			hashById.put(id, ret);
		}
		return ret;
	}

	@Override
	public TypeSQL getEntityType() {
		return getObjectType(getConnection());
	}

	public static TypeSQL getObjectType(Connection con) {
		return TypeSQL.getType(TypeSQL.DATABASE, con);
	}

}
