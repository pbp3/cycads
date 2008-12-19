/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

// S: same class that implements this interface
public interface DBRecord<D extends DBLinkAnnotation< ? , S, R, M>, S extends DBRecord< ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends DBLinkAnnotationSource<D, R, M>, DBLinkAnnotationContainer<D, S, R, M>, NoteSource, NotesContainer<Note<S>>

{
	public String getAccession();

	public ExternalDatabase<S> getDatabase();

	public String getDatabaseName();

	public Note<S> addNote(String value, String type);

}