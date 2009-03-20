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
		Iterator<Intron> itSubseq = subseq.getIntrons().iterator();
		Intron intronOtherSubseq = null;
		if (itSubseq.hasNext()) {
			intronOtherSubseq = itSubseq.next();
		}
		for (Intron intron : this.getIntrons()) {
			if (intronOtherSubseq == null) {
				return intron.getStart() > subseq.getMaxPosition();
			}
			// get intronOtherSubseq that overlap intron
			while (intronOtherSubseq.getEnd() < intron.getStart()) {
				if (!itSubseq.hasNext()) {
					return intron.getStart() > subseq.getMaxPosition();
				}
				intronOtherSubseq = itSubseq.next();
			}
			if (intronOtherSubseq.getStart() > intron.getStart()) {
				return intron.getStart() > subseq.getMaxPosition();
			}
			else {
				// intronOtherSubseq.min<=intron.min
				while (intronOtherSubseq.getEnd() < intron.getEnd()) {
					// get nextIntron adjacent
					if (itSubseq.hasNext()) {
						Intron nextIntronOtherSubseq = itSubseq.next();
						if (intronOtherSubseq.getEnd() + 1 == nextIntronOtherSubseq.getStart()) {
							intronOtherSubseq = nextIntronOtherSubseq;
						}
						else {
							return intron.getStart() > subseq.getMaxPosition();
						}
					}
					else {
						return intron.getStart() > subseq.getMaxPosition();
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
				introns = new TreeSet<Intron>();
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
	public boolean addIntron(Intron intron) {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Intron (subsequence_id, start_position, end_position) VALUES (?,?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, intron.getStart());
			stmt.setInt(3, intron.getEnd());
			stmt.executeUpdate();
			if (introns != null) {
				introns.add(intron);
			}
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
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
	public boolean removeIntron(Intron intron) {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("DELETE FROM Intron WHERE subsequence_id=? AND start_position=? AND end_position=?");
			stmt.setInt(1, getId());
			stmt.setInt(2, intron.getStart());
			stmt.setInt(3, intron.getEnd());
			stmt.executeUpdate();
			if (introns != null) {
				introns.remove(intron);
			}
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
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
	public boolean addExon(int start, int end) {
		if (start > end) {
			int aux = start;
			start = end;
			end = aux;
		}
		Collection<Intron> introns = getIntrons();
		Collection<Intron> intronsToRemove = new ArrayList<Intron>();
		Collection<Intron> intronsToAdd = new ArrayList<Intron>();
		for (Intron intron : introns) {
			if ((intron.getStart() < start && intron.getEnd() > end)
				|| (intron.getStart() >= start && intron.getStart() <= end)
				|| (intron.getEnd() >= start && intron.getEnd() <= end)) {
				intronsToRemove.add(intron);
				if (intron.getStart() < start) {
					intronsToAdd.add(new SimpleIntron(intron.getStart(), start - 1));
				}
				if (intron.getEnd() > end) {
					intronsToAdd.add(new SimpleIntron(end + 1, intron.getEnd()));
				}
			}
		}
		for (Intron intron : intronsToRemove) {
			removeIntron(intron);
		}
		for (Intron intron : intronsToAdd) {
			addIntron(intron);
		}
		return true;
	}

	@Override
	public SubseqDbxrefAnnotationSQL addDbxrefAnnotation(AnnotationMethodSQL method, DbxrefSQL dbxref) {
		Collection< ? extends SubseqDbxrefAnnotationSQL> annots = getDbxrefAnnotations(method, dbxref);
		if (annots.isEmpty()) {
			return createDbxrefAnnotation(method, dbxref);
		}
		else {
			return annots.iterator().next();
		}
	}

	@Override
	public SubseqFunctionAnnotationSQL addFunctionAnnotation(AnnotationMethodSQL method, FunctionSQL function) {
		Collection<SubseqFunctionAnnotationSQL> annots = getFunctionAnnotations(method, function);
		if (annots.isEmpty()) {
			return createFunctionAnnotation(method, function);
		}
		else {
			return annots.iterator().next();
		}
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
	public SubseqAnnotation< ? , ? , ? , ? , ? > createAnnotation(TypeSQL type, AnnotationMethodSQL method) {
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

	@Override
	public Collection<SubseqFunctionAnnotationSQL> getFunctionAnnotations(AnnotationMethodSQL method,
			FunctionSQL function) {
		String extraWhere = " SSA.subsequence_id=" + getId();
		String extraFrom = "";
		return SubseqFunctionAnnotationSQL.getAnnotations(method, null, null, function, extraFrom, extraWhere,
			getConnection());
	}

}
