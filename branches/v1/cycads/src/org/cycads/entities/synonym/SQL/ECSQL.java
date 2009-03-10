/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym.SQL;

import java.sql.Connection;
import java.sql.SQLException;

import org.cycads.entities.synonym.EC;

public class ECSQL extends DbxrefSQL implements EC<DbxrefSQL>
{

	public ECSQL(int id, Connection con) throws SQLException {
		super(id, con);
		if (!getDbName().equals(DBNAME)) {
			throw new SQLException("The DbxrefId " + id + "(" + this.toString() + ") is not a EC Dbxref.");
		}
	}

	protected ECSQL(String accession, Connection con) throws SQLException {
		super(DBNAME, accession, con);
	}

}
