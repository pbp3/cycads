/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import org.cycads.parser.ParserException;

public class SimpleAssociationRecordFactory<S, T> implements ObjectFactory<SimpleAssociation<S, T>>
{
	ObjectFactory<S>	fieldFactorySource;
	ObjectFactory<T>	fieldFactoryTarget;

	public SimpleAssociationRecordFactory(ObjectFactory<S> fieldFactorySource, ObjectFactory<T> fieldFactoryTarget) {
		this.fieldFactorySource = fieldFactorySource;
		this.fieldFactoryTarget = fieldFactoryTarget;
	}

	@Override
	public SimpleAssociation<S, T> create(String[] values) throws ParserException {
		return new SimpleAssociation<S, T>(fieldFactorySource.create(values), fieldFactoryTarget.create(values));
	}

}