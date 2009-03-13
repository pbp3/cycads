/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.SubseqAnnotationSQL;
import org.cycads.entities.annotation.SQL.SubseqDbxrefAnnotationSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class OrganismSQL
		implements Organism<SequenceSQL, SubsequenceSQL, SubseqAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			id;
	private String		name;
	private Connection	con;

	public OrganismSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT name from Organism WHERE NCBI_TAXON_ID=" + id);
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
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			if (name == null) {
				stmt.executeUpdate("INSERT INTO Organism (NCBI_TAXON_ID) VALUES (" + id + ")");
			}
			else {
				stmt.executeUpdate("INSERT INTO Organism (NCBI_TAXON_ID, name) VALUES (" + id + ",'" + name + "')");
			}
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
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public Connection getConnection() {
		return con;
	}

	@Override
	public void setName(String name) {
		if (name == null) {
			name = "";
		}
		if (!name.equals(getName())) {
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("UPDATE Organism SET name='" + name + "' WHERE NCBI_TAXON_ID=" + getId());
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

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO sequence (NCBI_TAXON_ID, version) VALUES (" + getId() + ",'" + version
				+ "')", Statement.RETURN_GENERATED_KEYS);
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
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT sequence_id from sequence where NCBI_TAXON_ID=" + getId());
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
	public Collection<SequenceSQL> getSequences(String version) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT sequence_id from sequence where NCBI_TAXON_ID=" + getId() + " AND version='"
				+ version + "'");
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
	public Collection<SequenceSQL> getSequences(DbxrefSQL synonym) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT S.sequence_id from sequence S, sequence_synonym SS where S.NCBI_TAXON_ID="
				+ getId() + " AND S.sequence_id=SS.sequence_id AND SS.dbxref_id=" + synonym.getId());
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
	public Collection<SubsequenceSQL> getSubsequences(DbxrefSQL synonym) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT SS.subsequence_id from subsequence SS, subsequence_synonym SSS, sequence S"
				+ " WHERE S.NCBI_TAXON_ID=" + getId()
				+ " AND S.sequence_id=SS.sequence_id AND SS.subsequence_id=SSS.subsequence_id AND SSS.dbxref_id="
				+ synonym.getId());
			ArrayList<SubsequenceSQL> sseqs = new ArrayList<SubsequenceSQL>();
			while (rs.next()) {
				sseqs.add(new SubsequenceSQL(rs.getInt("subsequence_id"), getConnection()));
			}
			return sseqs;
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
	public Collection<SubseqAnnotationSQL> getAnnotations(AnnotationFilter<SubseqAnnotationSQL> filter) {
		String extraFrom = ", subsequence SS, sequence S";
		String extraWhere = " S.NCBI_TAXON_ID=" + getId()
			+ " AND S.sequence_id=SS.sequence_id AND SS.subsequence_id=SSA.subsequence_id";
		Collection<SubseqAnnotationSQL> annots = SubseqAnnotationSQL.getAnnotations(null, null, null, extraFrom,
			extraWhere, getConnection());
		Collection<SubseqAnnotationSQL> ret = new ArrayList<SubseqAnnotationSQL>();
		for (SubseqAnnotationSQL annot : annots) {
			if (filter.accept(annot)) {
				ret.add(annot);
			}
		}
		return ret;
	}

	@Override
	public Collection<SubseqDbxrefAnnotationSQL> getDbxrefAnnotations(AnnotationMethodSQL method, DbxrefSQL dbxref) {
		String extraFrom = ", subsequence SS, sequence S";
		String extraWhere = " S.NCBI_TAXON_ID=" + getId()
			+ " AND S.sequence_id=SS.sequence_id AND SS.subsequence_id=SSA.subsequence_id";
		return SubseqDbxrefAnnotationSQL.getAnnotations(method, null, null, dbxref, extraFrom, extraWhere,
			getConnection());
	}

	@Override
	public Collection<SubseqAnnotationSQL> getAnnotations(AnnotationMethodSQL method, Collection<TypeSQL> types,
			DbxrefSQL synonym) {
		String extraFrom = ", subsequence SS, sequence S";
		String extraWhere = " S.NCBI_TAXON_ID=" + getId()
			+ " AND S.sequence_id=SS.sequence_id AND SS.subsequence_id=SSA.subsequence_id";
		return SubseqAnnotationSQL.getAnnotations(method, types, synonym, extraFrom, extraWhere, getConnection());
	}

}
