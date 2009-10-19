/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;

public class SynonymsSQL
{
	private int			sourceId;
	private int			sourceTypeId;
	private Connection	con;

	public SynonymsSQL(int sourceId, Type sourceType, Connection con) {
		this.sourceId = sourceId;
		this.con = con;
		if (sourceType instanceof TypeSQL) {
			this.sourceTypeId = ((TypeSQL) sourceType).getId();
		}
		else {
			this.sourceTypeId = TypeSQL.getType(sourceType.getName(), con).getId();
		}
	}

	public Collection<DbxrefSQL> getSynonyms() throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT dbxref_id from Synonym where source_id=? and source_type_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			rs = stmt.executeQuery();
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT X.dbxref_id from Synonym S ,dbxref X where S.source_id=? and S.source_type_id=?"
				+ " AND S.dbxref_id=X.dbxref_id AND X.dbname=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setString(3, dbName);
			rs = stmt.executeQuery();
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
		DbxrefSQL dbxref = new DbxrefSQL(dbName, accession, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from Synonym where source_id=? and source_type_id=? AND dbxref_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, dbxref.getId());
			rs = stmt.executeQuery();
			if (!rs.next()) {
				return null;
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
			return null;
		}
		DbxrefSQL dbxref = new DbxrefSQL(dbName, accession, con);
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Synonym (source_id, source_type_id, dbxref_id) VALUES(?,?,?)");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, dbxref.getId());
			stmt.executeUpdate();
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
		if (!isSynonym(dbxref)) {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO Synonym (source_id, source_type_id, dbxref_id) VALUES(?,?,?)");
				stmt.setInt(1, sourceId);
				stmt.setInt(2, sourceTypeId);
				stmt.setInt(3, dbxref.getId());
				stmt.executeUpdate();
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

	public boolean isSynonym(DbxrefSQL dbxref) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT * from Synonym where source_id=? AND source_type_id=? AND dbxref_id=?");
			stmt.setInt(1, sourceId);
			stmt.setInt(2, sourceTypeId);
			stmt.setInt(3, dbxref.getId());
			rs = stmt.executeQuery();
			return rs.next();
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
		return (getSynonym(dbName, accession) != null);
	}

}
