/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;

//S: same class that implements this interface
public interface DBRecord<D extends DBLink< ? , S, R, M>, S extends DBRecord< ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends DBLinkSource<D, R, M>, DBLinkContainer<D, S, R, M>, NoteSource, NotesContainer<Note<S>>

{
	public String getAccession();

	public ExternalDatabase<S> getDatabase();

	public String getDatabaseName();

	public Collection<DBLink< ? , ? , ? , ? >> getDBLinksFromSequence(
			Organism< ? extends Sequence< ? , ? , ? , ? , ? , ? >> organism);

}