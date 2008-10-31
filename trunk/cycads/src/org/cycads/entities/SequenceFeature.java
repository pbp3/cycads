/*
 * Created on 31/10/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface SequenceFeature
{
	public String getType();

	public Sequence getSequence();

	public String getName();

	public Location getLocation();

	public Collection<DBRef> getDBRefs();

	public Collection<Note> getNotes();

	public Collection<SequenceFeature> getFeatures();

}
