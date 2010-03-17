/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.note.Note;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class DbxrefsRecordFactory implements ObjectFactory<Collection<Dbxref>>
{
	private ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	private ObjectFactory<Collection<Note>>		notesFactory;
	private ObjectFactory<Collection<Dbxref>>	synonymsFactory;

	public DbxrefsRecordFactory(ObjectFactory<Collection<Dbxref>> dbxrefsFactory,
			ObjectFactory<Collection<Note>> notesFactory, ObjectFactory<Collection<Dbxref>> synonymsFactory) {
		this.dbxrefsFactory = dbxrefsFactory;
		this.notesFactory = notesFactory;
		this.synonymsFactory = synonymsFactory;
	}

	@Override
	public Collection<Dbxref> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return new ArrayList<Dbxref>();
		}
		Collection<Note> notes = notesFactory.create(values);
		Collection<Dbxref> synonyms = synonymsFactory.create(values);

		for (Dbxref dbxref : dbxrefs) {
			if (notes != null) {
				for (Note note : notes) {
					dbxref.addNote(note.getType(), note.getValue());
				}
			}
			if (synonyms != null) {
				for (Dbxref synonym : synonyms) {
					dbxref.addSynonym(synonym);
				}
			}
		}
		return dbxrefs;
	}
}
