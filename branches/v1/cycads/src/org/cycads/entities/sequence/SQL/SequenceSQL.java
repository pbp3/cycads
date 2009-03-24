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
import java.util.TreeSet;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.SubseqAnnotationSQL;
import org.cycads.entities.annotation.SQL.SubseqDbxrefAnnotationSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class SequenceSQL extends HasSynonymsNotebleSQL
		implements Sequence<OrganismSQL, SubsequenceSQL, SubseqAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	public static final int	INVALID_LENGTH	= -1;

	private int				id;
	private String			seqStr;
	private Connection		con;
	private int				length			= INVALID_LENGTH;
	private int				organismId;
	private OrganismSQL		organism;
	private String			version;

	public SequenceSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT NCBI_TAXON_ID, version from sequence WHERE sequence_id=" + id);
			if (rs.next()) {
				organismId = rs.getInt("NCBI_TAXON_ID");
				version = rs.getString("version");
			}
			else {
				throw new SQLException("Sequence does not exist:" + id);
			}
			rs = stmt.executeQuery("SELECT length from biosequence WHERE sequence_id=" + id);
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
	public String getSynonymTableName() {
		return "sequence_synonym";
	}

	@Override
	public String getIdFieldName() {
		return "sequence_id";
	}

	@Override
	public String getNoteTableName() {
		return "sequence_note";
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
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT seq from biosequence WHERE sequence_id=" + id);
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
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT length from biosequence WHERE sequence_id=" + id);
			if (!rs.next()) {
				stmt.executeUpdate("INSERT INTO biosequence (sequence_id, length, seq) VALUES (" + getId() + ","
					+ seqStr.length() + ",'" + seqStr + "')");
			}
			else {
				stmt.executeUpdate("UPDATE biosequence SET seq='" + seqStr + "' WHERE sequence_id=" + getId());
			}
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

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO subsequence (sequence_id, start_position, end_position) VALUES (" + getId()
				+ "," + start + "," + end + ")", Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Subsequence insert didn't return the id.");
			}
			if (introns != null) {
				int intronStart, intronEnd;
				for (Intron intron : introns) {
					if (intron.getStart() < start) {
						intronStart = start;
					}
					else {
						intronStart = intron.getStart();
					}
					if (intron.getEnd() > end) {
						intronEnd = end;
					}
					else {
						intronEnd = intron.getEnd();
					}
					if (intronStart <= intronEnd) {
						stmt.executeUpdate("INSERT INTO Intron (subsequence_id, start_position, end_position) VALUES ("
							+ id + "," + intronStart + "," + intronEnd + ")");
					}
				}
			}
			return new SubsequenceSQL(id, getConnection());
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
	public SubsequenceSQL getSubsequence(int start, int end, Collection<Intron> introns) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT subsequence_id from subsequence where sequence_id=" + getId()
				+ " AND start_position=" + start + " AND end_position=" + end);
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
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT subsequence_id from subsequence where sequence_id=" + getId()
				+ " AND start_position=" + start);
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
	public Collection<SubsequenceSQL> getSubsequences(DbxrefSQL synonym) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT SS.subsequence_id from ssubsequence SS, subsequence_synonym SSS where SS.sequence_id="
				+ getId() + " AND SS.subsequence_id=SSS.subsequence_id AND SSS.dbxref_id=" + synonym.getId());
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
		String extraFrom = ", subsequence SS";
		String extraWhere = " SS.sequence_id=" + getId() + " AND SS.subsequence_id=SSA.subsequence_id";
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
		String extraFrom = ", subsequence SS";
		String extraWhere = " SS.sequence_id=" + getId() + " AND SS.subsequence_id=SSA.subsequence_id";
		return SubseqDbxrefAnnotationSQL.getAnnotations(method, null, null, dbxref, extraFrom, extraWhere,
			getConnection());
	}

	@Override
	public Collection<SubseqAnnotationSQL> getAnnotations(AnnotationMethodSQL method, Collection<TypeSQL> types,
			DbxrefSQL synonym) {
		String extraFrom = ", subsequence SS";
		String extraWhere = " SS.sequence_id=" + getId() + " AND SS.subsequence_id=SSA.subsequence_id";
		return SubseqAnnotationSQL.getAnnotations(method, types, synonym, extraFrom, extraWhere, getConnection());
	}

}
