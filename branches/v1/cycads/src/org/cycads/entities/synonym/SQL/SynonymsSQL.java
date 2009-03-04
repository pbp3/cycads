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

public class SynonymsSQL
{
	String		tableName, idFieldName;
	int			idSynonymSource;
	Connection	con;

	public SynonymsSQL(int idSynonymSource, String tableName, String idFieldName, Connection con) {
		this.idSynonymSource = idSynonymSource;
		this.tableName = tableName;
		this.idFieldName = idFieldName;
		this.con = con;
	}

	public Collection<DbxrefSQL> getSynonyms() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_id from " + tableName + " where " + idFieldName + "="
				+ idSynonymSource);
			ArrayList<DbxrefSQL> dbxrefs = new ArrayList<DbxrefSQL>();
			while (rs.next()) {
				dbxrefs.add(new DbxrefSQL(rs.getInt("dbxref_id"), con));
			}
			return dbxrefs;
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

	public Collection<DbxrefSQL> getSynonyms(String dbName) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_id from " + tableName + " S ,dbxref X where S." + idFieldName + "="
				+ idSynonymSource + " AND S.dbxref_id=X.dbxref_id AND X.dbname='" + dbName + "'");
			ArrayList<DbxrefSQL> dbxrefs = new ArrayList<DbxrefSQL>();
			while (rs.next()) {
				dbxrefs.add(new DbxrefSQL(rs.getInt("dbxref_id"), con));
			}
			return dbxrefs;
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

	public DbxrefSQL getSynonym(String dbName, String accession) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_id from " + tableName + " S ,dbxref X where S." + idFieldName + "="
				+ idSynonymSource + " AND S.dbxref_id=X.dbxref_id AND X.dbname='" + dbName + "' AND X.accession='"
				+ accession + "'");
			DbxrefSQL dbxref = null;
			if (rs.next()) {
				dbxref = new DbxrefSQL(rs.getInt("dbxref_id"), con);
			}
			return dbxref;
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

	public DbxrefSQL addSynonym(String dbName, String accession) throws SQLException {
		if (getSynonym(dbName, accession) != null) {
			throw new SQLException("Synonym already exists: (" + idSynonymSource + "," + dbName + "," + accession + ")");
		}
		DbxrefSQL dbxref = new DbxrefSQL(dbName, accession, con);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO " + tableName + " (" + idFieldName + ", dbxref_id) VALUES("
				+ idSynonymSource + "," + dbxref.getId() + ")");
			return dbxref;
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

	public void addSynonym(DbxrefSQL dbxref) throws SQLException {
		if (isSynonym(dbxref)) {
			throw new SQLException("Synonym already exists: (" + idSynonymSource + "," + dbxref.getDbName() + ","
				+ dbxref.getAccession() + ")");
		}
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO " + tableName + " (" + idFieldName + ", dbxref_id) VALUES("
				+ idSynonymSource + "," + dbxref.getId() + ")");
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

	public boolean isSynonym(DbxrefSQL dbxref) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from " + tableName + " where " + idFieldName + "=" + idSynonymSource
				+ " AND dbxref_id=" + dbxref.getId());
			boolean ret = (rs.next());
			return ret;
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

	public boolean isSynonym(String dbName, String accession) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT dbxref_id from " + tableName + " S ,dbxref X where S." + idFieldName + "="
				+ idSynonymSource + " AND S.dbxref_id=X.dbxref_id AND X.dbname='" + dbName + "' AND X.accession='"
				+ accession + "'");
			boolean ret = (rs.next());
			return ret;
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
