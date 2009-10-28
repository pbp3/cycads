/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.SQL.EntitySQL;
import org.cycads.entities.SQL.SimpleEntitySQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.general.ParametersDefault;

public class OrganismSQL extends SimpleEntitySQL implements Organism<SequenceSQL>
{
	private String	name;

	public OrganismSQL(int id, Connection con) throws SQLException {
		super(id, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT name from Organism WHERE ncbi_taxon_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				name = rs.getString("name");
			}
			else {
				throw new SQLException("Organism does not exist:" + id);
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

	public static OrganismSQL createOrganism(int id, String name, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Organism (ncbi_taxon_id, name, next_cyc_id) VALUES (?,?,?)");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setInt(3, ParametersDefault.getNextCycId());
			stmt.executeUpdate();
			return new OrganismSQL(id, con);
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
	public String getName() {
		return name;
	}

	@Override
	public int getNextCycId() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement("SELECT next_cyc_id from Organism WHERE ncbi_taxon_id=?");
			stmt.setInt(1, getId());
			rs = stmt.executeQuery();
			int nextCycId;
			if (rs.next()) {
				nextCycId = rs.getInt("next_cyc_id");
			}
			else {
				throw new SQLException("Organism does not exist:" + id);
			}
			stmt = getConnection().prepareStatement("UPDATE Organism SET Next_Cyc_Id=? WHERE ncbi_taxon_id=?");
			stmt.setInt(1, nextCycId + 1);
			stmt.setInt(2, getId());
			stmt.executeUpdate();
			return nextCycId;
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
	public void setName(String name) {
		if (name == null) {
			name = "";
		}
		if (!name.equals(getName())) {
			PreparedStatement stmt = null;
			try {
				stmt = getConnection().prepareStatement("UPDATE Organism SET name=? WHERE ncbi_taxon_id=?");
				stmt.setString(1, name);
				stmt.setInt(2, id);
				stmt.executeUpdate();
				this.name = name;
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
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

	@Override
	public SequenceSQL createNewSequence(String version) {
		int id = 0;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO sequence (ncbi_taxon_id, version) VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, getId());
			stmt.setString(2, version);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Sequence insert didn't return the id.");
			}
			return new SequenceSQL(id, getConnection());
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
	public Collection<SequenceSQL> getSequences() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement("SELECT sequence_id from sequence where ncbi_taxon_id=?");
			stmt.setInt(1, getId());
			rs = stmt.executeQuery();
			ArrayList<SequenceSQL> seqs = new ArrayList<SequenceSQL>();
			while (rs.next()) {
				seqs.add(new SequenceSQL(rs.getInt("sequence_id"), getConnection()));
			}
			return seqs;
		}
		catch (SQLException e) {
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
	public Collection<SequenceSQL> getSequences(String version) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(
				"SELECT sequence_id from sequence where ncbi_taxon_id=? AND version=?");
			stmt.setInt(1, getId());
			stmt.setString(2, version);
			rs = stmt.executeQuery();
			ArrayList<SequenceSQL> seqs = new ArrayList<SequenceSQL>();
			while (rs.next()) {
				seqs.add(new SequenceSQL(rs.getInt("sequence_id"), getConnection()));
			}
			return seqs;
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
	public Collection<SequenceSQL> getSequencesBySynonym(Dbxref synonym, String version) {
		Collection<SequenceSQL> ret = new ArrayList<SequenceSQL>();
		DbxrefSQL dbxrefSQL;
		try {
			dbxrefSQL = DbxrefSQL.getDbxref(synonym, getConnection());
			Collection<EntitySQL> seqs = getEntities(SequenceSQL.getEntityType(getConnection()), dbxrefSQL,
				getConnection());
			for (EntitySQL entity : seqs) {
				SequenceSQL seq = (SequenceSQL) entity;
				if (seq.getVersion().equals(version)) {
					ret.add(seq);
				}
			}
			return ret;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getEntityTypeName() {
		return Organism.ENTITY_TYPE_NAME;
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(Organism.ENTITY_TYPE_NAME, con);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Organism)) {
			return false;
		}
		Organism organism = (Organism) obj;
		return this.getId() == organism.getId();
	}

}
