/*
 * Created on 23/10/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.KO;
import org.cycads.entities.synonym.SimpleDbxref;
import org.cycads.entities.synonym.SimpleEC;
import org.cycads.entities.synonym.SimpleKO;

public class SimpleDbxrefFactory implements EntityDbxrefFactory
{

	EntityTypeFactory		typeFactory;
	EntityDbxrefFactory		dbxrefFactory;
	EntityAnnotationFactory	annotationFactory;

	public SimpleDbxrefFactory(EntityTypeFactory typeFactory, EntityDbxrefFactory dbxrefFactory,
			EntityAnnotationFactory annotationFactory) {
		this.typeFactory = typeFactory;
		this.dbxrefFactory = dbxrefFactory;
		this.annotationFactory = annotationFactory;
	}

	@Override
	public Dbxref getDbxref(String dbName, String accession) {
		return new SimpleDbxref(dbName, accession, typeFactory, dbxrefFactory, annotationFactory);
	}

	@Override
	public EC getEC(String ecNumber) {
		return new SimpleEC(ecNumber, typeFactory, dbxrefFactory, annotationFactory);
	}

	@Override
	public KO getKO(String ko) {
		return new SimpleKO(ko, typeFactory, dbxrefFactory, annotationFactory);
	}

}
