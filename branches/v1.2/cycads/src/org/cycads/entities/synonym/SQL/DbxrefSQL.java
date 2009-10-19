/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;

public class DbxrefSQL extends HasSynonymsNotebleSQL implements Dbxref
{
	public final static int		INVALID_ID	= -1;
	private DatabaseSQL			database;
	private String				accession;
	private int					id;
	private final Connection	con;

	public DbxrefSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT external_db_id, accession from dbxref WHERE dbxref_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				database = DatabaseSQL.getDB(rs.getInt("external_db_id"), con);
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
		this.database = DatabaseSQL.getDB(dbName, con);
		this.accession = accession;
		this.con = con;
		this.id = getId(getDatabase(), accession, con);
		if (this.id == INVALID_ID) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.prepareStatement("INSERT INTO dbxref (external_db_id, accession) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, database.getId());
				stmt.setString(2, accession);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
				else {
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

	public static int getId(DatabaseSQL database, String accession, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT dbxref_id from dbxref WHERE external_db_id=? AND accession=?");
			stmt.setInt(1, database.getId());
			stmt.setString(2, accession);
			rs = stmt.executeQuery();
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
	public DatabaseSQL getDatabase() {
		return database;
	}

	@Override
	public String getDbName() {
		return getDatabase().getName();
	}

	@Override
	public Connection getConnection() {
		return con;
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
	public void addSynonym(Dbxref dbxref) {
		if (dbxref instanceof DbxrefSQL) {
			super.addSynonym(dbxref);
			try {
				((DbxrefSQL) dbxref).getSynonymsSQL().addSynonym(this);
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else {
			addSynonym(dbxref.getDbName(), dbxref.getAccession());
		}
	}

	@Override
	public String toString() {
		return getDbName() + ParametersDefault.getDbxrefToStringSeparator() + getAccession();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DbxrefSQL) {
			return getId() == ((DbxrefSQL) obj).getId();
		}
		if (obj instanceof Dbxref) {
			DbxrefSQL x = (DbxrefSQL) obj;
			return getDbName().equals(x.getDbName()) && getAccession().equals(x.getAccession());
		}
		return false;
	}

	@Override
	public TypeSQL getEntityType() {
		return getObjectType(getConnection());
	}

	public static TypeSQL getObjectType(Connection con) {
		return TypeSQL.getType(TypeSQL.DBXREF, con);
	}

}
