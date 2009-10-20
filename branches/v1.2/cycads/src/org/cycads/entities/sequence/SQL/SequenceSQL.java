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
import java.util.TreeSet;

import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class SequenceSQL extends HasSynonymsNotebleSQL implements Sequence<OrganismSQL, SubsequenceSQL>
{
	public static final int		INVALID_LENGTH	= -1;

	private final int			id;
	private String				seqStr;
	private final Connection	con;
	private int					length			= INVALID_LENGTH;
	private int					organismId;
	private OrganismSQL			organism;
	private String				version;

	public SequenceSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT ncbi_taxon_id, version from sequence WHERE sequence_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				organismId = rs.getInt("ncbi_taxon_id");
				version = rs.getString("version");
			}
			else {
				throw new SQLException("Sequence does not exist:" + id);
			}
			stmt = con.prepareStatement("SELECT length from biosequence WHERE sequence_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				length = rs.getInt("length");
			}
			else {
				length = 0;
				seqStr = "";
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
	public OrganismSQL getOrganism() {
		if (organism == null) {
			try {
				organism = new OrganismSQL(organismId, getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return organism;
	}

	@Override
	public String getSequenceString() {
		if (seqStr == null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.prepareStatement("SELECT seq from biosequence WHERE sequence_id=?");
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
				if (rs.next()) {
					seqStr = rs.getString("seq");
				}
				else {
					seqStr = "";
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
		return seqStr;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setSequenceString(String seqStr) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT length from biosequence WHERE sequence_id=?");
			stmt.setInt(1, getId());
			rs = stmt.executeQuery();
			if (!rs.next()) {
				stmt = con.prepareStatement("INSERT INTO biosequence (sequence_id, length, seq) VALUES (?,?,?)");
				stmt.setInt(1, getId());
				stmt.setInt(2, seqStr.length());
				stmt.setString(3, seqStr);
			}
			else {
				stmt = con.prepareStatement("UPDATE biosequence SET seq=? WHERE sequence_id=?");
				stmt.setString(1, seqStr);
				stmt.setInt(2, getId());
			}
			stmt.executeUpdate();
			this.seqStr = seqStr;
			this.length = seqStr.length();
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
	public SubsequenceSQL createSubsequence(int start, int end, Collection<Intron> introns) {
		int id = 0;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "";
		try {
			stmt = con.prepareStatement(
				"INSERT INTO subsequence (sequence_id, start_position, end_position) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, getId());
			stmt.setInt(2, start);
			stmt.setInt(3, end);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Subsequence insert didn't return the id.");
			}
			if (introns != null) {
				int intronStart, intronEnd;
				int minStart = start;
				int maxEnd = end;
				if (minStart > maxEnd) {
					int aux = minStart;
					minStart = maxEnd;
					maxEnd = aux;
				}
				stmt = con.prepareStatement("INSERT INTO Intron (subsequence_id, start_position, end_position) VALUES (?,?,?)");
				for (Intron intron : introns) {
					if (intron.getStart() < minStart) {
						intronStart = minStart;
					}
					else {
						intronStart = intron.getStart();
					}
					if (intron.getEnd() > maxEnd) {
						intronEnd = maxEnd;
					}
					else {
						intronEnd = intron.getEnd();
					}
					if (intronStart <= intronEnd) {
						stmt.setInt(1, getId());
						stmt.setInt(2, intronStart);
						stmt.setInt(3, intronEnd);
						stmt.executeUpdate();
					}
				}
			}
			return new SubsequenceSQL(id, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(query, e);
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
	public SubsequenceSQL getSubsequence(int start, int end, Collection<Intron> introns) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT subsequence_id from subsequence where sequence_id=? AND start_position=? AND end_position=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, start);
			stmt.setInt(3, end);
			rs = stmt.executeQuery();
			ArrayList<SubsequenceSQL> sseqs = new ArrayList<SubsequenceSQL>();
			while (rs.next()) {
				sseqs.add(new SubsequenceSQL(rs.getInt("subsequence_id"), getConnection()));
			}
			Object[] introns1;
			if (introns == null) {
				introns1 = (new TreeSet<Intron>()).toArray();
			}
			else {
				introns1 = (new TreeSet<Intron>(introns)).toArray();
			}
			for (SubsequenceSQL subseq : sseqs) {
				Object[] introns2 = (new TreeSet<Intron>(subseq.getIntrons())).toArray();
				if (introns1.length == introns2.length) {
					int i = 0;
					while (i < introns1.length && introns1[i].equals(introns2[i])) {
						i++;
					}
					if (i == introns1.length) {
						return subseq;
					}
				}
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
		return null;
	}

	@Override
	public Collection<SubsequenceSQL> getSubsequences(int start) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT subsequence_id from subsequence where sequence_id=? AND start_position=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, start);
			rs = stmt.executeQuery();
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
	public TypeSQL getEntityType() {
		return getEntityType(con);
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(TypeSQL.SEQUENCE, con);
	}

}
