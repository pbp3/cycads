/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public class SimpleAssociationRecord<S, T> implements AssociationRecord<S, T>
{
	private String[]	values;
	private S			source;
	private T			target;

	public SimpleAssociationRecord(String[] values, int indexSource, FieldFactory<S> fieldFactorySource,
			int indexTarget, FieldFactory<T> fieldFactoryTarget) throws FileParserError {
		this.values = values;
		this.source = fieldFactorySource.create(getNote(indexSource));
		this.target = fieldFactoryTarget.create(getNote(indexTarget));
	}

	@Override
	public String getNote(int index) {
		if (index >= 0 && index < values.length) {
			return values[index];
		}
		else {
			return null;
		}
	}

	@Override
	public S getSource() {
		return source;
	}

	@Override
	public T getTarget() {
		return target;
	}

}
