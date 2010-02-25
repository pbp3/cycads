/*
 * Created on 25/09/2009
 * Get annotations using synonyms. Create only the objects already persisted
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.EntityFinder;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class ExistedEntitiesFactoryBySynonym implements ObjectFactory<Collection<BasicEntity>>
{
	private EntityFinder						entityFinder;
	private ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;

	public ExistedEntitiesFactoryBySynonym(EntityFinder entityFinder, ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.entityFinder = entityFinder;
		this.dbxrefsFactory = dbxrefsFactory;
	}

	@Override
	public Collection<BasicEntity> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return new ArrayList<BasicEntity>();
		}
		Collection<BasicEntity> ret = new ArrayList<BasicEntity>();
		Collection<BasicEntity> entities;
		for (Dbxref dbxref : dbxrefs) {
			entities = entityFinder.getEntitiesBySynonym(dbxref, null);
			if (entities != null && !entities.isEmpty()) {
				ret.addAll(entities);
			}
		}
		return ret;
	}

}
