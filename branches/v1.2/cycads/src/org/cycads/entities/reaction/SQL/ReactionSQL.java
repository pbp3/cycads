package org.cycads.entities.reaction.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.reaction.Reaction;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.ECSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class ReactionSQL extends HasSynonymsNotebleSQL implements Reaction<CompoundSQL, ECSQL, CompoundReactionSQL>
{
	private Connection	con;
	private int			id;
	private ECSQL		ec;
	private boolean		reversible;

	public ReactionSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT ec,reversible from reaction WHERE reaction_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				reversible = rs.getBoolean("reversible");
				ec = new ECSQL(rs.getInt("ec"), con);
			}
			else {
				throw new SQLException("Reaction does not exist:" + id);
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

	protected ReactionSQL(ECSQL ec, boolean reversible, Connection con) throws SQLException {
		this.reversible = reversible;
		this.ec = ec;
		this.con = con;
		this.id = createNewReaction(ec, reversible, con);
	}

	public ReactionSQL(ECSQL ec, boolean reversible, String dbName, String accession, Connection con)
			throws SQLException {
		this.reversible = reversible;
		this.ec = ec;
		this.con = con;
		ArrayList<ReactionSQL> reactions = getReactions(dbName, accession, con);
		if (reactions.size() > 0) {
			for (ReactionSQL reaction : reactions) {
				if (reaction.isReversible() == reversible && reaction.getEC().equals(ec)) {
					this.id = reaction.getId();
					return;
				}
			}
			throw new SQLException("Reaction already exists with different fields.");
		}
		this.id = createNewReaction(ec, reversible, con);
		addSynonym(dbName, accession);
	}

	public static ArrayList<ReactionSQL> getReactions(String dbName, String accession, Connection con)
			throws SQLException {
		return getReactions(new DbxrefSQL(dbName, accession, con), con);
	}

	public static ArrayList<ReactionSQL> getReactions(DbxrefSQL synonym, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ReactionSQL> result = new ArrayList<ReactionSQL>();
		try {
			stmt = con.prepareStatement("SELECT reaction_id from reaction_synonym WHERE dbxref_id=?");
			stmt.setInt(1, synonym.getId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new ReactionSQL(rs.getInt("reaction_id"), con));
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

	private int createNewReaction(ECSQL ec, boolean reversible, Connection con) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("INSERT INTO reaction (ec, reversible) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, ec.getId());
			stmt.setBoolean(2, reversible);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
			else {
				throw new SQLException("Reaction insert didn't return the id.");
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
	public CompoundReactionSQL addCompoundSideA(CompoundSQL compound, int quantity) {
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL compR : comps) {
			if (compR.getCompound().getId() == compound.getId()) {
				if (!compR.isSideA() || compR.getQuantity() != quantity) {
					try {
						throw new SQLException("Compound already exists with different quantity or side.");
					}
					catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
				else {
					return new CompoundReactionSQL(compound, this, true, quantity);
				}
			}
		}
		addCompound(compound, true, quantity);
		return new CompoundReactionSQL(compound, this, true, quantity);
	}

	@Override
	public CompoundReactionSQL addCompoundSideB(CompoundSQL compound, int quantity) {
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL compR : comps) {
			if (compR.getCompound().getId() == compound.getId()) {
				if (compR.isSideA() || compR.getQuantity() != quantity) {
					try {
						throw new SQLException("Compound already exists with different quantity or side.");
					}
					catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
				else {
					return new CompoundReactionSQL(compound, this, false, quantity);
				}
			}
		}
		addCompound(compound, false, quantity);
		return new CompoundReactionSQL(compound, this, false, quantity);
	}

	private void addCompound(CompoundSQL compound, boolean sideA, int quantity) {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO reaction_has_compound (reaction_id, compound_id, side_a, quantity) VALUES (?,?,?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, compound.getId());
			stmt.setBoolean(3, sideA);
			stmt.setInt(4, quantity);
			stmt.executeUpdate();
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

	@Override
	public Collection<CompoundReactionSQL> getCompounds() {
		ArrayList<CompoundReactionSQL> result = new ArrayList<CompoundReactionSQL>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT compound_id, side_a, quantity from reaction_has_compound WHERE reaction_id=?");
			stmt.setInt(1, getId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new CompoundReactionSQL(new CompoundSQL(rs.getInt("compound_id"), getConnection()), this,
					rs.getBoolean("side_a"), rs.getInt("quantity")));
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
		return result;
	}

	@Override
	public Collection<CompoundReactionSQL> getCompoundsSideA() {
		Collection<CompoundReactionSQL> result = new ArrayList<CompoundReactionSQL>();
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL comp : comps) {
			if (comp.isSideA()) {
				result.add(comp);
			}
		}
		return result;
	}

	@Override
	public Collection<CompoundReactionSQL> getCompoundsSideB() {
		Collection<CompoundReactionSQL> result = new ArrayList<CompoundReactionSQL>();
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL comp : comps) {
			if (!comp.isSideA()) {
				result.add(comp);
			}
		}
		return result;
	}

	@Override
	public ECSQL getEC() {
		return ec;
	}

	@Override
	public boolean isReversible() {
		return reversible;
	}

	@Override
	public Type getEntityType() {
		return TypeSQL.getType(TypeSQL.REACTION, con);
	}

}
