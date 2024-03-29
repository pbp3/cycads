/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Note;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class EntityObjectsRecordFactory implements ObjectFactory<Collection<BasicEntity>>
{
	private EntityFinder						entityFinder;
	private ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	private ObjectFactory<Collection<Note>>		notesFactory;
	private ObjectFactory<Collection<Dbxref>>	synonymsFactory;
	private String								objectType;

	public EntityObjectsRecordFactory(EntityFinder entityFinder, ObjectFactory<Collection<Dbxref>> dbxrefsFactory,
			ObjectFactory<Collection<Note>> notesFactory, ObjectFactory<Collection<Dbxref>> synonymsFactory,
			String objectType) {
		this.entityFinder = entityFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.notesFactory = notesFactory;
		this.synonymsFactory = synonymsFactory;
		this.objectType = objectType;
	}

	@Override
	public Collection<BasicEntity> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return new ArrayList<BasicEntity>();
		}
		Collection<Note> notes = null;
		if (notesFactory != null) {
			notes = notesFactory.create(values);
		}
		Collection<Dbxref> synonyms = null;
		if (synonymsFactory != null) {
			synonyms = synonymsFactory.create(values);
		}

		Collection<BasicEntity> ret = new ArrayList<BasicEntity>();
		Collection<BasicEntity> retPartial;
		for (Dbxref dbxref : dbxrefs) {
			retPartial = entityFinder.getEntitiesBySynonym(dbxref, objectType);
			if (retPartial != null && !retPartial.isEmpty()) {
				ret.addAll(retPartial);
				for (BasicEntity entity : retPartial) {
					if (notes != null) {
						for (Note note : notes) {
							entity.addNote(note.getType(), note.getValue());
						}
					}
					if (synonyms != null) {
						for (Dbxref synonym : synonyms) {
							entity.addSynonym(synonym);
						}
					}
				}
			}
		}
		return ret;
	}
}
