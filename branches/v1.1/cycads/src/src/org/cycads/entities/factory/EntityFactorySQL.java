/*
 * Created on 03/03/2009
 */
package org.cycads.entities.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;

import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.AnnotationSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.SQL.OrganismSQL;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.KO;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.ECSQL;
import org.cycads.entities.synonym.SQL.FunctionSQL;
import org.cycads.entities.synonym.SQL.KOSQL;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;

public class EntityFactorySQL
		implements EntityFactory<DbxrefSQL, AnnotationMethodSQL, TypeSQL, OrganismSQL, FunctionSQL, AnnotationSQL>
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

	private AnnotationMethodSQL						methodDefault;
	private Hashtable<String, AnnotationMethodSQL>	methods	= new Hashtable<String, AnnotationMethodSQL>();

	public AnnotationMethodSQL getMethodDefault() {
		return methodDefault;
	}

	public void setMethodDefault(AnnotationMethodSQL methodDefault) {
		this.methodDefault = methodDefault;
	}

	@Override
	public AnnotationMethodSQL getAnnotationMethod(String name) {
		if (name == null || (name = name.trim()).length() == 0) {
			return getMethodDefault();
		}
		try {
			AnnotationMethodSQL ret = methods.get(name);
			if (ret == null) {
				ret = new AnnotationMethodSQL(name, getConnection());
				methods.put(name, ret);
			}
			return ret;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL getDbxref(String dbName, String accession) {
		String[] strs = accession.split(ParametersDefault.getDbxrefToStringSeparator());
		if (strs.length == 2) {
			dbName = strs[0];
			accession = strs[1];
		}
		if (dbName == null || (dbName = dbName.trim()).length() == 0) {
			throw new RuntimeException(Messages.dbxrefWithoutDBNameException());
		}
		if (accession == null || (accession = accession.trim()).length() == 0) {
			throw new RuntimeException(Messages.dbxrefWithoutAccessionException());
		}
		try {
			return new DbxrefSQL(dbName, accession, getConnection());
		}
		catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + " with dbName=" + dbName + " and accession=" + accession, e);
		}
	}

	@Override
	public TypeSQL getNoteType(String name) {
		try {
			return TypeSQL.getType(name, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getAnnotationType(String name) {
		try {
			return TypeSQL.getType(name, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public OrganismSQL getOrganism(int orgId) {
		try {
			return new OrganismSQL(orgId, getConnection());
		}
		catch (SQLException e) {
			return null;
		}
	}

	@Override
	public OrganismSQL createOrganism(int orgId, String name) {
		try {
			return OrganismSQL.createOrganism(orgId, name, getConnection());
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

	@Override
	public FunctionSQL getFunction(String name, String description) {
		try {
			return new FunctionSQL(name, description, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getAnnotationTypeCDS() {
		return getAnnotationType(ParametersDefault.getCDSAnnotationTypeName());
	}

	@Override
	public TypeSQL getAnnotationTypeGene() {
		return getAnnotationType(ParametersDefault.getGeneAnnotationTypeName());
	}

	@Override
	public EC< ? , ? , ? , ? > getEC(String ecNumber) {
		try {
			return new ECSQL(ecNumber, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<AnnotationSQL> getAnnotations(Dbxref< ? , ? , ? , ? > dbxref) {
		try {
			return AnnotationSQL.getAnnotations(dbxref, getConnection());
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
