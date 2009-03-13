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
import java.util.Iterator;
import java.util.TreeSet;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.SubseqAnnotationSQL;
import org.cycads.entities.annotation.SQL.SubseqDbxrefAnnotationSQL;
import org.cycads.entities.annotation.SQL.SubseqFunctionAnnotationSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.FunctionSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class SubsequenceSQL extends HasSynonymsNotebleSQL
		implements Subsequence<SequenceSQL, SubseqAnnotationSQL, FunctionSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int					id;
	private Connection			con;
	private int					start, end;
	private int					sequenceId;
	private SequenceSQL			sequence;
	private Collection<Intron>	introns;

	public SubsequenceSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT sequence_id, start_position, end_position from subsequence WHERE subsequence_id="
				+ id);
			if (rs.next()) {
				sequenceId = rs.getInt("sequence_id");
				start = rs.getInt("start_position");
				end = rs.getInt("end_position");
			}
			else {
				throw new SQLException("Subsequence does not exist:" + id);
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
		return "subsequence_synonym";
	}

	@Override
	public String getIdFieldName() {
		return "subsequence_id";
	}

	@Override
	public String getNoteTableName() {
		return "subsequence_note";
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
	public int getEnd() {
		return end;
	}

	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getMaxPosition() {
		return getEnd() > getStart() ? getEnd() : getStart();
	}

	@Override
	public int getMinPosition() {
		return getEnd() < getStart() ? getEnd() : getStart();
	}

	@Override
	public boolean isPositiveStrand() {
		return getStart() <= getEnd();
	}

	@Override
	public SequenceSQL getSequence() {
		if (sequence == null) {
			try {
				sequence = new SequenceSQL(sequenceId, getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return sequence;
	}

	@Override
	public boolean contains(Subsequence< ? , ? , ? , ? , ? , ? > subseq) {
		if (subseq.getMinPosition() < this.getMinPosition() || subseq.getMaxPosition() > this.getMaxPosition()) {
			return false;
		}
		// introns must be in natural order
		Iterator<Intron> itSubseq = (new TreeSet<Intron>(subseq.getIntrons())).iterator();
		Intron intronOtherSubseq = null;
		if (itSubseq.hasNext()) {
			intronOtherSubseq = itSubseq.next();
		}
		for (Intron intron : new TreeSet<Intron>(this.getIntrons())) {
			if (intronOtherSubseq == null) {
				return intron.getMinPosition() > subseq.getMaxPosition();
			}
			// get intronOtherSubseq that overlap intron
			while (intronOtherSubseq.getMaxPosition() < intron.getMinPosition()) {
				if (!itSubseq.hasNext()) {
					return intron.getMinPosition() > subseq.getMaxPosition();
				}
				intronOtherSubseq = itSubseq.next();
			}
			if (intronOtherSubseq.getMinPosition() > intron.getMinPosition()) {
				return intron.getMinPosition() > subseq.getMaxPosition();
			}
			else {
				// intronOtherSubseq.min<=intron.min
				while (intronOtherSubseq.getMaxPosition() < intron.getMaxPosition()) {
					// get nextIntron adjacent
					if (itSubseq.hasNext()) {
						Intron nextIntronOtherSubseq = itSubseq.next();
						if (intronOtherSubseq.getMaxPosition() + 1 == nextIntronOtherSubseq.getMinPosition()) {
							intronOtherSubseq = nextIntronOtherSubseq;
						}
						else {
							return intron.getMinPosition() > subseq.getMaxPosition();
						}
					}
					else {
						return intron.getMinPosition() > subseq.getMaxPosition();
					}
				}
			}
		}
		return true;
	}

	@Override
	public Collection<Intron> getIntrons() {
		if (introns == null) {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT start_position, end_position from Intron where subsequence_id="
					+ getId());
				introns = new ArrayList<Intron>();
				while (rs.next()) {
					introns.add(new SimpleIntron(rs.getInt("start_position"), rs.getInt("end_position")));
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
		return introns;
	}

	@Override
	public SubseqFunctionAnnotationSQL createFunctionAnnotation(AnnotationMethodSQL method, FunctionSQL function) {
		try {
			int id = SubseqFunctionAnnotationSQL.createSubseqFunctionAnnotationSQL(method, this, function,
				getConnection());
			return new SubseqFunctionAnnotationSQL(id, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public SubseqDbxrefAnnotationSQL createDbxrefAnnotation(AnnotationMethodSQL method, DbxrefSQL dbxref) {
		try {
			int id = SubseqDbxrefAnnotationSQL.createSubseqDbxrefAnnotationSQL(method, this, dbxref, getConnection());
			return new SubseqDbxrefAnnotationSQL(id, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public SubseqAnnotation< ? , ? , ? , ? > createAnnotation(TypeSQL type, AnnotationMethodSQL method) {
		try {
			int id = SubseqAnnotationSQL.createSubseqAnnotationSQL(method, this, getConnection());
			SubseqAnnotationSQL annot = new SubseqAnnotationSQL(id, getConnection());
			annot.addType(type);
			return annot;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<SubseqAnnotationSQL> getAnnotations(AnnotationFilter<SubseqAnnotationSQL> filter) {
		String extraWhere = " SSA.subsequence_id=" + getId();
		String extraFrom = "";
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
		String extraWhere = " SSA.subsequence_id=" + getId();
		String extraFrom = "";
		return SubseqDbxrefAnnotationSQL.getAnnotations(method, null, null, dbxref, extraFrom, extraWhere,
			getConnection());
	}

	@Override
	public Collection<SubseqAnnotationSQL> getAnnotations(AnnotationMethodSQL method, Collection<TypeSQL> types,
			DbxrefSQL synonym) {
		String extraWhere = " SSA.subsequence_id=" + getId();
		String extraFrom = "";
		return SubseqAnnotationSQL.getAnnotations(method, types, synonym, extraFrom, extraWhere, getConnection());
	}

}
