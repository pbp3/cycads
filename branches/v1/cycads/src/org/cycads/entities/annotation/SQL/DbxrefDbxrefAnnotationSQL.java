/*
 * Created on 05/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class DbxrefDbxrefAnnotationSQL extends AnnotationSQL<DbxrefDbxrefAnnotationSQL>
		implements DbxrefDbxrefAnnotation<DbxrefDbxrefAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
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

	public static int createDbxrefDbxrefAnnotationSQL(DbxrefDbxrefAnnotationSQL parent, TypeSQL type,
			AnnotationMethodSQL method, DbxrefSQL dbxrefSource, DbxrefSQL dbxrefTarget, Connection con)
			throws SQLException {
		int id = AnnotationSQL.createAnnotationSQL(parent, type, method, con);

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO dbxref_dbxref_annotation (annotation_id, dbxref_source, dbxref_target) VALUES ("
				+ id + "," + dbxrefSource.getId() + "," + dbxrefTarget.getId() + ")");
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
	protected DbxrefDbxrefAnnotationSQL createParent() throws SQLException {
		return new DbxrefDbxrefAnnotationSQL(getParentId(), getConnection());
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

}