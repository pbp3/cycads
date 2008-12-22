/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesSourceContainer;

// S: same class that implements this interface
public interface DBRecord extends NotesSourceContainer<Note<DBRecord>>
{
	public String getAccession();

	public ExternalDatabase getDatabase();

	public String getDatabaseName();

	public Note<DBRecord> addNote(String value, String type);

}