/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SimpleAnnotation;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class SimpleAnnotationRecordFactory<S extends EntityObject, T extends EntityObject>
		implements ObjectFactory<Collection<Annotation<S, T>>>
{
	private EntityAnnotationFactory< ? >			annotationFactory;
	private ObjectFactory<Collection<S>>			sourcesFactory;
	private ObjectFactory<Collection<T>>			targetsFactory;
	private ObjectFactory<String>					scoreFactory;
	private ObjectFactory<AnnotationMethod>			methodFactory;
	private ObjectFactory<Collection<Note>>			notesFactory;
	private ObjectFactory<Collection<Type>>			typesFactory;
	private ObjectFactory<Collection<Dbxref>>		synonymsFactory;
	private ObjectFactory<Collection<Annotation>>	parentsFactory;

	public SimpleAnnotationRecordFactory(EntityAnnotationFactory< ? > annotationFactory,
			ObjectFactory<Collection<S>> sourcesFactory, ObjectFactory<Collection<T>> targetsFactory,
			ObjectFactory<String> scoreFactory, ObjectFactory<AnnotationMethod> methodFactory,
			ObjectFactory<Collection<Type>> typesFactory, ObjectFactory<Collection<Note>> notesFactory,
			ObjectFactory<Collection<Dbxref>> synonymsFactory, ObjectFactory<Collection<Annotation>> parentsFactory) {
		this.annotationFactory = annotationFactory;
		this.sourcesFactory = sourcesFactory;
		this.targetsFactory = targetsFactory;
		this.scoreFactory = scoreFactory;
		this.methodFactory = methodFactory;
		this.notesFactory = notesFactory;
		this.typesFactory = typesFactory;
		this.synonymsFactory = synonymsFactory;
		this.parentsFactory = parentsFactory;
	}

	@Override
	public SimpleAnnotation<S, T> create(String[] values) throws ParserException {
		Collection<S> sources = sourcesFactory.create(values);

		return new SimpleAnnotation<S, T>(sourcesFactory.create(values), targetsFactory.create(values),
			scoreFactory.create(values), methodFactory.create(values), notesFactory.create(values));
	}

}
