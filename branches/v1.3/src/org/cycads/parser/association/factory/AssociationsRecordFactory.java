/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class AssociationsRecordFactory<S extends BasicEntity, T extends BasicEntity>
		implements ObjectFactory<Collection<Association<S, T>>>
{
	private EntityAnnotationFactory<BasicEntity>	associationFactory;
	private ObjectFactory<Collection<S>>			sourcesFactory;
	private ObjectFactory<Collection<T>>			targetsFactory;
	private ObjectFactory<Collection<Note>>			notesFactory;
	private ObjectFactory<Collection<Type>>			typesFactory;
	private ObjectFactory<Collection<Dbxref>>		synonymsFactory;

	public AssociationsRecordFactory(EntityAnnotationFactory<BasicEntity> associationFactory,
			ObjectFactory<Collection<S>> sourcesFactory, ObjectFactory<Collection<T>> targetsFactory,
			ObjectFactory<Collection<Type>> typesFactory, ObjectFactory<Collection<Note>> notesFactory,
			ObjectFactory<Collection<Dbxref>> synonymsFactory) {
		this.associationFactory = associationFactory;
		this.sourcesFactory = sourcesFactory;
		this.targetsFactory = targetsFactory;
		this.notesFactory = notesFactory;
		this.typesFactory = typesFactory;
		this.synonymsFactory = synonymsFactory;
	}

	@Override
	public Collection<Association<S, T>> create(String[] values) throws ParserException {
		Collection<S> sources = sourcesFactory.create(values);
		Collection<T> targets = targetsFactory.create(values);
		Collection<Note> notes = null;
		if (notesFactory != null) {
			notes = notesFactory.create(values);
		}
		Collection<Type> types = null;
		if (typesFactory != null) {
			types = typesFactory.create(values);
		}
		Collection<Dbxref> synonyms = null;
		if (synonymsFactory != null) {
			synonyms = synonymsFactory.create(values);
		}

		if (sources == null || targets == null) {
			return null;
		}

		Collection<Association<S, T>> ret = new ArrayList<Association<S, T>>(sources.size() * targets.size());
		for (S source : sources) {
			for (T target : targets) {
				Association<S, T> association = associationFactory.createAssociation(source, target, types);
				ret.add(association);
				if (notes != null) {
					for (Note note : notes) {
						association.addNote(note.getType(), note.getValue());
					}
				}
				if (synonyms != null) {
					for (Dbxref synonym : synonyms) {
						association.addSynonym(synonym);
					}
				}
			}
		}
		return ret;
	}
}
