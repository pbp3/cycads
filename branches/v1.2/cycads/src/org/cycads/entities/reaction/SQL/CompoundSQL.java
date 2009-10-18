package org.cycads.entities.reaction.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.cycads.entities.annotation.SQL.AnnotationObjectSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.reaction.Compound;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class CompoundSQL extends HasSynonymsNotebleSQL implements Compound<DbxrefSQL, TypeSQL>, AnnotationObjectSQL
{
	private Connection	con;
	private int			id;
	private boolean		smallMolecule;

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
				smallMolecule = rs.getBoolean("small_molecule");
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

	protected CompoundSQL(boolean smallMolecule, Connection con) throws SQLException {
		this.smallMolecule = smallMolecule;
		this.con = con;
		this.id = createNewCompound(smallMolecule, con);
	}

	public CompoundSQL(boolean smallMolecule, String dbName, String accession, Connection con) throws SQLException {
		this.smallMolecule = smallMolecule;
		this.con = con;
		ArrayList<CompoundSQL> comps = getCompounds(dbName, accession, con);
		if (comps.size() > 0) {
			for (CompoundSQL comp : comps) {
				if (comp.isSmallMolecule() == smallMolecule) {
					this.id = comp.getId();
					return;
				}
			}
			throw new SQLException("Small molecule field diferent to create the object compound.");
		}
		this.id = createNewCompound(smallMolecule, con);
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

	private int createNewCompound(boolean smallMolecule, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("INSERT INTO compound (small_molecule) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setBoolean(1, smallMolecule);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
			else {
				throw new SQLException("Compound insert didn't return the id.");
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
		return smallMolecule;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CompoundSQL)) {
			return false;
		}
		CompoundSQL o = (CompoundSQL) obj;
		return (o.getId() == this.getId());
	}

	@Override
	public TypeSQL getAnnotationObjectType() {
		return getAnnotationObjectType(con);
	}

	public static TypeSQL getAnnotationObjectType(Connection con) {
		return TypeSQL.getType(TypeSQL.COMPOUND, con);
	}

}
