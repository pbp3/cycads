/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.parser.ParserException;

public class SimpleAnnotationRecordFactory<S, T> implements ObjectFactory<SimpleAnnotationRecord<S, T>>
{
	private ObjectFactory<S>				sourceFactory;
	private ObjectFactory<T>				targetFactory;
	private ObjectFactory<String>			scoreFactory;
	private ObjectFactory<AnnotationMethod>	methodFactory;
	private ObjectFactory<Collection<Note>>	notesFactory;

	public SimpleAnnotationRecordFactory(ObjectFactory<S> sourceFactory, ObjectFactory<T> targetFactory,
			ObjectFactory<String> scoreFactory, ObjectFactory<AnnotationMethod> methodFactory,
			ObjectFactory<Collection<Note>> notesFactory) {
		this.sourceFactory = sourceFactory;
		this.targetFactory = targetFactory;
		this.scoreFactory = scoreFactory;
		this.methodFactory = methodFactory;
		this.notesFactory = notesFactory;
	}

	@Override
	public SimpleAnnotationRecord<S, T> create(String[] values) throws ParserException {
		return new SimpleAnnotationRecord<S, T>(sourceFactory.create(values), targetFactory.create(values),
			scoreFactory.create(values), methodFactory.create(values), notesFactory.create(values));
	}

}
