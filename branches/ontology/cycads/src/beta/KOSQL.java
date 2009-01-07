/*
 * Created on 05/01/2009
 */
package beta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.dbExternal.COG;
import org.cycads.dbExternal.EC;
import org.cycads.dbExternal.GO;
import org.cycads.dbExternal.KO;
import org.cycads.exceptions.MethodNotImplemented;

public class KOSQL implements KO
{
	String			id;
	String			definition	= "";
	ArrayList<EC>	ecs			= new ArrayList<EC>();

	public KOSQL(String id) {
		this.id = id;
	}

	public KOSQL(String id, String definition) {
		this.id = id;
		this.definition = definition;
	}

	@Override
	public Collection<COG> getCOGs() {
		throw new MethodNotImplemented();
	}

	@Override
	public String getDefinition() {
		return definition;
	}

	@Override
	public Collection<EC> getECs() {
		return ecs;
	}

	@Override
	public Collection<GO> getGOs() {
		throw new MethodNotImplemented();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void link2COG(COG cog) {
		throw new MethodNotImplemented();
	}

	@Override
	public void link2EC(EC ec) {
		ecs.add(ec);
	}

	@Override
	public void link2GO(GO go) {
		throw new MethodNotImplemented();
	}

	@Override
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public int compareTo(KO o) {
		throw new MethodNotImplemented();
	}

	public void save(Connection con) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			if (definition == null || definition.length() == 0) {
				stmt.executeUpdate("INSERT INTO KO(Accession) VALUES('" + id + "')");
			}
			else {
				definition = definition.replace("'", "\\'");
				stmt.executeUpdate("INSERT INTO KO(Accession, Function) VALUES('" + id + "','" + definition + "')");
			}
			for (EC ec : ecs) {
				stmt.executeUpdate("INSERT INTO KO_EC(KO_ACCESSION, EC_ACCESSION) VALUES('" + id + "','" + ec.getId()
					+ "')");
			}
		}
		catch (SQLException e) {
			System.out.println(id);
			e.printStackTrace();
			System.exit(0);
		}

	}
}
