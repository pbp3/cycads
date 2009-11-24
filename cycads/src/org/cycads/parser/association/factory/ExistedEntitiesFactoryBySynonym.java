/*
 * Created on 25/09/2009
 * Get annotations using synonyms. Create only the objects already persisted
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.EntityFinder;
import org.cycads.entities.factory.EntityFeatureFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;

public class ExistedEntitiesFactoryBySynonym implements ObjectFactory<Collection<BasicEntity>>
{
	private EntityFinder						entityFinder;
	private ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	boolean										createFake;
	//the attributes below will be used in the fake creation
	private Organism							organism;
	private EntityMethodFactory					methodFactory;
	private EntityFeatureFactory				featureFactory;
	private EntityTypeFactory					typeFactory;

	public ExistedEntitiesFactoryBySynonym(EntityFinder entityFinder, ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.entityFinder = entityFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.createFake = false;
	}

	public ExistedEntitiesFactoryBySynonym(EntityFinder entityFinder, ObjectFactory<Collection<Dbxref>> dbxrefsFactory,
			Organism organism, EntityMethodFactory methodFactory, EntityFeatureFactory featureFactory,
			EntityTypeFactory typeFactory) {
		this.entityFinder = entityFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.createFake = true;
		this.organism = organism;
		this.methodFactory = methodFactory;
		this.featureFactory = featureFactory;
		this.typeFactory = typeFactory;
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
			if ((entities == null || entities.isEmpty()) && createFake) {
				if (entities == null) {
					entities = new ArrayList<BasicEntity>();
				}
				entities.add(Tools.createFakeSubseqAnnot(dbxref, organism, methodFactory, featureFactory, typeFactory));
			}
			if (entities != null && !entities.isEmpty()) {
				ret.addAll(entities);
			}
		}
		return ret;
	}

}
