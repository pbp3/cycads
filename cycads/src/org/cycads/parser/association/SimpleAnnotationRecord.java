/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public class SimpleAnnotationRecord<S, T> extends SimpleAssociationRecord<S, T> implements AnnotationRecord<S, T>
{

	int	indexScore;

	public SimpleAnnotationRecord(String[] values, int indexSource, FieldFactory<S> fieldFactorySource,
			int indexTarget, FieldFactory<T> fieldFactoryTarget, int indexScore) throws FileParserError {
		super(values, indexSource, fieldFactorySource, indexTarget, fieldFactoryTarget);
		this.indexScore = indexScore;
	}

	@Override
	public String getScore() {
		return getNote(indexScore);
	}

}
