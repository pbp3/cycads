/*
 * Created on 03/03/2009
 */
package org.cycads.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.OrganismSQL;
import org.cycads.entities.synonym.KO;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.KOSQL;
import org.cycads.general.Config;

public class EntityFactorySQL implements EntityFactory<DbxrefSQL, AnnotationMethodSQL, TypeSQL, OrganismSQL>
{
	private Connection	con;

	public EntityFactorySQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Config.getSQLConnectionUrl(), Config.getSQLUser(),
				Config.getSQLPassword());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void finish() {
		try {
			getConnection().close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public AnnotationMethodSQL getAnnotationMethod(String name) {
		try {
			return new AnnotationMethodSQL(name, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL getDbxref(String dbName, String accession) {
		try {
			return new DbxrefSQL(dbName, accession, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getNoteType(String name) {
		throw new RuntimeException("Method not implemented.");
	}

	@Override
	public TypeSQL getAnnotationType(String name) {
		throw new RuntimeException("Method not implemented.");
	}

	@Override
	public OrganismSQL getOrganism(int orgId) {
		try {
			return new OrganismSQL(orgId, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() {
		return con;
	}

	@Override
	public KO< ? , ? , ? , ? > getKO(String ko) {
		try {
			return new KOSQL(ko, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
