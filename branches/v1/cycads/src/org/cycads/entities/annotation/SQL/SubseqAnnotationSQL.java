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

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class SubseqAnnotationSQL extends AnnotationSQL
		implements SubseqAnnotation<SubsequenceSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private static TypeSQL	typeDefault	= null;
	private int				subseqId;
	private SubsequenceSQL	subsequence;

	public SubseqAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT subsequence_id from subseq_annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				subseqId = rs.getInt("subsequence_id");
			}
			else {
				throw new SQLException("SubsequenceAnnotation does not exist:" + id);
			}
			rs = stmt.executeQuery("SELECT type_id from Annotation_type WHERE annotation_id=" + id + " AND type_id="
				+ TypeSQL.getSubseqAnnotationType(con).getId());
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

	public static int createSubseqAnnotationSQL(AnnotationMethodSQL method, SubsequenceSQL subsequence, Connection con)
			throws SQLException {
		int id = AnnotationSQL.createAnnotationSQL(method, con);

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subseq_annotation (annotation_id, subsequence_id) VALUES (" + id + ","
				+ subsequence.getId() + ")");
			stmt.executeUpdate("INSERT INTO Annotation_type (annotation_id, type_id) VALUES (" + id + ","
				+ TypeSQL.getSubseqAnnotationType(con).getId() + ")");
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
	public SubsequenceSQL getSubsequence() {
		if (subsequence == null) {
			try {
				subsequence = new SubsequenceSQL(getSubseqId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return subsequence;
	}

	public int getSubseqId() {
		return subseqId;
	}

	public static Collection<SubseqAnnotationSQL> getAnnotations(AnnotationMethodSQL method, Collection<TypeSQL> types,
			DbxrefSQL synonym, String clauseFrom, String clauseWhere, Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT SSA.annotation_id FROM " + clauseFrom + " WHERE " + clauseWhere);
			ArrayList<SubseqAnnotationSQL> ssas = new ArrayList<SubseqAnnotationSQL>();
			while (rs.next()) {
				ssas.add(new SubseqAnnotationSQL(rs.getInt("annotation_id"), con));
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

	protected static StringBuffer getFrom(AnnotationMethodSQL method, Collection<TypeSQL> types, DbxrefSQL synonym,
			String clauseFrom) {
		StringBuffer from = new StringBuffer("subseq_annotation SSA");
		if (method != null) {
			from.append(", Annotation A");
		}
		if (types != null && !types.isEmpty()) {
			from.append(", Annotation_type AT");
		}
		if (synonym != null) {
			from.append(", Annotation_synonym AS");
		}
		return from.append(clauseFrom);
	}

}
