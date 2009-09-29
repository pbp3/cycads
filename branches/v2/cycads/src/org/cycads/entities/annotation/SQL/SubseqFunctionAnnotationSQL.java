/*
 * Created on 05/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.SubseqFunctionAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.FunctionSQL;

public class SubseqFunctionAnnotationSQL extends SubseqAnnotationSQL
		implements SubseqFunctionAnnotation<AnnotationSQL, SubsequenceSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			functionId;
	private FunctionSQL	function;

	public SubseqFunctionAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT function_id from subseq_function_annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				functionId = rs.getInt("function_id");
			}
			else {
				throw new SQLException("SubseqFunctionAnnotation does not exist:" + id);
			}
			rs = stmt.executeQuery("SELECT type_id from Annotation_type WHERE annotation_id=" + id + " AND type_id="
				+ TypeSQL.getFunctionAnnotationType(con).getId());
			if (!rs.next()) {
				throw new SQLException("Annotation don't have the correct type: " + id);
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

	public static int createSubseqFunctionAnnotationSQL(AnnotationMethodSQL method, SubsequenceSQL subsequence,
			FunctionSQL function, Connection con) throws SQLException {

		int id = SubseqAnnotationSQL.createSubseqAnnotationSQL(method, subsequence, con);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subseq_function_annotation (annotation_id, function_id) VALUES (" + id
				+ "," + function.getId() + ")");
			stmt.executeUpdate("INSERT INTO Annotation_type (annotation_id, type_id) VALUES (" + id + ","
				+ TypeSQL.getFunctionAnnotationType(con).getId() + ")");
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

	public static Collection<SubseqFunctionAnnotationSQL> getAnnotations(AnnotationMethodSQL method,
			Collection<TypeSQL> types, DbxrefSQL synonym, FunctionSQL function, String extraClauseFrom,
			String extraClauseWhere, Connection con) {

		StringBuffer query = getQueryBasic().append(", subseq_function_annotation SSFA");

		StringBuffer clauseWhere = getWhere(method, types, synonym, extraClauseWhere);
		if (clauseWhere.length() > 0) {
			clauseWhere.append(" AND");
		}
		clauseWhere.append(" SSA.annotation_id=SSFA.annotation_id");
		if (function != null) {
			clauseWhere.append(" AND SSFA.function_id=" + function.getId());
		}
		query.append(getFrom(method, types, synonym, extraClauseFrom)).append(clauseWhere);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			ArrayList<SubseqFunctionAnnotationSQL> ssas = new ArrayList<SubseqFunctionAnnotationSQL>();
			while (rs.next()) {
				ssas.add(new SubseqFunctionAnnotationSQL(rs.getInt("annotation_id"), con));
			}
			return ssas;
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

}
