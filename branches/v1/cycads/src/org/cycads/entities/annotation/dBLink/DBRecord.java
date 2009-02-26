/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.NotesContainer;

// S: same class that implements this interface
public interface DBRecord<D extends DBLinkAnnot< ? , S, R, M>, S extends DBRecord< ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends DBLinkAnnotSource<D, R, M>, DBLinkAnnotContainer<D, S, R, M>, Noteble, NotesContainer<Note<S>>

{
	public String getAccession();

	public ExternalDatabase<S> getDatabase();

	public String getDatabaseName();

	public Note<S> addNote(String value, String type);

}