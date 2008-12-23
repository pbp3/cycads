/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.DBRecord;
import org.cycads.entities.annotation.DBRecordsContainer;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesSourceContainer;

public interface Sequence<S extends Sequence< ? , ? , ? , ? >, R extends DBRecord< ? >, L extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, F extends AnnotFeature< ? , ? , ? >>
		extends NotesSourceContainer<Note<S>>, DBRecordsContainer<R>
{
	public String getDescription();

	public double getVersion();

	public String getName();

	public int getId();

	public Organism<S> getOrganism();

	public L getOrCreateSubsequence(int start, int end, Collection<Intron> introns);

	public Collection<F> getFeatures(AnnotationFilter<F> featureFilter);
}