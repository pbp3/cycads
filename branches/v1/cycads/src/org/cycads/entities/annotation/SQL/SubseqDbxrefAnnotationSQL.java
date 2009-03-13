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

import org.cycads.entities.annotation.SubseqDbxrefAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class SubseqDbxrefAnnotationSQL extends SubseqAnnotationSQL
		implements SubseqDbxrefAnnotation<SubsequenceSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			dbxrefId;
	private DbxrefSQL	dbxref;

	public SubseqDbxrefAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_id from subseq_dbxref_annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				dbxrefId = rs.getInt("dbxref_id");
			}
			else {
				throw new SQLException("SubseqDbxrefAnnotation does not exist:" + id);
			}
			rs = stmt.executeQuery("SELECT type_id from Annotation_type WHERE annotation_id=" + id + " AND type_id="
				+ TypeSQL.getDbxrefAnnotationType(con).getId());
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

	public static int createSubseqDbxrefAnnotationSQL(AnnotationMethodSQL method, SubsequenceSQL subsequence,
			DbxrefSQL dbxref, Connection con) throws SQLException {

		int id = SubseqAnnotationSQL.createSubseqAnnotationSQL(method, subsequence, con);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subseq_dbxref_annotation (annotation_id, dbxref_id) VALUES (" + id + ","
				+ dbxref.getId() + ")");
			stmt.executeUpdate("INSERT INTO Annotation_type (annotation_id, type_id) VALUES (" + id + ","
				+ TypeSQL.getDbxrefAnnotationType(con).getId() + ")");
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
	public DbxrefSQL getDbxref() {
		if (dbxref == null) {
			try {
				dbxref = new DbxrefSQL(getDbxrefId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return dbxref;
	}

	public int getDbxrefId() {
		return dbxrefId;
	}

	public static Collection<SubseqDbxrefAnnotationSQL> getAnnotations(AnnotationMethodSQL method,
			Collection<TypeSQL> types, DbxrefSQL synonym, DbxrefSQL dbxref, String extraClauseFrom,
			String extraClauseWhere, Connection con) {
		StringBuffer clauseWhere = SubseqAnnotationSQL.getWhere(method, types, synonym, extraClauseWhere);
		asdasdasd
		StringBuffer query = new StringBuffer(
			"SELECT distinct(SSXA.annotation_id) FROM subseq_annotation SSA, subseq_dbxref_annotation SSXA");
		query.append(getFrom(method, types, synonym, extraClauseFrom)).append(clauseWhere);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			ArrayList<SubseqDbxrefAnnotationSQL> ssas = new ArrayList<SubseqDbxrefAnnotationSQL>();
			while (rs.next()) {
				ssas.add(new SubseqDbxrefAnnotationSQL(rs.getInt("annotation_id"), con));
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
			String extraClauseFrom) {
		StringBuffer from = new StringBuffer("");
		if (method != null) {
			from.append(", Annotation A");
		}
		if (types != null && !types.isEmpty()) {
			from.append(", Annotation_type AT");
		}
		if (synonym != null) {
			from.append(", Annotation_synonym AS");
		}
		return from.append(extraClauseFrom);
	}

	protected static StringBuffer getWhere(AnnotationMethodSQL method, Collection<TypeSQL> types, DbxrefSQL synonym,
			String extraClauseWhere) {
		StringBuffer where = new StringBuffer("");
		if (method != null) {
			where.append(" AND A.annotation_id=SSA.annotation_id AND A.annotation_method_id=" + method.getId());
		}
		if (types != null && !types.isEmpty()) {
			if (where.length() > 0) {
				where.append(" AND");
			}
			where.append(" AT.annotation_id=SSA.annotation_id AND AT.type_id");
			if (types.size() > 1) {
				StringBuffer whereIn = new StringBuffer("");
				for (TypeSQL type : types) {
					if (whereIn.length() > 0) {
						whereIn.append(",");
					}
					whereIn.append("" + type.getId());
				}
				where.append(" IN (").append(whereIn).append(")");
			}
			else {
				where.append("=" + types.iterator().next().getId());
			}
		}
		if (synonym != null) {
			if (where.length() > 0) {
				where.append(" AND");
			}
			where.append(" AS.annotation_id=SSA.annotation_id AND dbxref_id=" + synonym.getId());
		}
		if (where.length() > 0 && extraClauseWhere.length() > 0) {
			where.append(" AND");
		}
		where.append(extraClauseWhere);
		if (where.length() > 0) {
			where.insert(0, " WHERE");
		}
		return where;
	}

}
