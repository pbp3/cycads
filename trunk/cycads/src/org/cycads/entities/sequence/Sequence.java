/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBLinkContainer;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;
import org.cycads.entities.note.NoteSource;

public interface Sequence extends NoteSource, NotesContainer<Note<Sequence>>, DBLinkSource, DBLinkContainer
{
	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public String getDescription();

	public double getVersion();

	public String getName();

	public Organism getOrganism();

	public Location createLocation(int start, int end, Collection<Intron> introns);

}