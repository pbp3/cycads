/*
 * Created on 05/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.annotation.SubseqDbxrefAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class SubseqDbxrefAnnotationSQL extends SubseqAnnotationSQL
		implements SubseqDbxrefAnnotation<SubsequenceSQL, SubseqAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			dbxrefId;
	private DbxrefSQL	dbxref;

	public SubseqDbxrefAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref from subseq_dbxref_annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				dbxrefId = rs.getInt("dbxref");
			}
			else {
				throw new SQLException("SubseqDbxrefAnnotation does not exist:" + id);
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

	public static int createSubseqDbxrefAnnotationSQL(SubseqAnnotationSQL parent, TypeSQL type,
			AnnotationMethodSQL method, SubsequenceSQL subsequence, DbxrefSQL dbxref, Connection con)
			throws SQLException {

		int id = SubseqAnnotationSQL.createSubseqAnnotationSQL(parent, type, method, subsequence, con);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subseq_dbxref_annotation (annotation_id, dbxref) VALUES (" + id + ","
				+ dbxref.getId() + ")");
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

}
