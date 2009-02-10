/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesSourceContainer;

// S: same class that implements this interface
public interface DBRecord<D extends ExternalDatabase< ? >> extends NotesSourceContainer<Note<DBRecord< ? >>>
{
	public String getAccession();

	public D getDatabase();

	public String getDatabaseName();

}