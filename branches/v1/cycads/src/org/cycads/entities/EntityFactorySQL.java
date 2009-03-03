/*
 * Created on 03/03/2009
 */
package org.cycads.entities;

import java.sql.Connection;
import java.sql.SQLException;

import org.cycads.entities.annotation.AnnotationMethodSQL;
import org.cycads.entities.note.TypeSQL;
import org.cycads.entities.synonym.DbxrefSQL;

public class EntityFactorySQL implements EntityFactory<DbxrefSQL, AnnotationMethodSQL, TypeSQL>
{
	Connection	con;

	public EntityFactorySQL(Connection con) {
		this.con = con;
	}

	@Override
	public AnnotationMethodSQL getAnnotationMethod(String name) {
		try {
			return new AnnotationMethodSQL(name, con);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DbxrefSQL getDbxref(String dbName, String accession) {
		try {
			return new DbxrefSQL(dbName, accession, con);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL getNoteType(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeSQL getAnnotationType(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
