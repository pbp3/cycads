/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

//S: same class that implements this interface
public interface DBRecord<D extends DBLink<S, R>, S extends DBRecord< ? , ? , ? >, R extends DBRecord< ? , ? , ? >>
		extends DBLinkSource<D, S, R>, DBLinkContainer<D, S, R>, NoteSource, NotesContainer<Note<S>>

{
	public String getAccession();

	public ExternalDatabase<S> getDatabase();

	public String getDatabaseName();

}