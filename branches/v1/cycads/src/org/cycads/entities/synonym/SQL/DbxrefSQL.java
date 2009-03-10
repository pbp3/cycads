/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.DbxrefDbxrefAnnotationSQL;
import org.cycads.entities.annotation.SQL.SubseqAnnotationSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Dbxref;

public class DbxrefSQL extends HasSynonymsNotebleSQL
		implements Dbxref<DbxrefDbxrefAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	public final static int	INVALID_ID	= -1;
	private String			dbName;
	private String			accession;
	private int				id;
	private Connection		con;

	public DbxrefSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbname, accession from dbxref WHERE dbxref_id=" + id);
			if (rs.next()) {
				dbName = rs.getString("dbname");
				accession = rs.getString("accession");
			}
			else {
				throw new SQLException("Dbxref does not exist:" + id);
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

	public DbxrefSQL(String dbName, String accession, Connection con) throws SQLException {
		this.dbName = dbName;
		this.accession = accession;
		this.con = con;
		this.id = getId(dbName, accession, con);
		if (this.id == INVALID_ID) {
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO dbxref (dbname, accession) VALUES ('" + dbName + "','" + accession
					+ "')");
				this.id = getId(dbName, accession, con);
				if (this.id == INVALID_ID) {
					throw new SQLException("Error creating dbxref:" + dbName + ", " + accession);
				}
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

	public static int getId(String dbName, String accession, Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_id from dbxref WHERE dbname='" + dbName + "' AND accession='"
				+ accession + "'");
			int id = INVALID_ID;
			if (rs.next()) {
				id = rs.getInt("dbxref_id");
			}
			return id;
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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getAccession() {
		return accession;
	}

	@Override
	public String getDbName() {
		return dbName;
	}

	@Override
	public SynonymsSQL getSynonymsSQL() {
		if (synonymsSQL == null) {
			synonymsSQL = new SynonymsSQL(getId(), getSynonymTableName(), "dbxref_id1", getConnection());
		}
		return synonymsSQL;
	}

	@Override
	public String getSynonymTableName() {
		return "dbxref_synonym";
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public String getIdFieldName() {
		return "dbxref_id";
	}

	@Override
	public String getNoteTableName() {
		return "dbxref_note";
	}

	@Override
	public DbxrefSQL addSynonym(String dbName, String accession) {
		DbxrefSQL dbxref = super.addSynonym(dbName, accession);
		try {
			dbxref.getSynonymsSQL().addSynonym(this);
			return dbxref;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return getDbName() + ":" + getAccession();
	}

	@Override
	public Collection<DbxrefDbxrefAnnotationSQL> getDbxrefDbxrefAnnotations(DbxrefSQL synonym) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT SSA.annotation_id from subseq_annotation SSA, Annotation_synonym AS"
				+ " WHERE SSA.subsequence=" + getId() + " AND SSA.annotation_id=AS.annotation_id AND AS.dbxref_id="
				+ synonym.getId());
			ArrayList<SubseqAnnotationSQL> ssas = new ArrayList<SubseqAnnotationSQL>();
			while (rs.next()) {
				ssas.add(new SubseqAnnotationSQL(rs.getInt("annotation_id"), getConnection()));
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

	@Override
	public Collection<DbxrefDbxrefAnnotationSQL> getDbxrefDbxrefAnnotations(TypeSQL type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DbxrefDbxrefAnnotationSQL> getDbxrefDbxrefAnnotations(AnnotationMethodSQL method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DbxrefDbxrefAnnotationSQL> getDbxrefDbxrefAnnotations(AnnotationMethodSQL method, TypeSQL type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DbxrefDbxrefAnnotationSQL> getDbxrefDbxrefAnnotations(
			AnnotationFilter<DbxrefDbxrefAnnotationSQL> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
