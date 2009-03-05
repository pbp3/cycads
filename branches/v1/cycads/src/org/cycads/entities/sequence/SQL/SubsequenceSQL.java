/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.sql.Connection;
import java.sql.SQLException;

import org.cycads.entities.sequence.Subsequence;

public class SubsequenceSQL implements Subsequence
{
	int			id;

	Connection	con;

	public SubsequenceSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
	}

	public int getId() {
		return id;
	}

}
