/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.DBRecordsContainer;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesSourceContainer;

public interface Sequence<S extends Sequence< ? , ? , ? , ? >, R extends DBRecord, L extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? , ? >, F extends Feature< ? , ? , ? , ? >>
		extends NotesSourceContainer<Note<S>>, DBRecordsContainer<R>
{
	public String getDescription();

	public double getVersion();

	public String getName();

	public int getId();

	public Organism<S> getOrganism();

	public L getOrCreateSubsequence(int start, int end, Collection<Intron> introns);

	public Collection<F> getFeatures(FeatureFilter<F> featureFilter);

	public Note<S> addNote(String value, String type);

}