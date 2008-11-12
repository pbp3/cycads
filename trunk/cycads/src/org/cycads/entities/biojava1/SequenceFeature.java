/*
 * Created on 31/10/2008
 */
package org.cycads.entities.biojava1;

import java.util.Collection;

public interface SequenceFeature
{
	public String getType();

	public Sequence getSequence();

	public String getName();

	public Location getLocation();

	public Collection<DBRef> getDBRefs();

	public Collection<FeatureNote> getNotes();

	//	public void addNote(FeatureNote note);
	//
	public Collection<FeatureNote> getNotes(String noteTypeName);

	public FeatureNote getNote(String noteTypeName);

	public FeatureNote getNote(FeatureNoteType noteType);

	public Collection<FeatureNote> getNotes(FeatureNoteType noteType);

}
