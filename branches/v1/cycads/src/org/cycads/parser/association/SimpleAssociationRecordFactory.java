/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public class SimpleAssociationRecordFactory<S, T> implements RecordFactory<SimpleAssociationRecord<S, T>>
{
	int				indexSource;
	FieldFactory<S>	fieldFactorySource;
	int				indexTarget;
	FieldFactory<T>	fieldFactoryTarget;

	public SimpleAssociationRecordFactory(int indexSource, FieldFactory<S> fieldFactorySource, int indexTarget,
			FieldFactory<T> fieldFactoryTarget) {
		this.indexSource = indexSource;
		this.fieldFactorySource = fieldFactorySource;
		this.indexTarget = indexTarget;
		this.fieldFactoryTarget = fieldFactoryTarget;
	}

	@Override
	public SimpleAssociationRecord<S, T> create(String[] values) throws FileParserError {
		return new SimpleAssociationRecord<S, T>(values, indexSource, fieldFactorySource, indexTarget,
			fieldFactoryTarget);
	}

}
