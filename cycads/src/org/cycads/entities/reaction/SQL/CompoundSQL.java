package org.cycads.entities.reaction.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.cycads.entities.SQL.EntitySQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.reaction.Compound;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;
import org.cycads.entities.synonym.SQL.SynonymsSQL;

public class CompoundSQL extends HasSynonymsNotebleSQL implements Compound, EntitySQL
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
		Collection<EntitySQL> comps = SynonymsSQL.getEntities(getEntityType(), dbName, accession, con);
		if (comps.size() > 0) {
			for (EntitySQL entity : comps) {
				CompoundSQL comp = (CompoundSQL) entity;
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
	public Connection getConnection() {
		return con;
	}

	@Override
	public int getId() {
		return id;
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
	public TypeSQL getEntityType() {
		return getEntityType(con);
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(Compound.ENTITY_TYPE_NAME, con);
	}

}
