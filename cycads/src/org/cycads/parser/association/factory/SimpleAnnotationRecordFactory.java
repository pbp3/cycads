/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class SimpleAnnotationRecordFactory<S extends EntityObject, T extends EntityObject>
		implements ObjectFactory<Collection<Annotation<S, T>>>
{
	private EntityAnnotationFactory<EntityObject>	annotationFactory;
	private ObjectFactory<Collection<S>>			sourcesFactory;
	private ObjectFactory<Collection<T>>			targetsFactory;
	private ObjectFactory<String>					scoreFactory;
	private ObjectFactory<AnnotationMethod>			methodFactory;
	private ObjectFactory<Collection<Note>>			notesFactory;
	private ObjectFactory<Collection<Type>>			typesFactory;
	private ObjectFactory<Collection<Dbxref>>		synonymsFactory;
	private ObjectFactory<Collection<Annotation>>	parentsFactory;

	public SimpleAnnotationRecordFactory(EntityAnnotationFactory<EntityObject> annotationFactory,
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
	public Collection<Annotation<S, T>> create(String[] values) throws ParserException {
		Collection<S> sources = sourcesFactory.create(values);
		Collection<T> targets = targetsFactory.create(values);
		String score = scoreFactory.create(values);
		AnnotationMethod method = methodFactory.create(values);
		Collection<Note> notes = notesFactory.create(values);
		Collection<Type> types = typesFactory.create(values);
		Collection<Dbxref> synonyms = synonymsFactory.create(values);
		Collection<Annotation> parents = parentsFactory.create(values);

		Collection<Annotation<S, T>> ret = new ArrayList<Annotation<S, T>>(sources.size() * targets.size());
		for (S source : sources) {
			for (T target : targets) {
				Annotation<S, T> annotation = annotationFactory.createAnnotation(source, target, types, method, score);
				ret.add(annotation);
				if (notes != null) {
					for (Note note : notes) {
						annotation.addNote(note.getType(), note.getValue());
					}
				}
				if (synonyms != null) {
					for (Dbxref synonym : synonyms) {
						annotation.addSynonym(synonym);
					}
				}
				if (parents != null) {
					for (Annotation parent : parents) {
						annotation.addParent(parent);
					}
				}
			}
		}
		return ret;
	}
}
