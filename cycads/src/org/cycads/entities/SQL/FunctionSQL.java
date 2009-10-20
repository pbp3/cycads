/*
 * Created on 25/02/2009
 */
package org.cycads.entities.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.cycads.entities.Function;
import org.cycads.entities.note.SQL.TypeSQL;

public class FunctionSQL extends TypeSQL implements Function
{

	private static Hashtable<String, FunctionSQL>	hashByName	= new Hashtable<String, FunctionSQL>();
	private static Hashtable<Integer, FunctionSQL>	hashById	= new Hashtable<Integer, FunctionSQL>();

	protected FunctionSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!isFunction(this, con)) {
			makeFunction(this, con);
		}
	}

	protected FunctionSQL(String name, Connection con) throws SQLException {
		super(name, con);
		if (!isFunction(this, con)) {
			makeFunction(this, con);
		}
	}

	public static boolean isFunction(String typeName, Connection con) {
		return isFunction(TypeSQL.getType(typeName, con), con);
	}

	public static boolean isFunction(int typeId, Connection con) throws SQLException {
		return isFunction(TypeSQL.getType(typeId, con), con);
	}

	public static boolean isFunction(TypeSQL type, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from Biofunction WHERE function_id=?");
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

	public static void makeFunction(TypeSQL type, Connection con) {
		if (!isFunction(type, con)) {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO Biofunction (function_id) VALUES (?)");
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

	public static FunctionSQL getFunction(String name, Connection con) throws SQLException {
		FunctionSQL ret = hashByName.get(name);
		if (ret == null) {
			ret = new FunctionSQL(name, con);
			hashByName.put(name, ret);
		}
		return ret;
	}

	public static FunctionSQL getFunction(int id, Connection con) throws SQLException {
		FunctionSQL ret = hashById.get(id);
		if (ret == null) {
			ret = new FunctionSQL(id, con);
			hashById.put(id, ret);
		}
		return ret;
	}

	@Override
	public TypeSQL getEntityType() {
		return getEntityType(getConnection());
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(TypeSQL.FUNCTION, con);
	}

}
