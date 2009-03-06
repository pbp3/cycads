/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.SubseqAnnotationSQL;
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

	public SequenceSQL(int id, Connection connection) {
		// TODO Auto-generated constructor stub
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
		if (seqStr==null)
		{
			seqStr=
		}
		return seqStr;
	}

	@Override
	public int getLength() {
		if (length==INVALID_LENGTH)
		{
			length=
		}
		return length;
	}

	@Override
	public SubsequenceSQL createSubsequence(int start, int end, Collection<Intron> introns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubsequenceSQL getSubsequence(int start, int end, Collection<Intron> introns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubsequenceSQL> getSubsequences(int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubsequenceSQL> getSubsequences(DbxrefSQL synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSequenceString(String seqStr) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(DbxrefSQL synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(TypeSQL type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(AnnotationMethodSQL method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(AnnotationMethodSQL method, TypeSQL type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(AnnotationFilter<SubseqAnnotationSQL> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
