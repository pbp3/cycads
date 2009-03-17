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

import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class DbxrefDbxrefAnnotationSQL extends AnnotationSQL
		implements DbxrefDbxrefAnnotation<DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			dbxrefSourceId;
	private int			dbxrefTargetId;
	private DbxrefSQL	dbxrefSource;
	private DbxrefSQL	dbxrefTarget;

	public DbxrefDbxrefAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_source, dbxref_target from dbxref_dbxref_annotation WHERE annotation_id="
				+ id);
			if (rs.next()) {
				dbxrefSourceId = rs.getInt("dbxref_source");
				dbxrefTargetId = rs.getInt("dbxref_target");
			}
			else {
				throw new SQLException("DbxrefDbxrefAnnotation does not exist:" + id);
			}
			rs = stmt.executeQuery("SELECT type_id from Annotation_type WHERE annotation_id=" + id + " AND type_id="
				+ TypeSQL.getDbxrefAnnotationType(con).getId());
			if (!rs.next()) {
				throw new SQLException("Annotation don't have the correct type: " + id);
			}
			rs = stmt.executeQuery("SELECT type_id from Annotation_type WHERE annotation_id=" + id + " AND type_id="
				+ TypeSQL.getDbxrefSourceAnnotationType(con).getId());
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

	public static int createDbxrefDbxrefAnnotationSQL(AnnotationMethodSQL method, DbxrefSQL dbxrefSource,
			DbxrefSQL dbxrefTarget, Connection con) throws SQLException {
		int id = AnnotationSQL.createAnnotationSQL(method, con);

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO dbxref_dbxref_annotation (annotation_id, dbxref_source, dbxref_target) VALUES ("
				+ id + "," + dbxrefSource.getId() + "," + dbxrefTarget.getId() + ")");
			stmt.executeUpdate("INSERT INTO Annotation_type (annotation_id, type_id) VALUES (" + id + ","
				+ TypeSQL.getDbxrefSourceAnnotationType(con).getId() + ")");
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
	public DbxrefSQL getDbxrefSource() {
		if (dbxrefSource == null) {
			try {
				dbxrefSource = new DbxrefSQL(getDbxrefSourceId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return dbxrefSource;
	}

	@Override
	public DbxrefSQL getDbxrefTarget() {
		if (dbxrefTarget == null) {
			try {
				dbxrefTarget = new DbxrefSQL(getDbxrefTargetId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return dbxrefTarget;
	}

	public int getDbxrefSourceId() {
		return dbxrefSourceId;
	}

	public int getDbxrefTargetId() {
		return dbxrefTargetId;
	}

	public static Collection<DbxrefDbxrefAnnotationSQL> getAnnotations(AnnotationMethodSQL method,
			Collection<TypeSQL> types, DbxrefSQL synonym, DbxrefSQL dbxref, String extraClauseFrom,
			String extraClauseWhere, Connection con) {

		StringBuffer query = new StringBuffer("SELECT distinct(XXA.annotation_id) FROM dbxref_dbxref_annotation XXA");
		StringBuffer clauseWhere = getWhere(method, types, synonym, dbxref, extraClauseWhere);
		query.append(getFrom(method, types, synonym, extraClauseFrom)).append(clauseWhere);

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			ArrayList<DbxrefDbxrefAnnotationSQL> xxas = new ArrayList<DbxrefDbxrefAnnotationSQL>();
			while (rs.next()) {
				xxas.add(new DbxrefDbxrefAnnotationSQL(rs.getInt("annotation_id"), con));
			}
			return xxas;
		}
		catch (SQLException e) {
			System.err.println(query);
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
			DbxrefSQL dbxref, String extraClauseWhere) {
		StringBuffer where = new StringBuffer("");
		if (method != null) {
			where.append(" A.annotation_id=XXA.annotation_id AND A.annotation_method_id=" + method.getId());
		}
		if (types != null && !types.isEmpty()) {
			if (where.length() > 0) {
				where.append(" AND");
			}
			where.append(" AT.annotation_id=XXA.annotation_id AND AT.type_id");
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
			where.append(" AS.annotation_id=XXA.annotation_id AND AS.dbxref_id=" + synonym.getId());
		}
		if (dbxref != null) {
			if (where.length() > 0) {
				where.append(" AND");
			}
			where.append(" XXA.dbxref_target=" + dbxref.getId());
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
