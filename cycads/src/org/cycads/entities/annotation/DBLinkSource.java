/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;

public interface DBLinkSource
{
	public DBLink createDBLink(AnnotationMethod method, DBRecord record, NotesContainer<Note<DBLink>> notes);

	public DBLink createDBLink(AnnotationMethod method, String accession, String dbName,
			NotesContainer<Note<DBLink>> notes);

}
