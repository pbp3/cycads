/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class SubsequenceSQL extends HasSynonymsNotebleSQL implements Subsequence<SequenceSQL>
{
	private final int			id;
	private final Connection	con;
	private int					start, end;
	private int					sequenceId;
	private SequenceSQL			sequence;
	private Collection<Intron>	introns;

	public SubsequenceSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT sequence_id, start_position, end_position from subsequence WHERE subsequence_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
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
	public boolean contains(Subsequence< ? > subseq) {
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
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.prepareStatement("SELECT start_position, end_position from Intron where subsequence_id=?");
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
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
	public TypeSQL getEntityType() {
		return getEntityType(con);
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(TypeSQL.SUBSEQUENCE, con);
	}

}
