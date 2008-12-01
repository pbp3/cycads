package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;

public interface DBLinkSource<S extends DBLinkSource<S>>
{
	public <R extends DBRecord> DBLink<S, R> createDBLink(AnnotationMethod method, R record,
			NotesContainer<Note<DBLink<S, R>>> notes);

	public <R extends DBRecord> DBLink<S, R> createDBLink(AnnotationMethod method, String accession, String dbName,
			NotesContainer<Note<DBLink<S, R>>> notes);

}
