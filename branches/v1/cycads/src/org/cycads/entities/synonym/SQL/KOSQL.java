/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.SQLException;

import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.DbxrefDbxrefAnnotationSQL;
import org.cycads.entities.synonym.KO;

public class KOSQL extends DbxrefSQL implements KO<DbxrefSQL, DbxrefDbxrefAnnotationSQL, AnnotationMethodSQL>
{

	public KOSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!getDbName().equals(DBNAME)) {
			throw new SQLException("The DbxrefId " + id + "(" + this.toString() + ") is not a KO Dbxref.");
		}
	}

	protected KOSQL(String accession, Connection con) throws SQLException {
		super(DBNAME, accession, con);
	}

	@Override
	public String getDefinition() {
		return getNoteValue("Definition");
	}

	@Override
	public String getName() {
		return getNoteValue("Name");
	}

	@Override
	public void setDefinition(String definition) {
		setNoteValue("Definition", definition);
	}

	@Override
	public void setName(String name) {
		setNoteValue("Name", name);
	}

	@Override
	public DbxrefDbxrefAnnotationSQL addEcAnnotation(AnnotationMethodSQL method, String ec) {
		try {
			this.get
			return new DbxrefDbxrefAnnotationSQL(DbxrefDbxrefAnnotationSQL.createDbxrefDbxrefAnnotationSQL(null,
				method, this, new ECSQL(ec, getConnection()), getConnection()), getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
