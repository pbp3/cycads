/*
 * Created on 05/01/2009
 */
package beta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.dbExternal.EC;
import org.cycads.exceptions.MethodNotImplemented;

public class ECSQL implements EC
{
	String	id;

	protected ECSQL(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(EC ec) {
		throw new MethodNotImplemented();
	}

	@Override
	public String getId() {
		return id;
	}

	public ECSQL save(Connection con) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO EC(Accession) VALUES('" + id + "')");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

}
