/*
 * Created on 05/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.annotation.SubseqFunctionAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.FunctionSQL;

public class SubseqFunctionAnnotationSQL extends SubseqAnnotationSQL
		implements
		SubseqFunctionAnnotation<SubsequenceSQL, SubseqAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			functionId;
	private FunctionSQL	function;

	public SubseqFunctionAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT function from subseq_function_annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				functionId = rs.getInt("function");
			}
			else {
				throw new SQLException("SubseqFunctionAnnotation does not exist:" + id);
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

	public static int createSubseqFunctionAnnotationSQL(SubseqAnnotationSQL parent, TypeSQL type,
			AnnotationMethodSQL method, SubsequenceSQL subsequence, FunctionSQL function, Connection con)
			throws SQLException {

		int id = SubseqAnnotationSQL.createSubseqAnnotationSQL(parent, type, method, subsequence, con);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subseq_function_annotation (annotation_id, function) VALUES (" + id + ","
				+ function.getId() + ")");
			return id;
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
	public FunctionSQL getFunction() {
		if (function == null) {
			try {
				function = new FunctionSQL(getFunctionId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return function;
	}

	public int getFunctionId() {
		return functionId;
	}

}
