/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.AnnotationSQL;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.synonym.KO;

public class KOSQL extends DbxrefSQL implements KO
{

	public KOSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!getDbName().equals(DBNAME)) {
			throw new SQLException("The DbxrefId " + id + "(" + this.toString() + ") is not a KO Dbxref.");
		}
	}

	public KOSQL(String accession, Connection con) throws SQLException {
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
		definition.replaceAll("\'", "");
		setNoteValue("Definition", definition);
	}

	@Override
	public void setName(String name) {
		setNoteValue("Name", name);
	}

	public AnnotationSQL< ? extends KOSQL, ? extends ECSQL> addEcAnnotation(AnnotationMethodSQL method, String ecNumber) {
		try {
			ECSQL ec = new ECSQL(ecNumber, getConnection());
			Collection< ? extends AnnotationSQL< ? extends KOSQL, ? extends ECSQL>> annots = EntityFactorySQL.getAnnotations(
				this, ec, null, null, method, getConnection());
			if (annots.isEmpty()) {
				return AnnotationSQL.createAnnotationSQL(this, ec, null, method, null, getConnection());
			}
			else {
				return annots.iterator().next();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
