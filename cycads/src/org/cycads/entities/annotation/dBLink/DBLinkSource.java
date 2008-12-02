package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;

public interface DBLinkSource<D extends DBLink<S, R>, S extends DBLinkSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? >>
{
	public D createDBLink(AnnotationMethod method, R record, NotesContainer<Note<D>> notes);

	public D createDBLink(AnnotationMethod method, String accession, String dbName, NotesContainer<Note<D>> notes);

}
