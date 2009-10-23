/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory.independent;

import java.util.Collection;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.EntityObject;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class EntityObjectFactoryBySynonym<E extends EntityObject>
		implements IndependentObjectFactory<Collection< ? extends E>>
{
	IndependentDbxrefFactory<Dbxref>	dbxrefFactory;
	EntityFinder<E>						finder;
	String								objectType;

	public EntityObjectFactoryBySynonym(IndependentDbxrefFactory<Dbxref> dbxrefFactory, EntityFinder<E> finder,
			String objectType) {
		this.dbxrefFactory = dbxrefFactory;
		this.finder = finder;
		this.objectType = objectType;
	}

	@Override
	public Collection< ? extends E> create(String value) throws ParserException {
		return finder.getEntitiesBySynonym(dbxrefFactory.create(value), objectType);
	}

}
