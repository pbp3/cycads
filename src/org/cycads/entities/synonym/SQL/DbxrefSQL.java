/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.SQL.BasicEntityAbstractSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;

public class DbxrefSQL extends BasicEntityAbstractSQL implements Dbxref
{
	public final static int	INVALID_ID	= -1;
	private DatabaseSQL		database;
	private String			accession;

	public DbxrefSQL(int id, Connection con) throws SQLException {
		super(id, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT external_db_id, accession FROM Dbxref WHERE dbxref_id=?");
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

	protected DbxrefSQL(String dbName, String accession, Connection con) throws SQLException {
		super(0, con);
		this.database = DatabaseSQL.getDB(dbName, con);
		this.accession = accession;
		this.id = getId(getDatabase(), accession, con);
		if (this.id == INVALID_ID) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.prepareStatement("INSERT INTO Dbxref (external_db_id, accession) VALUES (?,?)",
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
			stmt = con.prepareStatement("SELECT dbxref_id FROM Dbxref WHERE external_db_id=? AND accession=?");
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

	public static DbxrefSQL getDbxref(Dbxref dbxref, Connection con) throws SQLException {
		if (dbxref instanceof DbxrefSQL) {
			return (DbxrefSQL) dbxref;
		}
		else {
			return getDbxref(dbxref.getDbName(), dbxref.getAccession(), con);
		}
	}

	public static DbxrefSQL getDbxref(String dbName, String accession, Connection con) throws SQLException {
		String[] strs = accession.split(ParametersDefault.getDbxrefToStringSeparator());
		if (strs.length == 2) {
			dbName = strs[0];
			accession = strs[1];
		}
		if (dbName == null || (dbName = dbName.trim()).length() == 0) {
			throw new RuntimeException(Messages.dbxrefWithoutDBNameException());
		}
		if (accession == null || (accession = accession.trim()).length() == 0) {
			throw new RuntimeException(Messages.dbxrefWithoutAccessionException());
		}
		return new DbxrefSQL(dbName, accession, con);
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
	public DbxrefSQL addSynonym(String dbName, String accession) {
		try {
			return addSynonym(DbxrefSQL.getDbxref(dbName, accession, getConnection()));
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL addSynonym(Dbxref dbxref) {
		DbxrefSQL ret;
		if (dbxref instanceof DbxrefSQL) {
			ret = super.addSynonym(dbxref);
			if (ret != null) {
				ret.addSynonym(this);
			}
		}
		else {
			ret = addSynonym(dbxref.getDbName(), dbxref.getAccession());
		}
		return ret;
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
	public String getEntityTypeName() {
		return Dbxref.OBJECT_TYPE_NAME;
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(Dbxref.OBJECT_TYPE_NAME, con);
	}

}
