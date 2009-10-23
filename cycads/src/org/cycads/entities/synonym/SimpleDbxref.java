/*
 * Created on 23/10/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.SimpleEntityObject;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleDbxref extends SimpleEntityObject implements Dbxref
{

	String		accession;
	Database	database;

	public SimpleDbxref(String accession, String dbName, EntityTypeFactory< ? > typeFactory,
			EntityDbxrefFactory< ? > factory, EntityAnnotationFactory annotationFactory) {
		super(typeFactory, factory, annotationFactory);
		this.accession = accession;
		this.database = new SimpleDatabase(dbName, typeFactory, factory, annotationFactory);
	}

	@Override
	public String getAccession() {
		return accession;
	}

	@Override
	public Database getDatabase() {
		return database;
	}

	@Override
	public String getDbName() {
		return database.getName();
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("Simple Dbxref");
	}

}
