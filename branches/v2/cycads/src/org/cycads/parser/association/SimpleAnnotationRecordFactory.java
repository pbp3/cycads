/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public class SimpleAnnotationRecordFactory<S, T> implements RecordFactory<SimpleAnnotationRecord<S, T>>
{
	private int				indexSource;
	private FieldFactory<S>	fieldFactorySource;
	private int				indexTarget;
	private FieldFactory<T>	fieldFactoryTarget;
	private int				indexScore;

	public SimpleAnnotationRecordFactory(int indexSource, FieldFactory<S> fieldFactorySource, int indexTarget,
			FieldFactory<T> fieldFactoryTarget, int indexScore) {
		this.indexSource = indexSource;
		this.fieldFactorySource = fieldFactorySource;
		this.indexTarget = indexTarget;
		this.fieldFactoryTarget = fieldFactoryTarget;
		this.indexScore = indexScore;
	}

	@Override
	public SimpleAnnotationRecord<S, T> create(String[] values) throws FileParserError {
		return new SimpleAnnotationRecord<S, T>(values, indexSource, fieldFactorySource, indexTarget,
			fieldFactoryTarget, indexScore);
	}

}
