/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkContainer;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesContainer;

public interface Sequence<D extends DBLink<S, R>, S extends Sequence< ? , ? , ? >, R extends DBRecord< ? , ? , ? >>
		extends NoteSource, NotesContainer<Note<S>>, DBLinkSource<D, S, R>, DBLinkContainer<D, S, R>
{
	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public String getDescription();

	public double getVersion();

	public String getName();

	public Organism getOrganism();

	public Location createLocation(int start, int end, Collection<Intron> introns);

}