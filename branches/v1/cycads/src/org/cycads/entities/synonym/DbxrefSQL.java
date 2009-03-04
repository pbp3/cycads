/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.note.SQL.NotesSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class DbxrefSQL extends HasSynonymsNotebleSQL implements Dbxref<DbxrefSQL>
{
	public final static int	INVALID_ID	= -1;
	String					dbName;
	String					accession;
	int						id;
	SynonymsSQL				synonymsSQL	= null;
	NotesSQL				notes		= null;
	Connection				con;

	public DbxrefSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT dbname, accession from dbxref WHERE dbxref_id=" + id);
		if (rs.next()) {
			dbName = rs.getString("dbname");
			accession = rs.getString("accession");
		}
		else {
			throw new SQLException("Dbxref does not exist:" + id);
		}
		stmt.close();
	}

	public DbxrefSQL(String dbName, String accession, Connection con) throws SQLException {
		this.dbName = dbName;
		this.accession = accession;
		this.con = con;
		this.id = getId(dbName, accession, con);
		if (this.id == INVALID_ID) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO dbxref (dbname, accession) VALUES ('" + dbName + "','" + accession + "')");
			this.id = getId(dbName, accession, con);
			if (this.id == INVALID_ID) {
				throw new SQLException("Error creating dbxref:" + dbName + ", " + accession);
			}
			stmt.close();
		}
	}

	public static int getId(String dbName, String accession, Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT dbxref_id from dbxref WHERE dbname='" + dbName + "' AND accession='"
			+ accession + "'");
		int id = INVALID_ID;
		if (rs.next()) {
			id = rs.getInt("dbxref_id");
		}
		stmt.close();
		return id;
	}

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

}
