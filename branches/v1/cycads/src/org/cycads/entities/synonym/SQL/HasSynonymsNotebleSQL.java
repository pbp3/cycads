/*
 * Created on 04/03/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.SQLException;
import java.util.Collection;

import org.cycads.entities.note.SQL.NotebleSQL;
import org.cycads.entities.synonym.HasSynonyms;

public abstract class HasSynonymsNotebleSQL extends NotebleSQL implements HasSynonyms<DbxrefSQL>
{
	protected SynonymsSQL	synonymsSQL	= null;

	public SynonymsSQL getSynonymsSQL() {
		if (synonymsSQL == null) {
			synonymsSQL = new SynonymsSQL(getId(), getSynonymTableName(), getIdFieldName(), getConnection());
		}
		return synonymsSQL;
	}

	public abstract String getSynonymTableName();

	@Override
	public Collection<DbxrefSQL> getSynonyms() {
		try {
			return getSynonymsSQL().getSynonyms();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<DbxrefSQL> getSynonyms(String dbName) {
		try {
			return getSynonymsSQL().getSynonyms(dbName);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL getSynonym(String dbName, String accession) {
		try {
			return getSynonymsSQL().getSynonym(dbName, accession);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL addSynonym(String dbName, String accession) {
		try {
			return getSynonymsSQL().addSynonym(dbName, accession);
			//			dbxref.getSynonymsSQL().addSynonym(this);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean isSynonym(DbxrefSQL dbxref) {
		try {
			return getSynonymsSQL().isSynonym(dbxref);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean isSynonym(String dbName, String accession) {
		try {
			return getSynonymsSQL().isSynonym(dbName, accession);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
