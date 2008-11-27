/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.DBLinkCollection;
import org.cycads.entities.annotation.DBLinkSource;
import org.cycads.entities.annotation.Feature;
import org.cycads.entities.annotation.FeatureFilter;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteCollection;
import org.cycads.entities.note.NoteSource;

public interface Sequence extends NoteSource, NoteCollection<Note<Sequence>>, DBLinkSource, DBLinkCollection
{

	public Collection<Feature> getFeatures();

	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public String getDescription();

	public double getVersion();

	public String getName();

	public Organism getOrganism();

	public Location createLocation(int start, int end, Collection<Intron> introns);

}