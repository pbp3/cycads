/*
 * Created on 05/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.SubsequenceSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class SubseqAnnotationSQL extends AnnotationSQL<SubseqAnnotationSQL>
		implements SubseqAnnotation<SubsequenceSQL, SubseqAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int				subseqId;
	private SubsequenceSQL	subsequence;

	public SubseqAnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT subsequence from subseq_annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				subseqId = rs.getInt("subsequence");
			}
			else {
				throw new SQLException("SubsequenceAnnotation does not exist:" + id);
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

	public static int createSubseqAnnotationSQL(SubseqAnnotationSQL parent, TypeSQL type, AnnotationMethodSQL method,
			SubsequenceSQL subsequence, Connection con) throws SQLException {
		int id = AnnotationSQL.createAnnotationSQL(parent, type, method, con);

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subseq_annotation (annotation_id, subsequence) VALUES (" + id + ","
				+ subsequence.getId() + ")");
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
	protected SubseqAnnotationSQL createParent() throws SQLException {
		return new SubseqAnnotationSQL(getParentId(), getConnection());
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

}
