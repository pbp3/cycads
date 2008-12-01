package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;

public interface DBLinkSource
{
	public <R extends DBRecord, S extends DBLinkSource> DBLink<S, R> createDBLink(AnnotationMethod method, R record,
			NotesContainer<Note<DBLink<S, R>>> notes);

	public <R extends DBRecord, S extends DBLinkSource> DBLink<S, R> createDBLink(AnnotationMethod method,
			String accession, String dbName, NotesContainer<Note<DBLink<S, R>>> notes);

}
