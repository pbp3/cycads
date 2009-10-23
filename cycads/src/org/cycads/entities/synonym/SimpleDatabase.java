/*
 * Created on 23/10/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.SimpleEntityObject;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleDatabase extends SimpleEntityObject implements Database
{
	String	dbName;

	public SimpleDatabase(String dbName, EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > factory,
			EntityAnnotationFactory annotationFactory) {
		super(typeFactory, factory, annotationFactory);
		this.dbName = dbName;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return dbName;
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("SimpleDatabase");
	}

	@Override
	public String getEntityTypeName() {
		return "SimpleDatabase";
	}

}
