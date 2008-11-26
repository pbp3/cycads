/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteCollection;

public interface DBLinkSource
{
	public DBLink createDBLink(AnnotationMethod method, DBRecord record, NoteCollection<Note<DBLink>> notes);

	public DBLink createDBLink(AnnotationMethod method, String accession, String dbName,
			NoteCollection<Note<DBLink>> notes);

}
