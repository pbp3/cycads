/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.parser.FileParserError;

public class SimpleAnnotationRecord<S, T> extends SimpleAssociationRecord<S, T> implements AnnotationRecord<S, T>
{

	private int					indexScore;
	private int					indexMethod;
	private EntityMethodFactory	methodFactory;

	public SimpleAnnotationRecord(String[] values, int indexSource, FieldFactory<S> fieldFactorySource,
			int indexTarget, FieldFactory<T> fieldFactoryTarget, int indexScore, int indexMethod,
			EntityMethodFactory methodFactory) throws FileParserError {
		super(values, indexSource, fieldFactorySource, indexTarget, fieldFactoryTarget);
		this.indexScore = indexScore;
		this.indexMethod = indexMethod;
		this.methodFactory = methodFactory;
	}

	@Override
	public String getScore() {
		return getNote(indexScore);
	}

	@Override
	public AnnotationMethod getMethod() {
		return methodFactory.getAnnotationMethod(getNote(indexMethod));
	}

	//	@Override
	//	public Collection<Note> getNotes() {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}
	//
}
