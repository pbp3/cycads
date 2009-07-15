package org.cycads.entities.reaction.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.cycads.entities.reaction.Compound;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class CompoundSQL extends HasSynonymsNotebleSQL implements Compound<DbxrefSQL>
{
	Connection	con;
	int			id;
	boolean		isSmallMolecule;

	public CompoundSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT small_molecule from compound WHERE compound_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				isSmallMolecule = rs.getBoolean("small_molecule");
			}
			else {
				throw new SQLException("Compound does not exist:" + id);
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

	protected CompoundSQL(boolean isSmallMolecule, Connection con) throws SQLException {
		this.isSmallMolecule = isSmallMolecule;
		this.con = con;
		this.id = createNewCompound(isSmallMolecule, con);
	}

	public CompoundSQL(boolean isSmallMolecule, String dbName, String accession, Connection con) throws SQLException {
		this.isSmallMolecule = isSmallMolecule;
		this.con = con;
		ArrayList<CompoundSQL> comps = getCompounds(dbName, accession, con);
		if (comps.size() > 0) {
			for (CompoundSQL comp : comps) {
				if (comp.isSmallMolecule == isSmallMolecule) {
					this.id = comp.getId();
					return;
				}
			}
		}
		this.id = createNewCompound(isSmallMolecule, con);
		addSynonym(dbName, accession);
	}

	public static ArrayList<CompoundSQL> getCompounds(String dbName, String accession, Connection con)
			throws SQLException {
		return getCompounds(new DbxrefSQL(dbName, accession, con), con);
	}

	public static ArrayList<CompoundSQL> getCompounds(DbxrefSQL synonym, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<CompoundSQL> result = new ArrayList<CompoundSQL>();
		try {
			stmt = con.prepareStatement("SELECT compound_id from compound_synonym WHERE dbxref_id=?");
			stmt.setInt(1, synonym.getId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new CompoundSQL(rs.getInt("compound_id"), con));
			}
			return result;
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

	protected int createNewCompound(boolean isSmallMolecule, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("INSERT INTO compound (small_molecule) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setBoolean(1, isSmallMolecule);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
			else {
				throw new SQLException("Compound insert didn't return the id.");
			}
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
	public String getSynonymTableName() {
		return "compound_synonym";
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getIdFieldName() {
		return "compound_id";
	}

	@Override
	public String getNoteTableName() {
		return "compound_note";
	}

	@Override
	public boolean isSmallMolecule() {
		return isSmallMolecule;
	}

}
