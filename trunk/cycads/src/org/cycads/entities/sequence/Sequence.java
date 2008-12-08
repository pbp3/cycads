/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkContainer;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

public interface Sequence<D extends DBLink<S, R, M>, S extends Sequence< ? , ? , ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod, L extends Location< ? , ? , ? , ? , ? >, F extends Feature< ? , ? , ? >>
		extends NoteSource, NotesContainer<Note<S>>, DBLinkSource<D, R, M>, DBLinkContainer<D, S, R, M>
{
	public String getDescription();

	public double getVersion();

	public String getName();

	public Organism<S> getOrganism();

	public L createLocation(int start, int end, Collection<Intron> introns);

	public Collection<F> getFeatures(FeatureFilter featureFilter);

}